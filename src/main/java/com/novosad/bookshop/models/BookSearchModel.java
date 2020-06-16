package com.novosad.bookshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchModel {

    private String title;
    private List<String> authors = new ArrayList<>();
    private Integer minPublishingYear;
    private Integer maxPublishingYear;

    public List<String> getAuthors() {
        return authors.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public String getTitle() {
        return title == null ? null : title.toLowerCase();
    }
}
