package com.example.springProject.repository;

import com.example.springProject.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findById(Integer id);

    List<Movie> findAll();



}
