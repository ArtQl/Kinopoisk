package ru.artq.practice.kinopoisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.artq.practice.kinopoisk.model.Director;
import ru.artq.practice.kinopoisk.service.AbstractService;

@RestController
@RequestMapping("/directors")
public class DirectorController extends AbstractController<Director> {
    @Autowired
    public DirectorController(AbstractService<Director> abstractService) {
        super(abstractService);
    }
}