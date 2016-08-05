package com.kmeisl.schichtkalender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kmeisl.schichtkalender.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
