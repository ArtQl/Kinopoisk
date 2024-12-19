package ru.artq.practice.kinopoisk.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.artq.practice.kinopoisk.controller.FilmController;
import ru.artq.practice.kinopoisk.model.Film;
import ru.artq.practice.kinopoisk.service.FilmService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
public class MockitoFilmTest {
    @MockBean
    private FilmService filmService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getFilms() throws Exception {
        Mockito.when(filmService.getFilms())
                .thenReturn(List.of(new Film("a", "description value", LocalDate.now(), Duration.ofMinutes(123))));
        mockMvc.perform(MockMvcRequestBuilders.get("/films"))
                .andExpect(status().isOk());
    }

    @Test
    void addFilm() throws Exception {
        Film film = new Film("a", "description value",
                LocalDate.of(2024, 2, 2), Duration.ofMinutes(123));
        Mockito.when(filmService.addFilm(Mockito.any(Film.class))).thenReturn(film);
        mockMvc.perform(post("/films")
                .content(objectMapper.writeValueAsString(film))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(status().isOk());
        verify(filmService, times(1)).addFilm(film);

    }

}
