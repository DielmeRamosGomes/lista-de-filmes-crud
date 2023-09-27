package com.dielme.listadefilmes.service;

import com.dielme.listadefilmes.model.Movie;
import com.dielme.listadefilmes.repository.MovieRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public ResponseEntity<List<Movie>> getAllMovie() {
        return ResponseEntity.ok().body((List<Movie>) movieRepository.findAll());
    }

    public ResponseEntity<Movie> postMovie(Movie movie) {
        return ResponseEntity.ok().body(movieRepository.save(movie));
    }

    public Optional<Movie> findById(UUID id) {
        return movieRepository.findById(id);
    }

    public ResponseEntity<Object> deleteMovieById(UUID id) {
        movieRepository.deleteById(id);
        return ResponseEntity.ok().body(getAllMovie());
    }

    public List<Movie> getFindAll() {
        return (List<Movie>) movieRepository.findAll();
    }
}
