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
import com.elk.spring.repository.AccountRepository;
import com.elk.spring.service.AccountService;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class AccountController {
	@Autowired
	ElasticsearchOperations esTemplate;
	@Autowired
	private AccountService service;

	@Autowired
	AccountRepository repo;

	@GetMapping("/find/{id}")
	public Accounts findById(@PathVariable int id) throws Exception {
		return repo.findById(id).orElse(new Accounts());

	}

	@GetMapping("findByFirstName/{name}")
	public List<Accounts> findByName(@PathVariable String name) throws Exception {

		return service.findByFirstName(name);
	}

	@GetMapping("/findByName/{name}")
	public List<Accounts> saveCustomer(@PathVariable String name) throws Exception {
		return service.findByFirstNameLastName(name);

	}
}
