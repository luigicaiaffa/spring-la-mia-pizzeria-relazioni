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

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date cannot be set in the past")
    private LocalDate startDate;

    @FutureOrPresent(message = "Ending date cannot be set in the past")
    private LocalDate endDate;

    // # Costruttori
    public Offer() {
    }

    public Offer(Pizza pizza, String title, LocalDate startDate) {
        this.pizza = pizza;
        this.title = title;
        this.startDate = startDate;
    }

    public Offer(Pizza pizza, String title, LocalDate startDate, LocalDate endDate) {
        this.pizza = pizza;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // # Getter e Setter
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Pizza getPizza() {
        return this.pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public String toString() {
        return "Offer: " + this.title + "(" + this.startDate + " - " + this.endDate + ")";
    }
}
