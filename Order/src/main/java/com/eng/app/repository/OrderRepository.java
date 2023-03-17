package com.eng.app.repository;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eng.app.model.Cart;
import com.eng.app.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	
}
