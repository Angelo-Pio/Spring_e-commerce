package com.eng.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eng.app.model.Cart;
import com.eng.app.model.Order;
import com.eng.app.model.OrderDetails;
import com.eng.app.repository.OrderDetailsRepository;
import com.eng.app.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository repo;
	
	@Autowired
	OrderDetailsRepository repo_details;

	public List<Order> showAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	public Boolean create(@Valid List<Cart> carts) {
		// TODO Auto-generated method stub
		Order order = buildOrder(carts);
		repo.save(order);
		return true;
	}

//	public Boolean addCartById(@Valid Cart cart, Integer order_id) {
//
//		if (repo.findById(order_id).isEmpty()) {
//			return false;
//		}
//
//		Integer res = repo.insertCartById(cart, order_id);
//
//		return res == 0 ? true : false;
//	}

	private Order buildOrder(@Valid List<Cart> carts) {

		ArrayList<OrderDetails> order_details_list = new ArrayList<>(carts.size());
		Float total = Float.valueOf(0);
		
		for (Cart cart : carts) {
			OrderDetails order_detail = OrderDetails.builder()
					.product_cost(cart.getProduct_cost())
					.product_name(cart.getProduct_name())
					.product_quantity(cart.getProduct_quantity())
					.product_id(cart.getProduct_id())
					.user_id(cart.getUser_id()).build();
			order_detail = repo_details.save(order_detail);
			total = Float.sum(total, order_detail.getProduct_cost());
			order_details_list.add(order_detail);
		}

		Date timestamp = new Date();
		
		BigDecimal dec = new BigDecimal(total).setScale(2, RoundingMode.CEILING);
		
		
		return Order.builder()
				.order_details(order_details_list)
				.total(Float.valueOf(dec.toString()))
				.user_id(order_details_list.get(0).getUser_id())
				.placed_at(timestamp).build();
	}

}
