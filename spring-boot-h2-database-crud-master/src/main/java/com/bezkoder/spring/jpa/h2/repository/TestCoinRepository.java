package com.bezkoder.spring.jpa.h2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.h2.model.TestCoin;


public interface TestCoinRepository extends JpaRepository<TestCoin, Long> {
//  List<TestCoin> findByPublished();
//
	TestCoin findByCoinName(String coinName);
}
