package com.example.springProject.repository;

import com.example.springProject.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    Optional<Ticket> findById(Integer id);

    List<Ticket> findAll();

}
