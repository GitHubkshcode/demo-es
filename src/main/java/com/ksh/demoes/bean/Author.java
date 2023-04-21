package com.ksh.demoes.bean;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class Author {
    @Field(type = FieldType.Keyword)
    private String name;
}
