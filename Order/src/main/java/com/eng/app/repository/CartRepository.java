package com.eng.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eng.app.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}
