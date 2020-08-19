package com.lambdaschool.shoppingcart.repositories;

import com.lambdaschool.shoppingcart.models.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends CrudRepository<Role, Long>
{

    Role findByNameIgnoreCase(String name); // I don't THINK these are needed yet but they might be and they shouldn't break anything anyway

    @Transactional
    @Modifying
    @Query(value = "UPDATE roles SET name = :name, last_modified_by = :uname, last_modified_date = CURRENT_TIMESTAMP where roleid = :roleid", nativeQuery = true)
    void updateRoleName(String uname, long roleid, String name);

}
