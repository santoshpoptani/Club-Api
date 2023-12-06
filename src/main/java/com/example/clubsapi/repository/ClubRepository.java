package com.example.clubsapi.repository;

import com.example.clubsapi.entity.Clubs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Clubs,Integer> {


    Optional<Clubs> findByTitle(String Tile);
}
