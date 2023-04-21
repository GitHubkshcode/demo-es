package com.ksh.demoes.service;

import com.ksh.demoes.bean.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ArticleServices {
    Article save(Article article);

    boolean delete(String id);

    boolean updateTitle(String oldTitle, String newTitle);

    Page<Article> findByAuthorsName(String name, PageRequest pageRequest);


}
