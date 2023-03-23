package com.eng.app.repository;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eng.app.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	@Modifying
	@Transactional
	@Query("update Cart c set c.product_quantity =  :quantity where c.id = :cart_id ")
	Integer updateCartQuantityById(@Param("cart_id") Integer cart_id, @Param("quantity") Integer quantity);

	
	@Transactional
	@Query(value = "select c from Cart c where c.user_id = :user_id")
	List<Cart> findAllByUserId(@Param("user_id") Integer user_id);

	

}
