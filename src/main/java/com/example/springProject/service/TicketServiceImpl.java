package com.example.springProject.service;

import com.example.springProject.dto.TicketDto;
import com.example.springProject.mapper.TicketMapper;
import com.example.springProject.model.Ticket;
import com.example.springProject.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public TicketDto findById(Integer id) {
        return ticketMapper.ticketEntityToDto(ticketRepository.findById(id).orElse(null));
    }

    @Override
    public List<TicketDto> findAll() {
        return ticketMapper.ticketListEntityToDto(ticketRepository.findAll());
    }

    @Override
    public TicketDto createTicket(Ticket ticket) {
        return ticketMapper.ticketEntityToDto(ticketRepository.save(ticket));
    }

    @Override
    public TicketDto updateTicket(Ticket ticket) {
        return ticketMapper.ticketEntityToDto(ticketRepository.save(ticket));
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        ticketRepository.delete(ticket);
    }
}
