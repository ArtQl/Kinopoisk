package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.artq.practice.kinopoisk.service.AdviceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AdviceController {
    AdviceService adviceService;
    @PostMapping("/advice")
    public void adviceFilm(@RequestParam Integer userId,
                           @RequestParam Integer friendId,
                           @RequestParam Integer filmId) {
        adviceService.adviceFilm(userId, friendId, filmId);
    }
}
