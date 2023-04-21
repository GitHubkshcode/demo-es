package com.ksh.demoes.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Document(indexName = "blog")
public class Article {

    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Author> authors;

}