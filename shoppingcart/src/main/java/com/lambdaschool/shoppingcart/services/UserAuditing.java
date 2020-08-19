package com.lambdaschool.shoppingcart.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Spring Boot needs to know what username to use for the auditing fields CreatedBy and ModifiedBy
 * For now, a default name will be used
 */
@Component
public class UserAuditing
        implements AuditorAware<String>
{
    /**
     * The current user
     *
     * @return Optional(String) of current user
     */
    @Override
    public Optional<String> getCurrentAuditor()
    {
        String uname;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // get auth data for user

        if (authentication != null)
        {
            // will take this path if we DO have auth data
            uname = authentication.getName();

            // if you want to get the username elsewhere in the project, autowire a private UserAuditing variable and
            // then using userAuditing.getCurrentAuditor().get()
        }
        else
        {
            // will take this path when seed data

            uname = "SYSTEM";

        }
        return Optional.of(uname);
    }
}
