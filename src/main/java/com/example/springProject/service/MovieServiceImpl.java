package com.example.springProject.service;

import com.example.springProject.dto.MovieDto;
import com.example.springProject.mapper.MovieMapper;
import com.example.springProject.model.Movie;
import com.example.springProject.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public MovieDto findMovieById(Integer id) {
        return movieMapper.movieEntityToDto(movieRepository.findById(id).orElse(null));
    }

    @Override
    public List<MovieDto> findAll() {
        return movieMapper.movieListEntityToDto(movieRepository.findAll());
    }

    @Override
    public MovieDto createMovie(Movie movie) {
        return movieMapper.movieEntityToDto(movieRepository.save(movie));
    }


    @Override
    public MovieDto updateMovie(Movie movie) {
        if (movie.getId() == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        Optional<Movie> existsInDto = movieRepository.findById(movie.getId());
        if (existsInDto.isPresent()) {
            Movie newMovie = existsInDto.get();
            newMovie.setTicketPrice(movie.getTicketPrice());
            newMovie.setTitle(movie.getTitle());
            movieRepository.save(newMovie);
            System.out.println("Updated successfully!");
            return movieMapper.movieEntityToDto(newMovie);
        } else {
            throw new EntityNotFoundException("Movie not found");
        }
    }

    @Override
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }
}
