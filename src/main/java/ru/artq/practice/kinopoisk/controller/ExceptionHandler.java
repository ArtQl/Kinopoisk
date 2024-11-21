package ru.artq.practice.kinopoisk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.films.FilmAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.films.FilmNotExistException;
import ru.artq.practice.kinopoisk.exception.films.InvalidFilmIdException;
import ru.artq.practice.kinopoisk.exception.user.InvalidUserIdException;
import ru.artq.practice.kinopoisk.exception.user.UserAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;
import ru.artq.practice.kinopoisk.model.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResponse handleValidationException(ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({
            InvalidFilmIdException.class,
            FilmNotExistException.class,
            UserNotExistException.class,
            InvalidUserIdException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorResponse handleNotExistException(RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({
            FilmAlreadyExistException.class,
            UserAlreadyExistException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    private ErrorResponse handleAlreadyExistException(RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

}
