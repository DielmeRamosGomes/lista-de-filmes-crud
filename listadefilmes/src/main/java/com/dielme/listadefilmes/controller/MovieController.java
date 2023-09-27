package com.dielme.listadefilmes.controller;

import com.dielme.listadefilmes.dto.MovieRecordDto;
import com.dielme.listadefilmes.model.Movie;
import com.dielme.listadefilmes.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//http://localhost:8080/api/v1/movielist

@RestController
@RequestMapping(path = "api/v1/movielist")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(path = "/getallmovie")
    ResponseEntity<List<Movie>> getAllMovie() {
        List<Movie> movieList = movieService.getFindAll();
        if (!movieList.isEmpty()) {
            for (Movie movie: movieList) {
                UUID id = movie.getId();
                movie.add(linkTo(methodOn(MovieController.class).getOneMovie(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(movieList);
    }

    @GetMapping(path = "/getonemovie/{id}")
    ResponseEntity<Object> getOneMovie(@PathVariable(value = "id")UUID id) {
        Optional<Movie> movieOptional = movieService.findById(id);
        if (movieOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
        }
        movieOptional.get().add(linkTo(methodOn(MovieController.class).getAllMovie()).withRel("Movie List"));
        return ResponseEntity.status(HttpStatus.OK).body(movieOptional.get());
    }

    @PostMapping(path = "/postmovie")
    ResponseEntity<Movie> postMovie(@RequestBody @Valid MovieRecordDto movieRecordDto) {
        var movie = new Movie();
        BeanUtils.copyProperties(movieRecordDto, movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.postMovie(movie).getBody());
    }

    @PutMapping(path = "/putmovie/{id}")
    ResponseEntity<Object> putMovie(@RequestBody @Valid MovieRecordDto movieRecordDto, @PathVariable(value = "id") UUID id) {
        Optional<Movie> movieOptional = movieService.findById(id);
        if (movieOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }
        var movie = movieOptional.get();
        BeanUtils.copyProperties(movieRecordDto, movie);
        return ResponseEntity.status(HttpStatus.OK).body(movieService.postMovie(movie).getBody());
    }

    @DeleteMapping(path = "/deletemovie/{id}")
    ResponseEntity<Object> deleteMovie(@PathVariable(value = "id") UUID id) {
        Optional<Movie> movieOptional = movieService.findById(id);
        if (movieOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(movieService.deleteMovieById(id).getBody());
    }

}
