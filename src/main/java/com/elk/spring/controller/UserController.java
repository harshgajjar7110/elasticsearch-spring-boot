package com.elk.spring.controller;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.elk.spring.pojo.Accounts;
import com.elk.spring.repository.UserRepository;
import com.elk.spring.service.UserService;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class UserController {
	@Autowired
	ElasticsearchOperations esTemplate;
	@Autowired
	private UserService service;

	@Autowired
	UserRepository repo;

	@GetMapping("/find/{id}")
	public Accounts saveCustomer(@PathVariable int id) throws Exception {

//		
		return repo.findById(id).orElse(new Accounts());

	}

	@GetMapping("findByName/{name}")
	public List<Accounts> findByName(@PathVariable String name) throws Exception {
		RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("balance");

		QueryBuilder queryBuilder =

//				--------------------------------------prefix query --------------------------------------
//				QueryBuilders.matchPhrasePrefixQuery("name", "harsh")

//				-------------------------------------wild card query-------------------------------------
//				QueryBuilders.wildcardQuery("firstname", name + "*")

				QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("firstname", "hard").fuzziness("AUTO"))
						.filter(rangeQuery.gt(2000))

		;
		NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryBuilder);
		List<Accounts> users = esTemplate.queryForList(nativeSearchQuery, Accounts.class);
		System.out.println(nativeSearchQuery.getQuery().toString());
		return users;
	}
}
