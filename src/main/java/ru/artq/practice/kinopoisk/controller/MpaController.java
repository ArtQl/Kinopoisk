package ru.artq.practice.kinopoisk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.artq.practice.kinopoisk.service.MPAFilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaController {
    private final MPAFilmService mpaFilmService;

    @GetMapping("/{id}/mpa")
    public Collection<String> getAllMpaFilm(@PathVariable Integer id) {
        return mpaFilmService.getAllMPAFilm(id);
    }

    @PostMapping("/{id}/mpa/{mpa}")
    public Boolean addMpaToFilm(@PathVariable Integer id, @PathVariable String mpa) {
        return mpaFilmService.addMPAToFilm(id, mpa);
    }

    @DeleteMapping("/{id}/mpa/{mpa}")
    public Boolean removeMpaFromFilm(@PathVariable Integer id, @PathVariable String mpa) {
        return mpaFilmService.removeMPAFromFilm(id, mpa);
    }
}
