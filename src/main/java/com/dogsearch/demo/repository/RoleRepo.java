package com.dogsearch.demo.repository;

import com.dogsearch.demo.dto.role.PersonRoleDTO;
import com.dogsearch.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    @Query("""
            select
            role
            from Role role
            where UPPER(role.description) like CONCAT('%', UPPER(:description), '%')
            """)
    Role find(@Param("description") String description);

    @Query("""
            select
            new com.dogsearch.demo.dto.role.PersonRoleDTO(
            person.id,
            person.name,
            role.description)
            from Person person
            join Role role
            where UPPER(role.description) like CONCAT('%', UPPER(:description), '%') 
            """)
    PersonRoleDTO findPersonByRoleDescription(@Param("description") String description);
}
