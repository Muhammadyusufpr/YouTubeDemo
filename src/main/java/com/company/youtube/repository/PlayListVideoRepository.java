package com.company.youtube.repository;

import com.company.youtube.entity.PlayListVideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlayListVideoRepository extends JpaRepository<PlayListVideoEntity, String> {
    Iterable<PlayListVideoEntity> findByPlayListId(String id);

    Optional<PlayListVideoEntity> findByPlayListIdAndVideoId(String playListId, String videoId);


    Optional<PlayListVideoEntity> findByVideoId(String videoId);

    List<PlayListVideoEntity> findAllByPlayListId(String playlistId);

    @Query(value = "SELECT COUNT (p.*) from playlist_video AS p where p.playlist_id=:playlistId", nativeQuery = true)
    int getCount(@Param("playlistId") Integer playlistId);

    @Query(value = "select count(play_list.view_count) from play_list\n" +
            "inner join video on video.id = play_list.id\n" +
            "where video.view_count in (select count(view_count) from video) and play_list.view_count =:viewCount", nativeQuery = true)
    int getTotalViewCount(@Param("viewCount") Integer viewCount);


}
