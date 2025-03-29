package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    @NotBlank(message = "Offer title cannot be null")
    private String title;

    @NotNull(message = "The start date of the offer cannot be null")
    @FutureOrPresent(message = "The start date of offer cannot be set in the past")
    private LocalDate startDate;

    @FutureOrPresent(message = "The end date of offer cannot be set in the past")
    private LocalDate endDate;

}
