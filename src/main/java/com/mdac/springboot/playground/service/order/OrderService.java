package com.mdac.springboot.playground.service.order;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.mdac.springboot.playground.entity.customer.CustomerEntity;
import com.mdac.springboot.playground.entity.order.OrderEntity;
import com.mdac.springboot.playground.entity.order.OrderItemEntity;
import com.mdac.springboot.playground.repository.customer.CustomerRepository;
import com.mdac.springboot.playground.repository.order.OrderItemRepository;
import com.mdac.springboot.playground.repository.order.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private EntityManager em;

	@Autowired
	private CustomerRepository customerRepository;

	public OrderEntity createOrder(String orderNumber, String sku, Integer quantity, BigDecimal amount) {

		OrderEntity order = new OrderEntity();
		OrderItemEntity item = new OrderItemEntity();
		item.setSku(sku);
		item.setQuantity(quantity);
		item.setAmount(amount);

		order.setOrderNumber(orderNumber);
		order.setOrderItems(Arrays.asList(item));

		orderRepository.save(order);

		return order;
	}

	public Optional<OrderEntity> addOrderItems(String orderNumber, String sku, Integer quantity, BigDecimal amount,
			@Nullable Integer repetitions) {

		Optional<OrderEntity> orderMaybe = getOrder(orderNumber);
		
		if(!orderMaybe.isPresent()) {
			return orderMaybe;
		}
		
		Integer resultingRepetitions = 1;
		if(repetitions != null && repetitions > 0) {
			resultingRepetitions = repetitions;
		}
		
		OrderEntity order = orderMaybe.get();
		
		IntStream.range(0, resultingRepetitions).forEach(x -> {
			
			OrderItemEntity newItem = new OrderItemEntity();
			newItem.setAmount(amount);
			newItem.setQuantity(quantity);
			newItem.setSku(sku);
			newItem.setOrder(order);
			
			order.setAmount(order.getAmount().add(amount));
			
			orderItemRepository.save(newItem);
			
		});
		
		orderRepository.save(order);
		
		return Optional.of(order);
		
	}

	@Transactional
	public CustomerEntity createCustomerOrder(String customerNumber, String orderNumber, BigDecimal amount) {

		CustomerEntity customer = getCustomer(customerNumber).orElseGet(() -> {
			CustomerEntity newCustomer = new CustomerEntity();
			newCustomer.setCustomerNumber(customerNumber);
			return customerRepository.save(newCustomer);
		});

		// Load the orders so fetching is performed
		// customer.getOrders();

		// Save the order bypassing customer entity

		OrderEntity order = new OrderEntity();
		order.setOrderNumber(orderNumber);
		order.setAmount(amount);
		order.setCustomer(customer);

		OrderItemEntity item = new OrderItemEntity();
		item.setQuantity(1);
		item.setSku("SKU");
		item.setAmount(amount);

		// order.setOrderItems(Collections.singleton(item));

		orderRepository.save(order);

		em.refresh(customer);

		return customer;

	}

	public Optional<CustomerEntity> getCustomer(String customerNumber) {

		List<CustomerEntity> customers = customerRepository.findByCustomerNumber(customerNumber);

		return CollectionUtils.isEmpty(customers) ? Optional.empty() : Optional.of(customers.get(0));

	}

	public Optional<OrderEntity> getOrder(String orderNumber) {

		List<OrderEntity> orders = orderRepository.findByOrderNumber(orderNumber);

		return CollectionUtils.isEmpty(orders) ? Optional.empty() : Optional.of(orders.get(0));

	}

	public List<OrderItemEntity> getOrderItems(String orderNumber) {

		List<OrderEntity> orders = orderRepository.findByOrderNumber(orderNumber);

		if (CollectionUtils.isEmpty(orders)) {
			return null;
		}

		return null;

	}

}
