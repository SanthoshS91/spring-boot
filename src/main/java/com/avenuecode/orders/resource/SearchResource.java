/**
 * 
 */
package com.avenuecode.orders.resource;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.math.BigDecimal;
import java.util.List;


import javax.validation.constraints.NotNull;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.service.SearchService;

/**
 * @author santhosh
 *
 */
@RestController
@RequestMapping("/search")
public class SearchResource {
	
	@Autowired
    private SearchService searchService;
	
	final static Logger logger = LogManager.getLogger(SearchResource.class);
	
	public enum OrderStatus {
	    shipped,
	    fulfilled
	}
	
	/*
	 * Method getOrdersByStatus -  To return the all the orders whose status(shipped/fulfilled) is inputed from the user
	 * url patter /search/orders/{status} status=shipped or fulfilled
	 * returns the list of orders if successfully fetched from db
	 */
	@GetMapping("/orders/{status}")
	public ResponseEntity<List<Order>> getOrdersByStatus(@NotNull @PathVariable("status") String status) {
		logger.info("Inside getOrdersByStatus method of SearchResource");
		
		if(isInvalidOrderStatus(status)) {
			throw new IllegalArgumentException("The status parameter should either be shipped or fulfilled");
		}
		
		List<Order> orders = null;
		try {
			orders = searchService.getOrdersByStatus(status.toUpperCase());
		}catch(Exception ex) {
			logger.error("Exception caught: "+ex);
			return notFound().build();
		}
        return ok(orders);
    }


	/**
	 * @param status
	 * @return
	 */
	private boolean isInvalidOrderStatus(String status) {
		return !(status.equalsIgnoreCase(OrderStatus.shipped.toString())  || status.equalsIgnoreCase(OrderStatus.fulfilled.toString()));
	}
	
	
	/*
	 * Method getDiscountedOrders -  To return the all the orders that have discount
	 * url patter /search//orders/discounted
	 * returns the list of orders if successfully fetched from db
	 */
	@GetMapping("/orders/discounted")
	public ResponseEntity<List<Order>> getDiscountedOrders() {
		logger.info("Inside getDiscountedOrders method of SearchResource");
		List<Order> orders = null;
		try {
			orders = searchService.getDiscountedOrders();
		}catch(Exception ex) {
			logger.error("Exception caught: "+ex);
			return notFound().build();
		}
        return ok(orders);
    }
	
	/*
	 * Method getMinProductOrders -  To return the all the orders whose minimum product count is minProduct (2) inputted from the user
	 * url patter /search/orders?productCount=2
	 * returns the list of orders if successfully fetched from db
	 */
	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getMinProductOrders(@NotNull @RequestParam("productCount") int productCount) {
		logger.info("Inside getMinProductOrders method of SearchResource");
		if (productCount < 0) {
			logger.error("The productCount parameter cannot be less than zero");
            throw new IllegalArgumentException("The productCount parameter cannot be less than zero");
        }
		List<Order> orders = null;
		try {
			orders = searchService.getMinProductOrders(productCount);
		}catch(Exception ex) {
			logger.error("Exception caught: "+ex);
			return notFound().build();
		}
        return ok(orders);
    }
	
	/*
	 * Method getMinPriceProducts -  To return the all the products whose minimum product price is minPrice (30) inputted from the user
	 * url patter /search/products?minPrice=30
	 * returns the list of products if successfully fetched from db
	 */
	@GetMapping("/products")
    public ResponseEntity<List<Product>> getMinPriceProducts(@NotNull @RequestParam(value = "minPrice") BigDecimal minPrice) {
		logger.info("Inside getMinPriceProducts method of SearchResource");
		if (null == minPrice || minPrice.compareTo(BigDecimal.ZERO) < 0) {
			logger.error("The minPrice parameter cannot be less than zero or empty");
            throw new IllegalArgumentException("The minPrice parameter cannot be less than zero or empty");
        }
		List<Product> product = null;
		try {
			product = searchService.getMinPriceProducts(minPrice);
		}catch(Exception ex) {
			logger.error("Exception caught: "+ex);
			return notFound().build();
		}
        return ok(product);
    }
}
