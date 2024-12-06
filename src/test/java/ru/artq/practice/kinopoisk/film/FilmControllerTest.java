//package ru.artq.practice.kinopoisk.film;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import ru.artq.practice.kinopoisk.exception.ValidationException;
//import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
//import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
//import ru.artq.practice.kinopoisk.exception.films.InvalidFilmIdException;
//import ru.artq.practice.kinopoisk.model.Film;
//import ru.artq.practice.kinopoisk.storage.FilmStorage;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertInstanceOf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
//@RequiredArgsConstructor
//abstract class FilmControllerTest {
//    private final MockMvc mockMvc;
//    private final ObjectMapper mapper;
//    private final FilmStorage filmStorage;
//
//    Film film;
//
//    @AfterEach
//    void clear() {
//        filmStorage.clear();
//    }
//
//    @BeforeEach
//    void start() {
//        film = createFilm("Terminator");
//    }
//
//    @Test
//    void test1_addFilm() throws Exception {
//        film.setId(1);
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/films")
//                .content(mapper.writeValueAsString(createFilm("Terminator")))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpectAll(
//                status().isOk(),
//                MockMvcResultMatchers.content().json(mapper.writeValueAsString(film))
//        );
//
//        film.setId(2);
//        mockMvc.perform(post("/films")
//                .content(mapper.writeValueAsString(film))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpectAll(
//                status().isNotFound(),
//                result -> assertInstanceOf(InvalidFilmIdException.class, result.getResolvedException(), "ID Exist")
//        );
//        film.setId(1);
//        mockMvc.perform(post("/films")
//                .content(mapper.writeValueAsString(film))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpectAll(
//                status().isConflict(),
//                result -> assertInstanceOf(FilmAlreadyExistException.class, result.getResolvedException(), "Film Exist")
//        );
//
//        mockMvc.perform(get("/films/{id}", 1))
//                .andExpectAll(
//                        status().isOk(),
//                        jsonPath("name").value("Terminator")
//                );
//    }
//
//    @Test
//    void test2_addFilmWithWrongDuration() throws Exception {
//        film = Film.builder().name("a").description("asdfsdffdsdf")
//                .duration(Duration.ofMinutes(-21))
//                .releaseDate(LocalDate.of(2020, 12, 31))
//                .build();
//        mockMvc.perform(post("/films")
//                .content(mapper.writeValueAsString(film))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpectAll(
//                status().isBadRequest(),
//                result -> assertInstanceOf(ValidationException.class, result.getResolvedException(), "Negative duration")
//        );
//    }
//
//    @Test
//    void test3_addEmptyFilm() throws Exception {
//        mockMvc.perform(post("/films")
//                .content(mapper.writeValueAsString(Film.builder().build()))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpectAll(
//                status().isBadRequest(),
//                result -> assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException())
//        );
//
//        mockMvc.perform(post("/films")
//                        .content(mapper.writeValueAsString(null))
//                        .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(status().is5xxServerError())
//                .andExpect(result -> assertInstanceOf(HttpMessageNotReadableException.class, result.getResolvedException()));
//    }
//
//    @Test
//    void test3_getFilms() throws Exception {
//        mockMvc.perform(get("/films")).andExpectAll(
//                status().isNotFound(),
//                result -> assertInstanceOf(FilmNotExistException.class, result.getResolvedException(), "No Films")
//        );
//
//        mockMvc.perform(post("/films")
//                .content(mapper.writeValueAsString(createFilm("A")))
//                .contentType(MediaType.APPLICATION_JSON)
//        );
//        mockMvc.perform(post("/films")
//                .content(mapper.writeValueAsString(createFilm("B")))
//                .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        mockMvc.perform(get("/films/{id}", 1)).andExpectAll(
//                MockMvcResultMatchers.status().isOk(),
//                jsonPath("name").value("A")
//        );
//
//        mockMvc.perform(get("/films/{id}", 3)).andExpectAll(
//                MockMvcResultMatchers.status().isNotFound(),
//                result -> assertInstanceOf(FilmNotExistException.class, result.getResolvedException(), "Wrong ID")
//        );
//
//        Film a = createFilm("A");
//        a.setId(1);
//        Film b = createFilm("B");
//        b.setId(2);
//        mockMvc.perform(get("/films")).andExpectAll(
//                MockMvcResultMatchers.status().isOk(),
//                content().json(mapper.writeValueAsString(List.of(a, b)))
//        );
//    }
//
//    @Test
//    void test4_updateFilm() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/films")
//                .content(mapper.writeValueAsString(film))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isOk());
//
//        film.setId(1);
//        mockMvc.perform(put("/films")
//                .content(mapper.writeValueAsString(film))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isOk());
//    }
//
//    @Test
//    void test5_updateNotFound() throws Exception {
//        film.setId(1);
//        mockMvc.perform(put("/films")
//                .content(mapper.writeValueAsString(film))
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpectAll(
//                status().isNotFound(),
//                result -> assertInstanceOf(InvalidFilmIdException.class, result.getResolvedException())
//        );
//    }
//
//    private Film createFilm(String name) {
//        return Film.builder()
//                .name(name).description("description value")
//                .duration(Duration.ofMinutes(30))
//                .releaseDate(LocalDate.of(2020, 12, 31))
//                .build();
//    }
//}