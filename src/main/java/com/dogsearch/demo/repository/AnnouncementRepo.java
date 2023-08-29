package com.dogsearch.demo.repository;

import com.dogsearch.demo.dto.announcement.AnnouncementDTO;
import com.dogsearch.demo.dto.announcement.AnnouncementTitlePersonDTO;
import com.dogsearch.demo.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {

    @Query("""
            SELECT
            new com.dogsearch.demo.dto.announcement.AnnouncementDTO(
            a.id,
            a.title,
            a.text,
            p.name,
            sc.category.name,
            sc.id,
            a.images) 
            FROM Announcement a
            JOIN a.person p
            JOIN a.subCategory sc
            WHERE UPPER(p.email) = UPPER(:personEmail)
            AND ((:title = '_default_') OR UPPER(a.title) like CONCAT('%', UPPER(:title), '%'))
            """)
    List<AnnouncementDTO> find(@Param("personEmail") String personEmail, @Param("title") String title);

    @Query(value = """
            SELECT
            new com.dogsearch.demo.dto.announcement.AnnouncementDTO(
            a.id,
            a.title,
            a.text,
            p.name) 
            FROM Announcement a
            INNER JOIN person p ON p.id = a.person.id
            WHERE ((:personId = 0) OR (a.person.id = :personId)) 
            AND ((:title = '_default_') OR (UPPER(a.title) like CONCAT('%', UPPER(:title), '%')))
            """)
    List<AnnouncementDTO> findByTitleAndPersonId(@Param("personId") Long personId, @Param("title") String title);

    @Query(value = """
            SELECT
            p.city
            FROM Announcement a
            JOIN a.person p
            GROUP BY p.city
            """)
    List<String> getCities();

    @Query(value = """
            SELECT
            new com.dogsearch.demo.dto.announcement.AnnouncementDTO(
            a.id,
            a.title,
            a.text,
            p.name) 
            FROM Announcement a
            JOIN a.person p
            JOIN a.subCategory s
            WHERE p.city = :city
            AND s.name = :subCategory
            """)
    List<AnnouncementDTO> getByCityAndSubCategory(@Param("city") String city, @Param("subCategory") String subCategory);
}
