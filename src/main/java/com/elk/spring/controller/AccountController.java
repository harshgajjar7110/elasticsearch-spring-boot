package com.elk.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.elk.spring.pojo.Accounts;
import com.elk.spring.repository.AccountRepository;
import com.elk.spring.service.AccountService;

@RestController

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

	@GetMapping("/find/{field}/{name}")
	public List<Accounts> wildCardQuery(@PathVariable String field, @PathVariable String name) throws Exception {
		return service.wildCardQuery(field, name);

	}

	@GetMapping("/findDisQuery/{name}")
	public List<Accounts> disMaxQuery(@PathVariable String name) throws Exception {
		return service.disMaxQuery(name);

	}

	@GetMapping("/filterByBalance/{from}/{to}")
	public List<Accounts> filterByBalance(@PathVariable double from, @PathVariable double to) throws Exception {
		return service.getResultByFilter(from, to);

	}
}
