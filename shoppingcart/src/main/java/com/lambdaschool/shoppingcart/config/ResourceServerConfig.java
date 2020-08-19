package com.lambdaschool.shoppingcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration // notifies spring that this is a config
@EnableResourceServer // lets spring know to enable a resource server (with this being the config)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter // have to extend this for compatibility
{
    private static final String RESOURCE_ID = "resource_id"; // we are managing our own resource server, but for a third
    // party resource server, you would use its resource id. As such we just name this whatever we want.


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception
    {
        resources.resourceId(RESOURCE_ID).stateless(false); // make sure we know the resource id to connect to it, and
        // once connected to the resource server, stay connected - this keeps the connection current by default and
        // speeds up determining who has access to what
    }

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        // this is where most work will be done configuring things

        http.csrf().disable(); // cross site request forgery disabled, disabled creation of tokens so clients can access
        // from other systems while going through our system. Beyond the scope of this class but put this in for now.

        http.headers().frameOptions().disable(); // for the h2 console to work

        http.logout().disable(); // disable spring's auto logout functionality

        http.authorizeRequests()
            .antMatchers("/", // what endpoint(s) this person/entity has access to
                "/h2-console/**",
                "/swagger-resources/**",
                "/swagger-resource/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**",
                "/createnewuser")
            .permitAll() // give everyone access to the above (this is the list of person/entity mentioned above)
            .antMatchers("/logout")
            .authenticated() // only authenticated users can access logout endpoint
            .antMatchers("/users/**")
            .hasAnyRole("ADMIN", "USER") // this is configuring global access and will be fine-tuned in controller
            .antMatchers("/roles/**")
            .hasAnyRole("ADMIN")
            .and()
            .exceptionHandling() // we do our own exception handling sometimes, except...
            .accessDeniedHandler(new OAuth2AccessDeniedHandler()); // ...when we use our OAuth2 default access denied handler

    }
}
