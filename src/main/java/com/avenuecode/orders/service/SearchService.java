/**
 * 
 */
package com.avenuecode.orders.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.OrderRepository;
import com.avenuecode.orders.repository.ProductRepository;

/**
 * @author santhosh
 *
 */
@Service
public class SearchService {
	
	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
    private ProductRepository productRepository;

	public List<Order> getOrdersByStatus(String status) {
		return orderRepository.getOrdersByStatus(status);
	}

	public List<Order> getDiscountedOrders() {
		return orderRepository.getDiscountedOrders();
	}

	public List<Order> getMinProductOrders(int productCount) {
		return orderRepository.getMinProductOrders(productCount);
	}

	public List<Product> getMinPriceProducts(BigDecimal minPrice) {
		return productRepository.getMinPriceProducts(minPrice);
	}

}
