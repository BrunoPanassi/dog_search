package com.dogsearch.demo.repository;

import com.dogsearch.demo.dto.person.AnnouncementDTO;
import com.dogsearch.demo.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {

    @Query("""
            select
            a.id,
            a.title,
            a.text,
            p.name as personName
            from Announcement a
            join Person p
            where UPPER(p.name) like CONCAT('%', UPPER(:personName), '%')
            AND UPPER(a.title) like CONCAT('%', UPPER(:title), '%')
            """)
    AnnouncementDTO findByPersonNameAndTitle(@Param("personName") String personName, @Param("title") String title);
}
