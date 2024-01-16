package com.example.springProject.mapper;

import com.example.springProject.model.Movie;
import com.example.springProject.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class MovieMapper {

    public MovieDto movieEntityToDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .ticketPrice(movie.getTicketPrice())
                .build();
    }

    public List<MovieDto> movieListEntityToDto(List<Movie> movies) {
        return movies.stream()
                .map(this::movieEntityToDto)
                .collect(Collectors.toList());
    }

    public Movie movieDtoToEntity(MovieDto movieDto) {
        return Movie.builder()
                .id(movieDto.id())
                .title(movieDto.title())
                .ticketPrice(movieDto.ticketPrice())
                .build();
    }
//
//    public List<Movie> movieListDtoToEntity(List<MovieDto> movieDtos) {
//        return movieDtos.stream()
//                .map(this::movieDtoToEntity)
//                .collect(Collectors.toList());
//    }
}
