package com.eng.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.eng.app.model.Cart;
import com.eng.app.model.Order;
import com.eng.app.model.OrderDetails;
import com.eng.app.repository.CartRepository;
import com.eng.app.repository.OrderDetailsRepository;
import com.eng.app.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

	@Value("${com.api.url}")
	private String api;
	
	@Autowired
	OrderRepository repo;
	
	@Autowired
	OrderDetailsRepository repo_details;
	
	@Autowired
	CartRepository repo_cart;

	/**
	 * Debug only
	 * @return
	 */
	public List<Order> showAll() {
		return repo.findAll();
	}

	public Boolean create(@Valid Integer user_id) {
		log.info("extract cart info");
		List<Cart> carts = repo_cart.findAllByUserId(user_id);
		
		log.info("building order: " + carts.toString());
		Order order = buildOrder(carts);
		if (order == null) {
			return false;
		}
		log.info("update inventory");
		boolean resp = updateInventory(order);
		if (resp == false) {
			log.info("error with inventory");
			return false;
		}

		log.info("everything ok, placing order");
		Order response = repo.save(order);
		
		log.info("clearing cart");
		clearCart(user_id);
		
		return true;
	}

	private void clearCart(@Valid Integer user_id) {
		repo_cart.deleteByUserId(user_id);
	}

	/**
	 * This method update the products in Product Service's db
	 * TODO this method should be more robust in order to rollback on errors from the request
	 * @param order
	 * @return
	 */
	private boolean updateInventory(Order order) {
		
		String url ="http://localhost:8080/product/decreaseQuantity";
		List<OrderDetails> details = order.getOrder_details();
		
		for (OrderDetails d : details) {
			log.info("Updating product:" + d.getProduct_name());
			WebClient webClient = WebClient.builder().baseUrl(api).build();
			Boolean response = webClient.patch().uri( q -> 
					q.path("/decreaseQuantity")
					.queryParam("quantity", d.getProduct_quantity())
					.queryParam("name", d.getProduct_name()).build()
					)
			.retrieve()
			.bodyToMono(Boolean.class)
			.onErrorReturn(false)
			.block();
			if(response != null && response.booleanValue() == false) {
				return false;
			}
		}
		return true;
		
		
	}


	private Order buildOrder(@Valid List<Cart> carts) {
		if(carts.isEmpty()) return null;
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

	/**
	 * Show all orders placed by a specific user
	 * @param user_id
	 * @return
	 */
	public List<Order> showAll(Integer user_id) {
		// TODO Auto-generated method stub
		
		if(user_id == null) return null;
		
		return repo.findAllByUserId(user_id);
	}

}
