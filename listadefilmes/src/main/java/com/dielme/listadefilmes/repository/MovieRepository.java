package com.dielme.listadefilmes.repository;

import com.dielme.listadefilmes.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MovieRepository extends CrudRepository<Movie, UUID> {
}
