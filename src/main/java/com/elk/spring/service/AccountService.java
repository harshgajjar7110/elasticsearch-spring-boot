package com.elk.spring.service;

import java.util.List;

import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import com.elk.spring.pojo.Accounts;
import com.elk.spring.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private ElasticsearchOperations template;

	@Autowired
	AccountRepository repository;

	public void check() {
		System.out.println(repository.count());
	}

	public List<Accounts> findByFirstName(String name) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchQuery("firstname", name).fuzziness("AUTO"));

		NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryBuilder);
		List<Accounts> users = template.queryForList(nativeSearchQuery, Accounts.class);
		return users;

	}

	public List<Accounts> findByFirstNameLastName(String name) {

		MultiMatchQueryBuilder builder = QueryBuilders.multiMatchQuery(name, "firstname", "lastname")
				.fuzziness(Fuzziness.AUTO);
		NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(builder);
		List<Accounts> users = template.queryForList(nativeSearchQuery, Accounts.class);
		return users;
	}
}
