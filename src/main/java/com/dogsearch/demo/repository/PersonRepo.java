package com.dogsearch.demo.repository;

import com.dogsearch.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

    @Query(value = """
            SELECT
            person.name
            FROM Person person
            WHERE UPPER(person.name) LIKE CONCAT('%', UPPER(:name), '%') 
            AND person.phoneNumber LIKE CONCAT('%', UPPER(:phoneNumber), '%')
            """)
    String findPersonNameByNameAndPhoneNumber(@Param("name") String name, @Param("phoneNumber") String phoneNumber);
}
