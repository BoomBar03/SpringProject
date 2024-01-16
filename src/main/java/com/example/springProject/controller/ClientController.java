package com.example.springProject.controller;

import com.example.springProject.config.MySecurityAuthentication;
import com.example.springProject.dto.MovieDto;
import com.example.springProject.dto.TicketDto;
import com.example.springProject.dto.UserDto;
import com.example.springProject.model.Ticket;
import com.example.springProject.model.User;
import com.example.springProject.service.MovieService;
import com.example.springProject.service.TicketService;
import com.example.springProject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final MovieService movieService;
    private final TicketService ticketService;
    private final UserService userService;

    @GetMapping("clientPage")
    public String displayClientPage(Model model){
        return "clientPage";
    }
    @GetMapping("view-movies")
    public String displayViewMovies(Model model) {
        System.out.println(movieService.findAll());
        model.addAttribute("title", "View Movies");
        model.addAttribute("movies", movieService.findAll());
        return "view-movies";
    }

    @GetMapping("buy-tickets")
    public String displayBuyTicketsForm(Model model, Authentication authentication) {
        List<MovieDto> movies = movieService.findAll();
        model.addAttribute("title", "Buy Tickets");
        model.addAttribute("movies", movies);
        model.addAttribute(new Ticket());
        return "buy-tickets";

    }

    @PostMapping("buy-tickets")
    public String processBuyTicketsForm(@ModelAttribute @Valid Ticket newTicket,
                                        Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Buy Tickets");
            return "buy-tickets";
        }
        UserDto userDto = userService.getLoginUser();
        List<User> users = userService.findAllUsers();
        ArrayList<Integer> employeeIds = new ArrayList<>();
        for(User u: users){

            if(u.getRoles().stream().allMatch(role -> "DEVELOPER".equals(role.getRole())))
                //System.out.println("ID-UL ESTE: "+userService.getUserByUsername(u.username()));
                employeeIds.add(u.getId());
        }

        Random random = new Random();
        int randomIndex = random.nextInt(employeeIds.size());
        int randomEmployeeId = employeeIds.get(randomIndex);

        newTicket.setPrice(movieService.findMovieById(newTicket.getMovieId()).ticketPrice());
        newTicket.setUserId(userService.getUserByUsername(userDto.username()));
        newTicket.setEmployeeId(randomEmployeeId);


        ticketService.createTicket(newTicket);
        return "redirect:/buy-tickets";
    }
}
