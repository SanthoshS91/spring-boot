package com.avenuecode.orders.repository;

import com.avenuecode.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Serializable> {
	@Query(value="SELECT * FROM orders o WHERE o.status = :status", nativeQuery=true)
	List<Order> getOrdersByStatus(@Param("status") String status);

	@Query(value="SELECT * FROM orders o WHERE o.discount is not null", nativeQuery=true)
	List<Order> getDiscountedOrders();

	@Query(value="SELECT * FROM orders o WHERE (SELECT count(*) from order_product p WHERE o.order_id = p.order_id) > :productCount", nativeQuery=true)
	List<Order> getMinProductOrders(@Param("productCount") int productCount);
}
