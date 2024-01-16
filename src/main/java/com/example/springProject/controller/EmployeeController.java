package com.example.springProject.controller;

import com.example.springProject.dto.MovieDto;
import com.example.springProject.model.Movie;
import com.example.springProject.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final MovieService movieService;

    @GetMapping("employeePage")
    public String displayEmployeePage(Model model){
        return "employeePage";
    }
    @GetMapping("create-movie")
    public String displayCreateMovieForm(Model model) {
        model.addAttribute("title", "Employee - Create Movie");
        model.addAttribute(new Movie());
        return "create-movie";
    }

    @PostMapping("create-movie")
    public String processCreateMovieForm(@ModelAttribute @Valid Movie newMovie,
                                         Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Employee - Create Movie");
            return "create-movie";
        }

        movieService.createMovie(newMovie);
        return "redirect:/create-movie";
    }

    @GetMapping("search-movie")
    public String searchMovieForm(Model model){
        model.addAttribute("title", "Employee - Search Movie");
        return "search-movie";
    }

    @GetMapping("display-movie")
    public String searchMovie(@RequestParam("movieId") Integer movieId, Model model) {
        MovieDto movie = movieService.findMovieById(movieId);
        if (movie == null) {
            model.addAttribute("title", "Employee - Movie Not Found");
            return "search-movie";
        }

        model.addAttribute("title", "Employee - Display Movie");
        model.addAttribute("movie", movie);
        return "display-movie" ;
    }

    @PostMapping("display-movie")
    public String processSearchMovie(){
        return "redirect:/display-movie";
    }

    @GetMapping("update-movie")
    public String displayUpdateMovieForm( Model model) {
        model.addAttribute("title", "Employee - Update Movie");
        model.addAttribute( new Movie());
        return "update-movie";
    }

    @PostMapping("update-movie")
    public String processUpdateMovieForm(@ModelAttribute @Valid Movie updatedMovie,
                                         Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Employee - Update Movie");
            return "update-movie";
        }

        movieService.updateMovie(updatedMovie);
        return "redirect:/update-movie";
    }

    @GetMapping("delete-movie")
    public String displayDeleteMovieForm( Model model) {
        model.addAttribute("title", "Employee - Delete movie");
        model.addAttribute( new Movie());
        return "delete-movie";
    }
    @PostMapping("delete-movie")
    public String processDeleteMovieForm(@RequestParam("id") Integer id , Model model){
        if (id == null) {
            model.addAttribute("title", "Employee - Delete Movie");
            return "delete-movie";
        }
        System.out.println("ID: "+id);
        movieService.deleteMovie(id);
        return "redirect:/delete-movie";
    }

}
