package com.example.clubsapi.repository;

import com.example.clubsapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Integer> {

   Optional<Event> findByTitle(String eventName);
}
