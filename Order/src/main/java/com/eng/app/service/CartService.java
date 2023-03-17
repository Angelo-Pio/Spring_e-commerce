package com.eng.app.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eng.app.model.Cart;
import com.eng.app.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	CartRepository repo;
	
	public Boolean create(@Valid Cart cart) {
		// TODO Auto-generated method stub
		
		 repo.save(cart);
		 return true;
	}

	public List<Cart> showAll(Integer user_id) {
		// TODO Auto-generated method stub
		
		
		
		
		return repo.findAllByUserId(user_id);
	}

	public Boolean delete(Integer cart_id) {
		// TODO Auto-generated method stub
		Optional<Cart> cart = repo.findById(cart_id);
		if(cart.isEmpty() == true) {
			return false;
		}
		
		repo.delete(cart.get());
		return true;
	}

	public Boolean increaseQuantity(@Valid Integer cart_id, Integer quantity) {
		// TODO Auto-generated method stub
		Optional<Cart> cart = repo.findById(cart_id);
		if(cart.isEmpty() == true) {
			return false;
		}
		if(quantity < 0 ) {
			return false;
		}
		
		
		return repo.updateCartQuantityById(cart_id, quantity) > 0 ? true : false;
	}

}
