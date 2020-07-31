package com.elk.spring.service;

import java.util.List;

import org.apache.lucene.search.TermQuery;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import com.elk.spring.pojo.Accounts;
import com.elk.spring.repository.AccountRepository;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

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
				.must(QueryBuilders.matchQuery("firstname", name).fuzziness(Fuzziness.AUTO));

		return generateResult(queryBuilder);

	}

	public List<Accounts> findByFirstNameLastName(String name) {
		// find query on multiple fields
		MultiMatchQueryBuilder builder = QueryBuilders.multiMatchQuery(name, "firstname", "lastname")
				.fuzziness(Fuzziness.AUTO);

		return generateResult(builder);
	}

	public List<Accounts> wildCardQuery(String field, String name) {
		// wildcard query
		WildcardQueryBuilder wildcardQuery = QueryBuilders.wildcardQuery(field, name + "*");
		return generateResult(wildcardQuery);
	}

	public List<Accounts> getResultByFilter(double from, double to) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()

				.filter(QueryBuilders.rangeQuery("balance").gt(from).lt(to));

		return generateResult(queryBuilder);

	}

	public List<Accounts> disMaxQuery(String name) {
		// condition 1 or condition 2
		DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery().add(termQuery("firstname", name))
				.add(termQuery("lastname", name));

		return generateResult(disMaxQueryBuilder);
	}

	private List<Accounts> generateResult(org.elasticsearch.index.query.QueryBuilder builder) {
		NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(builder);

		return template.queryForList(nativeSearchQuery, Accounts.class);
	}
}
