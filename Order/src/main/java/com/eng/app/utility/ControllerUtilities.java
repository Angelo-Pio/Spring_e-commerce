package com.eng.app.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eng.app.model.Cart;
import com.eng.app.model.Order;
import com.eng.app.model.OrderDetails;

@Component
public class ControllerUtilities {
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<Boolean> handleParametersException() {
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}

	public  ResponseEntity<Boolean> responseStatus(Boolean resp, HttpStatus success, HttpStatus failure) {
		if (resp == null || resp == false) {
			return new ResponseEntity<Boolean>(false, failure);
		} else {
			return new ResponseEntity<Boolean>(true, success);
		}
	}

	public Order buildOrder(@Valid List<Cart> carts) {

		
		ArrayList<OrderDetails> order_details_list = new ArrayList<>(carts.size());
		Float total = Float.valueOf(0);
		for (Cart cart : carts) {
			OrderDetails order_detail = OrderDetails.builder()
						.product_cost(cart.getProduct_cost())
						.product_name(cart.getProduct_name())
						.product_quantity(cart.getProduct_quantity())
						.product_id(cart.getProduct_id())
						.user_id(cart.getUser_id())
						.build();
			Float.sum(total,order_detail.getProduct_cost());
			order_details_list.add(order_detail);
		}
		
		Date timestamp = new Date();
		
		return Order.builder()
				.order_details(order_details_list)
				.total(total)
				.user_id(order_details_list.get(0).getUser_id())
				.placed_at(timestamp)
				.build();
	}
}
