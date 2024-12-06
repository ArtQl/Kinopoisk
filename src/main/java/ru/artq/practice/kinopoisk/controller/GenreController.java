package ru.artq.practice.kinopoisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.artq.practice.kinopoisk.model.Genre;
import ru.artq.practice.kinopoisk.service.AbstractService;

@RestController
@RequestMapping("/genres")
public class GenreController extends AbstractController<Genre> {
    @Autowired
    public GenreController(AbstractService<Genre> abstractService) {
        super(abstractService);
    }
}
