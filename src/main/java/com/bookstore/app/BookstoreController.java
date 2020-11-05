package com.bookstore.app;

import com.bookstore.app.requests.*;
import com.bookstore.app.response.*;
import com.bookstore.app.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for Bookstore.
 */
@Log4j2
@Api(tags = "Bookstore Controller")
@RestController
public class BookstoreController {

    private NewBookService newBookService;
    private GetAllBooksService getAllBooksService;
    private GetBooksByTypeService getBooksByTypeService;
    private GetBookByIdService getBookByIdService;
    private DeleteBookByIdService deleteBookByIdService;
    private UpdateBookService updateBookService;

    public BookstoreController(NewBookService newBookService,
                               GetAllBooksService getAllBooksService,
                               GetBooksByTypeService getBooksByTypeService,
                               GetBookByIdService getBookByIdService,
                               DeleteBookByIdService deleteBookByIdService,
                               UpdateBookService updateBookService) {
        this.newBookService = newBookService;
        this.getAllBooksService = getAllBooksService;
        this.getBooksByTypeService = getBooksByTypeService;
        this.getBookByIdService = getBookByIdService;
        this.deleteBookByIdService = deleteBookByIdService;
        this.updateBookService = updateBookService;
    }

    /**
     * Save new Book in DB.
     */

    @ApiOperation(value = "Save New Book", nickname = "saveNewBook", notes = "Save new Book")
    @PostMapping(value = "/newbook", consumes = "application/json")
    public NewBookResponse postNewBook(
            @ApiParam(value = "Save new book request data body") @RequestBody @Valid List<NewBookRequest> request) {
        return newBookService.executeRequest(request);
    }

    /**
     * Select all books from DB.
     */
    @ApiOperation(value = "Show all books stored in DB", nickname = "AllBooks", notes = "Show all books")
    @GetMapping(value = "/", produces = "application/json")
    public GetAllBooksResponse getAllBooks() {
        return getAllBooksService.executeRequest();
    }

    /**
     * Returns books by type.
     */
    @ApiOperation(value = "Shows books by type", nickname = "booksByType", notes = "Shows books by type")
    @GetMapping(value = "/booksbytype", consumes = "application/json", produces = "application/json")
    public GetBooksByTypeResponse getBooksByType(
            @ApiParam(value = "Books by type request data body") @RequestBody @Valid GetBooksByTypeRequest booksByType) {
        return getBooksByTypeService.executeRequest(booksByType);
    }

    /**
     * Return book by Id.
     */
    @ApiOperation(value = "Find book by Id", nickname = "FindById", notes = "Find book by Id")
    @GetMapping(value = "/bookbyid", consumes = "application/json", produces = "application/json")
    public GetBookByIdResponse getBookById(
            @ApiParam(value = "Book by Id request data body") @RequestBody @Valid GetBookByIdRequest bookById) {
        return getBookByIdService.executeRequest(bookById);
    }

    /**
     * Delete book by Id.
     */
    @ApiOperation(value = "Delete book by Id", nickname = "DeleteById", notes = "Finds and delete book by Id")
    @PostMapping(value = "/deletebook", consumes = "application/json", produces = "application/json")
    public DeleteBookByIdResponse deleteBookById(
            @ApiParam(value = "Delete book request data body") @RequestBody @Valid DeleteBookByIdRequest deleteById) {
        return deleteBookByIdService.executeRequest(deleteById);
    }

    /**
     * Update book by Id.
     */
    @ApiOperation(value = "Update Book", nickname = "UpdateBook", notes = "Update Book")
    @PatchMapping(value = "/updatebook", consumes = "application/json", produces = "application/json")
    public UpdateBookResponse patchReport(
            @ApiParam(value = "Update book request data body") @RequestBody @Valid UpdateBookRequest updateBookRequest) {
        return updateBookService.executeRequest(updateBookRequest);
    }
}
