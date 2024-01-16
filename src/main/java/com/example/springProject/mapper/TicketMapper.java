package com.example.springProject.mapper;

import com.example.springProject.model.Ticket;
import com.example.springProject.dto.TicketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TicketMapper {


    public TicketDto ticketEntityToDto(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .userId(ticket.getUserId())
                .movieId(ticket.getMovieId())
                .employeeId(ticket.getEmployeeId())
                .price(ticket.getPrice())
                .build();
    }

    public List<TicketDto> ticketListEntityToDto(List<Ticket> tickets) {
        return tickets.stream()
                .map(this::ticketEntityToDto)
                .toList();
    }

    public Ticket ticketDtoToEntity(TicketDto ticketDto) {
        return Ticket.builder()
                .id(ticketDto.id())
                .userId(ticketDto.userId())
                .movieId(ticketDto.movieId())
                .employeeId(ticketDto.employeeId())
                .price(ticketDto.price())
                .build();
    }

    public List<Ticket> ticketListDtoToEntity(List<TicketDto> ticketDtos) {
        return ticketDtos.stream()
                .map(this::ticketDtoToEntity)
                .toList();
    }
}
