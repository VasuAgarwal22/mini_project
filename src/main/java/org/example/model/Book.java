package org.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class Book {

    private Long bookId;
    private String title;
    private String author;
    private String publisher;
    private String category;
    private int totalQuantity;
    private int availableQuantity;

}
