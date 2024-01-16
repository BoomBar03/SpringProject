package com.example.springProject.dto;

import lombok.Builder;
import java.util.List;
@Builder
public record MovieDto(

        Integer id,
        String title,
        float ticketPrice) {}

