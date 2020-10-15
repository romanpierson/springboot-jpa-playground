package com.mdac.springboot.playground.web.controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mdac.springboot.playground.entity.customer.CustomerEntity;
import com.mdac.springboot.playground.entity.order.OrderEntity;
import com.mdac.springboot.playground.entity.order.OrderItemEntity;
import com.mdac.springboot.playground.service.order.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders/{orderNumber}")
	public OrderEntity getOrderByOrderNumber(@PathVariable final String orderNumber) {

		return orderService.getOrder(orderNumber)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));

	}

	@PostMapping("/orders")
	public ResponseEntity<OrderEntity> createOrder(@RequestParam @Size(min = 1, max = 10) String orderNumber,
			@RequestParam @Size(min = 1, max = 10) String sku,
			@RequestParam @Size(min = 1, max = 1000) Integer quantity,
			@RequestParam @Size(min = 1, max = 10) BigDecimal amount) {

		return ResponseEntity.ok(orderService.createOrder(orderNumber, sku, quantity, amount));

	}

	@PostMapping("/orders/items")
	public OrderEntity addOrderItems(@RequestParam @Size(min = 1, max = 10) String orderNumber,
			@RequestParam @Size(min = 1, max = 10) String sku,
			@RequestParam @Size(min = 1, max = 1000) Integer quantity,
			@RequestParam @Size(min = 1, max = 10) BigDecimal amount,
			@RequestParam @Size(min = 1, max = 1000) Integer repetitions) {

		return orderService.addOrderItems(orderNumber, sku, quantity, amount, repetitions)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));

	}

	// @GetMapping("/orderitems/{orderNumber}")
	public List<OrderItemEntity> getOrderItemsByOrderNumber(@PathVariable final String orderNumber) {

		return orderService.getOrderItems(orderNumber);

	}

	// @GetMapping("/customer/order/create/{customerNumber}/{orderNumber}/{amount}")
//	public CustomerEntity createCustomerOrder(@PathVariable final String customerNumber,
//			@PathVariable final String orderNumber, @PathVariable final BigDecimal amount) {
//
//		OrderEntity order = orderService.getOrder(orderNumber);
//
//		if (order != null) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Order Number already exists");
//		}
//
//		return orderService.createCustomerOrder(customerNumber, orderNumber, amount);
//
//		// return getCustomer(customerNumber);
//	}

	// @GetMapping("/customer/{customerNumber}")
	public CustomerEntity getCustomer(@PathVariable final String customerNumber) {

		return orderService.getCustomer(customerNumber)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));

	}

//	@GetMapping("/orders/add/{orderNumber}/{amount}/{repetitions}")
//	public OrderEntity addOrderItem(@PathVariable final String orderNumber, @PathVariable final BigDecimal amount,
//			@PathVariable final Integer repetitions) {
//
//		OrderEntity order = orderService.getOrder(orderNumber);
//
//		if (order == null) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Order Number does not exists");
//		}
//		
//		// We clear the items as we dont want to return them explicitely
//		OrderEntity resultingOrder = orderService.addOrderEntry(orderNumber, amount, repetitions);
//		resultingOrder.setOrderItems(null);
//		
//		return resultingOrder;
//	}

}
