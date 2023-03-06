package com.eng.app.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eng.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	boolean existsByName(String name);

	Optional<User> findByName(String name);

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	@Modifying
	@Transactional
	@Query( value = "update User u set u.hashed_password = :newPassword")
	Integer updateUserPassword(@Param("newPassword")String newPassword);

}
