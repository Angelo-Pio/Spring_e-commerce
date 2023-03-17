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

	
}
