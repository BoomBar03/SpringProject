package com.example.springProject.service;

import com.example.springProject.dto.MovieDto;
import com.example.springProject.model.Movie;

import java.util.List;

public interface MovieService {

    MovieDto findMovieById(Integer id);

    List<MovieDto> findAll();

    MovieDto createMovie(Movie movie);

    MovieDto updateMovie(Movie movie);

    void deleteMovie(Integer id);
}