package org.lessons.java.spring_la_mia_pizzeria_crud.repository;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    public List<Pizza> findByNameContaining(String name);
}
