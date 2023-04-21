package com.ksh.demoes.repository;

import com.ksh.demoes.bean.Blog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BlogRepository extends ElasticsearchRepository<Blog, Long> {

}