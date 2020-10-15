package com.mdac.springboot.playground.repository.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdac.springboot.playground.entity.customer.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
	
	List<CustomerEntity> findByCustomerNumber(String customerNumber);
	
	//List<OrderItemEntity> findByOrder(OrderEntity order);
	
}