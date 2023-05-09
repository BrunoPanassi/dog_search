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
            select
            new com.dogsearch.demo.dto.announcement.AnnouncementDTO(
            a.id,
            a.title,
            a.text,
            p.name) 
            from Announcement a
            join a.person p
            where UPPER(p.name) like CONCAT('%', UPPER(:personName), '%')
            AND UPPER(a.title) like CONCAT('%', UPPER(:title), '%')
            """)
    List<AnnouncementDTO> find(@Param("personName") String personName, @Param("title") String title);

    @Query(value = """
            SELECT
            new com.dogsearch.demo.dto.announcement.AnnouncementDTO(
            a.id,
            a.title,
            a.text,
            p.name) 
            FROM announcement a
            INNER JOIN person p ON p.id = a.person_id
            WHERE ((:personId = 0) OR (a.person_id = :personId)) 
            AND ((:title = '_default_') OR (UPPER(a.title) like CONCAT('%', UPPER(:title), '%')))
            """)
    List<AnnouncementDTO> findByTitleAndPersonId(@Param("personId") Long personId, @Param("title") String title);
}
