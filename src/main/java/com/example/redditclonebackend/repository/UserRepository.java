package com.example.redditclonebackend.repository;

import com.example.redditclonebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
	The User Repository interface represents the Spring Data JPA depository which
	includes Entities in a Domain.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}