package com.elk.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import com.elk.spring.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private ElasticsearchOperations template;

	@Autowired
	UserRepository repository;

	public void check() {
		System.out.println(repository.count());
	}
}
