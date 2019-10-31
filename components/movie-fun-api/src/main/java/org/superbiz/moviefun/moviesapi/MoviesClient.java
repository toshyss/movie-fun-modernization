package org.superbiz.moviefun.moviesapi;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.sun.jndi.toolkit.url.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestOperations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MoviesClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@PersistenceContext
    private EntityManager entityManager;

    private String moviesUrl;
    private RestOperations restOperations;

    public MoviesClient (String moviesUrl, RestOperations restOperations){
        this.moviesUrl = moviesUrl;
        this.restOperations = restOperations;
    }

    public MovieInfo find(Long id) {
        //return entityManager.find(MovieInfo.class, id);
        return restOperations.getForObject(moviesUrl + "/" +
                 String.valueOf(id), MovieInfo.class);
    }

    //@Transactional
    public void addMovie(MovieInfo movieInfo) {
        logger.debug("Creating movie with title {}, and year {}", movieInfo.getTitle(), movieInfo.getYear());

        //entityManager.persist(movieInfo);
        restOperations.postForLocation(moviesUrl, movieInfo);
    }

    //@Transactional
    public void updateMovie(MovieInfo movieInfo) {
//        entityManager.merge(movieInfo);
        restOperations.put(moviesUrl, movieInfo);
    }

    //@Transactional
    public void deleteMovie(MovieInfo movieInfo) {
//        entityManager.remove(movieInfo);
        long id = movieInfo.getId();
        String thisUrl = moviesUrl + "/" + String.valueOf(id);

        restOperations.delete(thisUrl);
    }

    //@Transactional
    public void deleteMovieId(long id) {
//        MovieInfo movieInfo = entityManager.find(MovieInfo.class, id);
        MovieInfo movieInfo = find(id);
        deleteMovie(movieInfo);
    }

//    public List<MovieInfo> getMovies() {
//        CriteriaQuery<MovieInfo> cq = entityManager.getCriteriaBuilder().createQuery(MovieInfo.class);
//        cq.select(cq.from(MovieInfo.class));
//        return entityManager.createQuery(cq).getResultList();
//    }

    public List<MovieInfo> getMovies() throws URISyntaxException {
        URI uri = new URI(moviesUrl);

        RequestEntity requestEntity =
                RequestEntity
                        .get(uri).build();

        ResponseEntity<List<MovieInfo>> responseEntity =
                restOperations.exchange(
                        requestEntity,
                        new ParameterizedTypeReference<List<MovieInfo>>(){});
        return responseEntity.getBody();
    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) throws URISyntaxException {
//        CriteriaQuery<MovieInfo> cq = entityManager.getCriteriaBuilder().createQuery(MovieInfo.class);
//        cq.select(cq.from(MovieInfo.class));
//        TypedQuery<MovieInfo> q = entityManager.createQuery(cq);
//        q.setMaxResults(maxResults);
//        q.setFirstResult(firstResult);
//        return q.getResultList();

        String thisUrl = moviesUrl + "?firstResult=" + Integer.toString(firstResult)
                + "&maxResults=" + Integer.toString(maxResults);

        URI uri = new URI(thisUrl);

        RequestEntity requestEntity =
                RequestEntity
                        .get(uri).build();

        ResponseEntity<List<MovieInfo>> responseEntity =
                restOperations.exchange(
                        requestEntity,
                        new ParameterizedTypeReference<List<MovieInfo>>(){});
        return responseEntity.getBody();
    }

    public int countAll() {
//        CriteriaQuery<Long> cq = entityManager.getCriteriaBuilder().createQuery(Long.class);
//        Root<MovieInfo> rt = cq.from(MovieInfo.class);
//        cq.select(entityManager.getCriteriaBuilder().count(rt));
//        TypedQuery<Long> q = entityManager.createQuery(cq);
//        return (q.getSingleResult()).intValue();
        int countNum = restOperations.getForObject(moviesUrl + "/count", Integer.class);

        return countNum;
    }

    public int count(String field, String searchTerm) {
//        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
//        Root<MovieInfo> root = cq.from(MovieInfo.class);
//        EntityType<MovieInfo> type = entityManager.getMetamodel().entity(MovieInfo.class);
//
//        Path<String> path = root.get(type.getDeclaredSingularAttribute(field, String.class));
//        Predicate condition = qb.like(path, "%" + searchTerm + "%");
//
//        cq.select(qb.count(root));
//        cq.where(condition);
//
//        return entityManager.createQuery(cq).getSingleResult().intValue();
        int countNum = restOperations.getForObject(moviesUrl + "/count/" + "?field=" + field + "&searchTerm=" + searchTerm, Integer.class);

        return countNum;
    }

    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) throws URISyntaxException {
//        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<MovieInfo> cq = qb.createQuery(MovieInfo.class);
//        Root<MovieInfo> root = cq.from(MovieInfo.class);
//        EntityType<MovieInfo> type = entityManager.getMetamodel().entity(MovieInfo.class);
//
//        Path<String> path = root.get(type.getDeclaredSingularAttribute(field, String.class));
//        Predicate condition = qb.like(path, "%" + searchTerm + "%");
//
//        cq.where(condition);
//        TypedQuery<MovieInfo> q = entityManager.createQuery(cq);
//        q.setMaxResults(maxResults);
//        q.setFirstResult(firstResult);
//        return q.getResultList();

        String thisUrl = moviesUrl
                + "?firstResult=" + Integer.toString(firstResult)
                + "&maxResults=" + Integer.toString(maxResults)
                + "&field=" + field
                + "&searchTerm=" + searchTerm;

        URI uri = new URI(thisUrl);

        RequestEntity requestEntity =
                RequestEntity
                        .get(uri).build();

        ResponseEntity<List<MovieInfo>> responseEntity =
                restOperations.exchange(
                        requestEntity,
                        new ParameterizedTypeReference<List<MovieInfo>>(){});
        return responseEntity.getBody();
    }

    public void clean() {
        // entityManager.createQuery("delete from Movie").executeUpdate();
        restOperations.delete(moviesUrl);
    }

}
