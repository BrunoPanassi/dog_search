package com.dogsearch.demo.repository;

import com.dogsearch.demo.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {

    @Query("""
            SELECT i
            FROM Image i
            WHERE ((:announcementId = 0) OR (i.announcement.id = :announcementId))
            AND ((:imageId = 0) OR (i.id = :imageId))
            """)
    Image find(@Param("announcementId") Long announcementId, @Param("imageId") Long imageId);

    @Query("""
            SELECT i.id
            FROM Image i
            WHERE i.announcement.id = :announcementId
            """)
    Optional<Long> findImageIdByAnnouncementId(@Param("announcementId") Long announcementId);
}
