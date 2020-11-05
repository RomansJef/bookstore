package com.bookstore.app;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/** Implementation of BooksRepository interface. */
@Service
public class BookstoreRepository {

  /** Books Repository. */
  @Autowired private IBookstoreRepository iBookstoreRepository;

  public void setBooksRepository(IBookstoreRepository booksRepository) {
    this.iBookstoreRepository = booksRepository;
  }

  public List<BookEntity> retrieveBooks() {
    List<BookEntity> bookEntities = iBookstoreRepository.findAll();
    return bookEntities;
  }

  public Optional<List<BookEntity>> getBooksByType(@NonNull String type) {
    Optional<List<BookEntity>> books = iBookstoreRepository.findByType(type);
    return books;
  }

  public  Optional<BookEntity> getBookById(@NonNull Long id) {
    Optional<BookEntity> book = iBookstoreRepository.findById(id);
    return book;
  }

  public BookEntity getBookByName(@NonNull String name) {
    BookEntity bookByName = iBookstoreRepository.findByName(name);
    return bookByName;
  }

  public void saveBook(@NonNull BookEntity bookEntity) {
    iBookstoreRepository.save(bookEntity);
  }

  public void deleteBook(@NonNull Long id) {
    iBookstoreRepository.deleteById(id);
  }
}
