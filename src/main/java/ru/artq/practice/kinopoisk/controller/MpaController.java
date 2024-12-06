package ru.artq.practice.kinopoisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.artq.practice.kinopoisk.model.Mpa;
import ru.artq.practice.kinopoisk.service.AbstractService;

@RestController
@RequestMapping("/mpa")
public class MpaController extends AbstractController<Mpa> {
    @Autowired
    public MpaController(AbstractService<Mpa> abstractService) {
        super(abstractService);
    }
}
