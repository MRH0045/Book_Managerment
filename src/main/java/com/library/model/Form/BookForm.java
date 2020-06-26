package com.library.model.Form;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookForm {

    private String isbn;

    private String name;

    private String bookPicture;

    private String author;

    private String namePub;

    private LocalDateTime dataPub;

    private Integer total;

    private String details;

    private Integer bookKind;

    private Integer bookSite;

}
