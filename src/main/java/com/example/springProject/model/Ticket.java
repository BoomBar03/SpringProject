package com.example.springProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TICKETS")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Integer movieId;

    @JoinColumn(name = "USER_ID", nullable = false)
    private Integer userId;

    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
    private Integer employeeId;

    @Column(nullable = false)
    private float price;

}
