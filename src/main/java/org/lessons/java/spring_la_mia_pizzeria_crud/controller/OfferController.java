package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Offer;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferRepository repository;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/offers/create";
        } else {
            repository.save(formOffer);
            return "redirect:/pizzas/" + formOffer.getPizza().getId();
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Offer offer = repository.findById(id).get();

        model.addAttribute("offer", offer);
        return "/offers/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/offers/edit";
        } else {
            repository.save(formOffer);
            return "redirect:/pizzas/" + formOffer.getPizza().getId();
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Offer offer = repository.findById(id).get();

        repository.delete(offer);
        return "redirect:/pizzas/" + offer.getPizza().getId();
    }

}
