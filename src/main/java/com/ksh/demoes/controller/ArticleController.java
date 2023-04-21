package com.ksh.demoes.controller;

import com.ksh.demoes.bean.Article;
import com.ksh.demoes.common.AjaxResult;
import com.ksh.demoes.service.ArticleServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("article")
public class ArticleController {

    @Resource
    private ArticleServices articleServices;

    @PostMapping("save")
    public AjaxResult updateTitle(@RequestBody Article article) {
        Article save = articleServices.save(article);
        return AjaxResult.success(save);
    }

    @DeleteMapping("delete/{id}")
    public AjaxResult delete(@PathVariable String id) {
        boolean delete = articleServices.delete(id);
        if (delete) {
            return AjaxResult.success("删除成功");
        } else {
            return AjaxResult.error("删除失败");
        }
    }

    @PutMapping("updateTitle")
    public AjaxResult updateTitle(String oldTitle, String newTitle) {
        boolean update = articleServices.updateTitle(oldTitle, newTitle);
        if (update) {
            return AjaxResult.success("修改成功");
        } else {
            return AjaxResult.error("修改失败");
        }
    }

    @GetMapping("findByAuthorsName")
    public AjaxResult findByAuthorsName(String name, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        Page<Article> articles = articleServices.findByAuthorsName(name, pageRequest);
        return AjaxResult.success(articles);
    }
}
