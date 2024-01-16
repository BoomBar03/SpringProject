package com.example.springProject.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record TicketDto (
        Integer id,
        Integer movieId,
        Integer userId,
        Integer employeeId,
        float price ){}
