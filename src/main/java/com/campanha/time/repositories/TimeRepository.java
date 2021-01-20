package com.campanha.time.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campanha.time.entities.Time;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
	Time findByNomeIgnoreCase(String time);
}
