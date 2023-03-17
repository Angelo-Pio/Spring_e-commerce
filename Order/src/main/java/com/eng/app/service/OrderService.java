package com.eng.app.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eng.app.model.Cart;
import com.eng.app.model.Order;
import com.eng.app.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository repo;
	
	public List<Order> showAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	public Boolean create(@Valid Order order) {
		// TODO Auto-generated method stub
		repo.save(order);
		return true;
	}

	public Boolean addCartById(@Valid Cart cart, Integer order_id) {
		
		if(repo.findById(order_id).isEmpty()) {
			return false;
		}
		
		Integer res = repo.insertCartById(cart,order_id);
		
		
		return res == 0 ? true : false;
	}

}
