package com.mdac.springboot.playground.repository.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdac.springboot.playground.entity.order.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	
	List<OrderEntity> findByOrderNumber(String orderNumber);
	
	//List<OrderItemEntity> findByOrder(OrderEntity order);
	
}