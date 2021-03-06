package com.elk.spring.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.elk.spring.pojo.Accounts;

@EnableElasticsearchRepositories
public interface AccountRepository extends ElasticsearchRepository<Accounts, Integer> {

}
