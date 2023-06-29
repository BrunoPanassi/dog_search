package com.dogsearch.demo.repository;

import com.dogsearch.demo.dto.person.PersonDTO;
import com.dogsearch.demo.model.Announcement;
import com.dogsearch.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

    @Query(value = """
            SELECT
            new com.dogsearch.demo.dto.person.PersonDTO(
            person.id,
            person.name) 
            FROM Person person
            WHERE UPPER(person.name) LIKE CONCAT('%', UPPER(:name), '%') 
            AND person.phoneNumber LIKE CONCAT('%', UPPER(:phoneNumber), '%')
            """)
    List<PersonDTO> findPersonNameByNameAndPhoneNumber(@Param("name") String name, @Param("phoneNumber") String phoneNumber);

    @Query(value = """
            SELECT
            new com.dogsearch.demo.dto.person.PersonDTO(
            person.id,
            person.name) 
            FROM Person person
            WHERE UPPER(person.phoneNumber) = UPPER(:phoneNumber)
            """)
    Optional<PersonDTO> findPersonNameByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query(value = """
            SELECT
            person
            FROM Person person
            WHERE UPPER(person.email) = UPPER(:email)
            """)
    Optional<Person> findPersonByEmail(@Param("email") String email);

    @Query(value = """
            SELECT
            new com.dogsearch.demo.dto.person.PersonDTO(
            person.id,
            person.name) 
            FROM Person person
            WHERE UPPER(person.name) LIKE CONCAT('%', UPPER(:name), '%') 
            """)
    List<PersonDTO> findByName(@Param("name") String name);

    @Query(value = """
            SELECT
            person
            FROM Person person
            WHERE UPPER(person.name) = UPPER(:name) 
            """)
    Optional<Person> findPersonByName(@Param("name") String name);

    @Query(value = """
            SELECT
            new com.dogsearch.demo.dto.person.PersonDTO(
            person.id,
            person.name) 
            FROM Person person
            WHERE (:name = '_default_') OR (UPPER(person.name) LIKE CONCAT('%', UPPER(:name), '%'))
            AND (:id = 0) OR (person.id = :id)
            """)
    List<PersonDTO> findByIdAndName(@Param("id") Long id, @Param("name") String name);

    @Query(value = """
            SELECT
            announcement
            FROM Person person
            JOIN person.announcements announcement
            WHERE person.id = :id
            """)
    List<Announcement> findAnnouncementsByPersonId(@Param("id") Long personId);
}
