package com.example.clubsapi.repository;

import com.example.clubsapi.entity.ClubModerator;
import com.example.clubsapi.entity.Clubs;
import com.example.clubsapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubModeratorRepository extends JpaRepository<ClubModerator,Integer> {

    @Query("select u.club.id from ClubModerator u where u.user.id = :userId")
    Optional<List<Integer>> findClubsId(int userId);

    @Query("select u.club from ClubModerator u where u.user.id = :userId")
    Optional<List<Clubs>> findEventsId(int userId);
}
