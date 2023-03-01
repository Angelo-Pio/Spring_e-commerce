package com.eng.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eng.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	boolean existsByName(String name);

	Optional<User> findByName(String name);

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

}
