package com.dogsearch.demo.repository;

import com.dogsearch.demo.dto.announcement.AnnouncementDTO;
import com.dogsearch.demo.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnouncementFindRepo extends PagingAndSortingRepository<Announcement, Long> {

    @Query( value = """
            SELECT
            new com.dogsearch.demo.dto.announcement.AnnouncementDTO(
            a.id,
            a.title,
            a.text,
            p.name,
            p.phoneNumber,
            sc.category.name,
            sc.id)
            FROM Announcement a
            JOIN a.person p
            JOIN a.subCategory sc
            WHERE UPPER(p.email) = UPPER(:personEmail)
            AND ((:title = '_default_') OR UPPER(a.title) like CONCAT('%', UPPER(:title), '%'))
            """,
            countQuery = """
                    SELECT
                    count(*)
                    FROM Announcement a
                    JOIN a.person p
                    JOIN a.subCategory sc
                    WHERE UPPER(p.email) = UPPER(:personEmail)
                    AND ((:title = '_default_') OR UPPER(a.title) like CONCAT('%', UPPER(:title), '%'))
                    """)
    Page<AnnouncementDTO> find(@Param("personEmail") String personEmail, @Param("title") String title, Pageable pageable);
}
