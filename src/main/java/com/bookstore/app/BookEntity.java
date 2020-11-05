package com.bookstore.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * DTO and DB fields for Bookstore.
 */
@Data
@Entity
@Table(name = "BOOKSTORE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    /**
     * ID for Book stored in DB.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    /**
     * Name of the Book.
     */
    @Column(name = "BOOK_NAME")
    @NotNull(message = "Book name cannot be null")
    private String name;

    /**
     * Type of the Book.
     */
    @Column(name = "BOOK_TYPE")
    @NotNull(message = "Book type cannot be null")
    private String type;

    /**
     * Book Price.
     */
    @Column(name = "BOOK_PRICE", columnDefinition = "Decimal(10,2) default '100.00'")
    @NotNull(message = "Book price cannot be null")
    private Float price;

    /**
     * Author of the Book.
     */
    @Column(name = "AUTHOR")
    @NotNull(message = "Author cannot be null")
    private String author;

    /**
     * Year of the Book.
     */
    @Column(name = "YEAR")
    private Integer year;

    @Override
    public String toString() {
        return "Report{" + "ID: " + id +
                ", Name: '" + name + '\'' +
                ", Type: '" + type + '\'' +
                ", Price: '" + price + '\'' +
                ", Author: '" + author + '\'' +
                ", Year: ' " + year + '\'' +
                '}';
    }
}