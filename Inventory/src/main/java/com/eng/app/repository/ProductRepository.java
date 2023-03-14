package com.eng.app.repository;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eng.app.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	Optional<Product> getByName(String name);

	@Query(value = "delete from Product p where p.name = :name")
	@Modifying
	@Transactional
	void deleteByName( @Param("name") String name);

	Optional<Product> findByName(String name);

	boolean existsByName(String name);

	@Query(value = "update Product p set p.description = ?1")
	@Modifying
	@Transactional
	void updateDescription(String description);

	@Query(value = "update Product p set p.category = ?1")
	@Modifying
	@Transactional
	void updateCategory(String category);

	@Query(value = "update Product p set p.cost= ?1")
	@Modifying
	@Transactional
	void updateCost(BigDecimal cost);

	@Query(value = "update Product p set p.quantity= ?1")
	@Modifying
	@Transactional
	void updateQuantity(Integer quantity);

	@Query(value = "update Product p set p.quantity = p.quantity - ?2 where p.name = ?1")
	@Modifying
	@Transactional
	Integer decreaseByName(String name, Integer q);

	@Query(value = "update Product p set p.quantity = p.quantity + ?2 where p.name = ?1")
	@Modifying
	@Transactional
	Integer increaseByName(String name, Integer q);


	
}
