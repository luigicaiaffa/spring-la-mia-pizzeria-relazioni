package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Offer;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.OfferRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model, @RequestParam(name = "name", required = false) String name) {
        List<Pizza> pizzas;

        if (name != null && !name.isEmpty()) {
            pizzas = pizzaRepository.findByNameContaining(name);
        } else {
            pizzas = pizzaRepository.findAll();
        }

        model.addAttribute("pizzas", pizzas);
        return "/pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable Integer id) {
        try {
            Pizza pizza = pizzaRepository.findById(id).get();
            model.addAttribute("pizza", pizza);
        } catch (NoSuchElementException e) {
            model.addAttribute("pizza", null);
        }

        return "/pizzas/show";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "/pizzas/create-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/pizzas/create-edit";
        } else {
            pizzaRepository.save(formPizza);
            return "redirect:/pizzas";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        model.addAttribute("edit", true);
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "/pizzas/create-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("pizza", pizzaRepository.findById(id).get());
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/pizzas/create-edit";
        } else {
            pizzaRepository.save(formPizza);
            return "redirect:/pizzas";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Pizza pizza = pizzaRepository.findById(id).get();

        for (Offer offer : pizza.getOffers()) {
            offerRepository.delete(offer);
        }

        pizzaRepository.delete(pizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/offer")
    public String createOffer(@PathVariable Integer id, Model model) {
        Offer offer = new Offer();
        offer.setPizza(pizzaRepository.findById(id).get());

        model.addAttribute("offer", offer);
        return "offers/create-edit";
    }
}
