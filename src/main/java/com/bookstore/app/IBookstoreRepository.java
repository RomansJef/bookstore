package com.bookstore.app;

import lombok.NonNull;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "bookstore", path = "bookstore")
public interface IBookstoreRepository extends PagingAndSortingRepository <BookEntity, Long> {

    List<BookEntity> findAll();
    Optional<List<BookEntity>> findByType(@Param("type" ) @NonNull String type);
    BookEntity findByName(@Param("name") @NonNull String name);
}