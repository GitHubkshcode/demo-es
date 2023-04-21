package com.ksh.demoes.service.impl;

import com.ksh.demoes.bean.Article;
import com.ksh.demoes.repository.ArticleRepository;
import com.ksh.demoes.service.ArticleServices;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Slf4j
@Service
public class ArticleServicesImpl implements ArticleServices {
    @Resource
    private ArticleRepository articleRepository;

    @Resource
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public Article save(Article article) {
        try {
            return articleRepository.save(article);
        } catch (Exception e) {
            log.error("保存失败：", e);
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        if (StringUtils.isEmpty(id))
            return false;
        try {
            Article article = new Article();
            article.setId(id);
            articleRepository.delete(article);
            return true;
        } catch (Exception e) {
            log.error("删除失败：", e);
            return false;
        }
    }


    @Override
    public Page<Article> findByAuthorsName(String name, PageRequest pageRequest) {
        if (pageRequest == null) {
            pageRequest = PageRequest.of(0, 10);
        }
        return articleRepository.findByAuthorsName(name, pageRequest);
    }

    @Override
    public boolean updateTitle(String oldTitle, String newTitle) {
        Query searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", oldTitle).minimumShouldMatch("75%"))
                .build();

        SearchHits<Article> articles =
                elasticsearchOperations.search(searchQuery, Article.class, IndexCoordinates.of("blog"));
        long totalHits = articles.getTotalHits();
        if (totalHits > 0) {
            Article article = articles.getSearchHit(0).getContent();
            article.setTitle(newTitle);
            articleRepository.save(article);
            return true;
        } else {
            return false;
        }
    }
}
