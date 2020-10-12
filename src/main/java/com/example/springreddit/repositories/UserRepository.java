package com.example.springreddit.repositories;

import com.example.springreddit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
	The User Repository interface represents the Spring Data JPA depository which
	includes Entities in a Domain.
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}