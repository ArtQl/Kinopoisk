package ru.artq.practice.kinopoisk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.artq.practice.kinopoisk.exception.ErrorResponse;
import ru.artq.practice.kinopoisk.exception.films.*;
import ru.artq.practice.kinopoisk.exception.review.ReviewIdExistException;
import ru.artq.practice.kinopoisk.exception.ValidationException;
import ru.artq.practice.kinopoisk.exception.review.ReviewNotExistException;
import ru.artq.practice.kinopoisk.exception.user.FriendshipException;
import ru.artq.practice.kinopoisk.exception.user.UserIdException;
import ru.artq.practice.kinopoisk.exception.user.UserAlreadyExistException;
import ru.artq.practice.kinopoisk.exception.user.UserNotExistException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({
            ValidationException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResponse handleValidationException(ValidationException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ExceptionHandler({
            GenreIdException.class, GenreNotExistException.class,
            MpaIdException.class, MpaNotExistException.class,
            ReviewIdExistException.class, ReviewNotExistException.class,
            FilmIdException.class, FilmNotExistException.class,
            UserIdException.class, UserNotExistException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorResponse handleNotExistException(RuntimeException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ExceptionHandler({
            FilmAlreadyExistException.class,
            UserAlreadyExistException.class,
            LikeFilmException.class,
            FriendshipException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    private ErrorResponse handleAlreadyExistException(RuntimeException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorResponse handleOtherException(Throwable e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": "
                        + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body("Validation error: " + errorMessage);
    }
}

