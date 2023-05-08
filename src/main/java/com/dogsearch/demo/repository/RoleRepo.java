package com.dogsearch.demo.repository;

import com.dogsearch.demo.dto.role.PersonRoleDTO;
import com.dogsearch.demo.model.Category;
import com.dogsearch.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    @Query("""
            select
            role
            from Role role
            where UPPER(role.description) like CONCAT('%', UPPER(:description), '%')
            """)
    List<Role> findByDescription(@Param("description") String description);

    @Query(value = """
            SELECT
            role
            FROM Role role
            WHERE (:name = '_default_') OR (UPPER(role.description) LIKE CONCAT('%', UPPER(:name), '%'))
            AND (:id = 0) OR (role.id = :id) 
            """)
    List<Role> findByIdAndName(@Param("id") Long id, @Param("name") String name);

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
    PersonRoleDTO findPersonByRoleDescription(@Param("description") String description);//TODO: Mudar para List em cada select com Like

    @Query("""
            select
            role.description
            from Person person
            join person.roles role
            where person.id = :id 
            """)
    List<String> findRolesByPersonId(@Param("id") Long id);
}
