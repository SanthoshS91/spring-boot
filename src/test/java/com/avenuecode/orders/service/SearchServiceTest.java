/**
 * 
 */
package com.avenuecode.orders.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.OrderRepository;
import com.avenuecode.orders.repository.ProductRepository;

/**
 * @author santhosh
 *
 */
@RunWith(SpringRunner.class)
public class SearchServiceTest {
	
	@Mock
    private OrderRepository orderRepository;
	
	@Mock
    private ProductRepository productRepository;
	
	@InjectMocks
	private SearchService searchService;
	
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testgetOrdersByStatus() {
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(setUpOrder1());
		orderList.add(setUpOrder2());
		when(orderRepository.getOrdersByStatus("Shipped")).thenReturn(orderList);
		
		List<Order> result = searchService.getOrdersByStatus("Shipped");
		assertEquals(2, result.size());
	}
	
	@Test
	public void testgetDiscountedOrders() {
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(setUpOrder1());
		orderList.add(setUpOrder2());
		orderList.add(setUpOrder3());
		when(orderRepository.getDiscountedOrders()).thenReturn(orderList);
		
		List<Order> result = searchService.getDiscountedOrders();
		assertEquals(3, result.size());
	}

	@Test
	public void getMinProductOrders() {
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(setUpOrder1());
		orderList.add(setUpOrder2());
		orderList.add(setUpOrder3());
		when(orderRepository.getMinProductOrders(2)).thenReturn(orderList);
		
		List<Order> result = searchService.getMinProductOrders(2);
		assertEquals(3, result.size());
	}
	
	@Test
	public void getMinPriceProducts() {
		List<Product> pd = new ArrayList<Product>();
		pd.add(setupProd1());
		pd.add(setupProd2());
		pd.add(setupProd3());
		when(productRepository.getMinPriceProducts(new BigDecimal("20"))).thenReturn(pd);
		
		List<Product> result = searchService.getMinPriceProducts(new BigDecimal("20"));
		assertEquals(3, result.size());
	}
	
	
	private Product setupProd1() {
		Product pd = new Product();
		pd.setSku("121323");
		pd.setProductId("1");
		pd.setUpc("132424");
		BigDecimal bd = new BigDecimal("20");
		pd.setPrice(bd);
		pd.setDescription("PD1");
		return pd;
	}
	
	private Product setupProd2() {
		Product pd = new Product();
		pd.setSku("1214232433");
		pd.setProductId("2");
		pd.setUpc("1324242424");
		BigDecimal bd = new BigDecimal("30");
		pd.setPrice(bd);
		pd.setDescription("PD2");
		return pd;
	}
	
	private Product setupProd3() {
		Product pd = new Product();
		pd.setSku("1213232345");
		pd.setProductId("3");
		pd.setUpc("1324243124");
		BigDecimal bd = new BigDecimal("40");
		pd.setPrice(bd);
		pd.setDescription("PD3");
		return pd;
	}

	private Order setUpOrder1() {
		Order ord = new Order();
		BigDecimal bd = new BigDecimal("15.99");
		ord.setDiscount(bd);
		ord.setOrderNumber("1");
		ord.setStatus("Shipped");
		bd = new BigDecimal("100");
		ord.setGrandTotal(bd);
		ord.setOrderId("1");
		bd = new BigDecimal("5.0");
		ord.setTaxPercent(bd);
		bd = new BigDecimal("12.45");
		ord.setTotal(bd);
		bd = new BigDecimal("22.45");
		ord.setTotalTax(bd);
		
		List<Product> pd = new ArrayList<Product>();
		pd.add(setupProd1());
		pd.add(setupProd2());
		ord.setProducts(pd);
		return ord;
	}
	
	private Order setUpOrder2() {
		Order ord = new Order();
		BigDecimal bd = new BigDecimal("25.99");
		ord.setDiscount(bd);
		ord.setOrderNumber("2");
		ord.setStatus("Shipped");
		bd = new BigDecimal("200");
		ord.setGrandTotal(bd);
		ord.setOrderId("2");
		bd = new BigDecimal("10.0");
		ord.setTaxPercent(bd);
		bd = new BigDecimal("22.45");
		ord.setTotal(bd);
		bd = new BigDecimal("44.45");
		ord.setTotalTax(bd);
		
		List<Product> pd = new ArrayList<Product>();
		pd.add(setupProd1());
		pd.add(setupProd2());
		pd.add(setupProd3());
		return ord;
	}
	
	private Order setUpOrder3() {
		Order ord = new Order();
		BigDecimal bd = new BigDecimal("35.99");
		ord.setDiscount(bd);
		ord.setOrderNumber("3");
		ord.setStatus("Fulfilled");
		bd = new BigDecimal("400");
		ord.setGrandTotal(bd);
		ord.setOrderId("3");
		bd = new BigDecimal("20.0");
		ord.setTaxPercent(bd);
		bd = new BigDecimal("42.45");
		ord.setTotal(bd);
		bd = new BigDecimal("84.45");
		ord.setTotalTax(bd);
		
		List<Product> pd = new ArrayList<Product>();
		pd.add(setupProd1());
		return ord;
	}

}
