package com.example.clubsapi.repository;

import com.example.clubsapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Integer> {
}
