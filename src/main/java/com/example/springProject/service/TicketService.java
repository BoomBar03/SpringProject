package com.example.springProject.service;

import com.example.springProject.dto.TicketDto;
import com.example.springProject.model.Ticket;

import java.util.List;

public interface TicketService {

    TicketDto findById(Integer id);

    List<TicketDto> findAll();

    TicketDto createTicket(Ticket ticket);

    TicketDto updateTicket(Ticket ticket);

    void deleteTicket(Ticket ticket);
}
