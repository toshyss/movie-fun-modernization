package org.superbiz.moviefun.movies;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {
    private MoviesRepository moviesRepository;

    public MoviesController(MoviesRepository moviesRepository){
        this.moviesRepository = moviesRepository;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll(
            @RequestParam(name = "firstResult", required = false) String firstResult,
            @RequestParam(name = "maxResults", required = false) String maxResults){
        if (firstResult == null && maxResults == null){
            return new ResponseEntity<List<Movie>>(moviesRepository.getMovies(), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Movie>>(moviesRepository.findAll(Integer.parseInt(firstResult), Integer.parseInt(maxResults)), HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> find(@PathVariable Long id){
        return new ResponseEntity<Movie>(moviesRepository.find(id), HttpStatus.OK);
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie){
        moviesRepository.addMovie(movie);
    }

    @PutMapping
    public void updateMovie(@RequestBody Movie movie) {
        moviesRepository.updateMovie(movie);
    }

    @DeleteMapping("{id}")
    public void deleteMovieId(@PathVariable Long id) {
        moviesRepository.deleteMovieId(id);
    }

    @DeleteMapping
    public void deleteMovie() {
        moviesRepository.clean();
    }

    @GetMapping("/count")
    public int count(@RequestParam(name = "field") String field,
                     @RequestParam(name = "searchTerm") String searchTerm) {
        if(field == "" || searchTerm == "") {
            return moviesRepository.countAll();
        } else {
            return moviesRepository.count(field, searchTerm);
        }
    }
}
