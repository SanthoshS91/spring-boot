/**
 * 
 */
package com.avenuecode.orders.service;

import static org.junit.Assert.*;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.OrderRepository;

/**
 * @author santhosh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

	/*@Autowired
    private OrderService orderService;*/
	
	@Mock
    private OrderRepository orderRepository;
	
	@InjectMocks
	private OrderService orderService;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testfindAllOrders(){
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(setUpOrder1());
		orderList.add(setUpOrder2());
		orderList.add(setUpOrder3());
		when(orderRepository.findAll()).thenReturn(orderList);
		
		List<Order> result = orderService.listOrders();
		assertEquals(3, result.size());
		Order ord = result.get(0);
		Object obj = ord.getGrandTotal();
		assertTrue(obj instanceof BigDecimal);
	}
	
	@Test
	public void testfindOneByOrderId(){
		Order ord = setUpOrder1();
		when(orderRepository.findOne("1")).thenReturn(ord);
		Order result = orderService.getOrder("1");
		assertEquals("1", result.getOrderId());
		assertEquals(false, result.getProducts().get(0).equals(result));
		assertEquals(false, result.equals(ord.getProducts().get(0)));
		assertEquals(2, result.getProducts().size());
		assertEquals("Shipped", result.getStatus());
		assertEquals(true, result.canEqual(ord));
		assertEquals(true, result.equals(result));
		Object obj = result.hashCode();
		assertTrue(obj instanceof Integer);
		assertTrue(result.toString().contains("orderId"));
	}
	
	@Test
	public void testPojoOrderIdNull(){
		Order ord = setUpOrder1();
		when(orderRepository.findOne("1")).thenReturn(ord);
		Order result = orderService.getOrder("1");
		ord.setOrderId(null);
		
		Electronics elc = new Electronics();
		elc.setOrderId("1");		
		assertEquals(false, result.equals(elc));		
	}
	
	@Test
	public void testPojoOrderNumberNull(){
		Order ord = setUpOrder1();
		when(orderRepository.findOne("1")).thenReturn(ord);
		Order result = orderService.getOrder("1");
		ord.setOrderId("1");
		ord.setOrderNumber(null);
		Electronics elc = new Electronics();
		elc.setOrderId("1");
		elc.setOrderNumber("123");
		assertEquals(false, result.equals(elc));		
	}
	
	@Test
	public void testPojoDiscountNull(){
		Order ord = setUpOrder1();
		when(orderRepository.findOne("1")).thenReturn(ord);
		Order result = orderService.getOrder("1");
		ord.setOrderId("1");
		ord.setOrderNumber("123");
		ord.setDiscount(null);
		Electronics elc = new Electronics();
		elc.setOrderId("1");
		elc.setOrderNumber("123");
		elc.setDiscount(new BigDecimal("20"));
		assertEquals(false, result.equals(elc));		
	}
	
	@Test
	public void testPojoTaxNull(){
		Order ord = setUpOrder1();
		when(orderRepository.findOne("1")).thenReturn(ord);
		Order result = orderService.getOrder("1");
		ord.setOrderId("1");		
		ord.setOrderNumber("123");
		ord.setDiscount(new BigDecimal("20"));
		ord.setTaxPercent(null);
		Electronics elc = new Electronics();
		elc.setOrderId("1");
		elc.setOrderNumber("123");
		elc.setDiscount(new BigDecimal("20"));
		ord.setTaxPercent(new BigDecimal("40"));
		assertEquals(false, result.equals(elc));		
	}
	
	@Test
	public void testPojoTotalNull(){
		Order ord = setUpOrder1();
		when(orderRepository.findOne("1")).thenReturn(ord);
		Order result = orderService.getOrder("1");
		ord.setOrderId("1");
		ord.setOrderNumber("123");
		ord.setDiscount(new BigDecimal("20"));
		ord.setTaxPercent(new BigDecimal("40"));
		ord.setTotal(null);
		
		Electronics elc = new Electronics();
		elc.setOrderId("1");
		elc.setOrderNumber("123");
		elc.setDiscount(new BigDecimal("20"));
		elc.setTaxPercent(new BigDecimal("40"));
		elc.setTotal(new BigDecimal("60"));
		assertEquals(false, result.equals(elc));		
	}
	
	@Test
	public void testPojoTotalTaxNull(){
		Order ord = setUpOrder1();
		when(orderRepository.findOne("1")).thenReturn(ord);
		
		Order result = orderService.getOrder("1");
		ord.setOrderId("1");
		ord.setOrderNumber("123");
		ord.setDiscount(new BigDecimal("20"));
		ord.setTaxPercent(new BigDecimal("40"));
		ord.setTotal(new BigDecimal("70"));
		
		Electronics elc = new Electronics();
		List<Product> pds = new ArrayList<Product>();
		Product pd = new Product();
		pd.setPrice(new BigDecimal("50"));
		pds.add(pd);
		elc.setProducts(pds);
		elc.setOrderId("1");
		elc.setOrderNumber("123");
		elc.setDiscount(new BigDecimal("20"));
		elc.setTaxPercent(new BigDecimal("40"));
		//elc.setTotal(new BigDecimal("10"));
		assertEquals(false, result.equals(elc));		
	}
	
	
	@Test
	public void testPojoGrandTotNull(){
		Order ord = setUpOrder1();
		when(orderRepository.findOne("1")).thenReturn(ord);
		Order result = orderService.getOrder("1");
		ord.setOrderId("1");
		ord.setOrderNumber("123");
		ord.setDiscount(null);
		ord.setTaxPercent(new BigDecimal("40"));
		
		ord.setTotal(null);
		ord.setTotalTax(new BigDecimal("80"));
		ord.setGrandTotal(null);
		
		Electronics elc = new Electronics();
		elc.setOrderId("1");
		elc.setOrderNumber("123");
		elc.setDiscount(new BigDecimal("20"));
		elc.setTaxPercent(new BigDecimal("40"));
		
		elc.setTotal(new BigDecimal("60"));
		elc.setTotalTax(new BigDecimal("80"));
		elc.setGrandTotal(new BigDecimal("100"));
		assertEquals(false, result.equals(elc));
		
	}
	
	@Test
	public void testPojoStatusNull(){
		Order ord = setUpOrder1();
		when(orderRepository.findOne("1")).thenReturn(ord);
		Order result = orderService.getOrder("1");
		ord.setOrderId("1");
		ord.setOrderNumber("123");
		ord.setDiscount(null);
		ord.setTaxPercent(new BigDecimal("40"));
		
		ord.setTotal(null);
		ord.setTotalTax(new BigDecimal("80"));
		ord.setGrandTotal(null);
		ord.setStatus(null);
		
		Electronics elc = new Electronics();
		elc.setOrderId("1");
		elc.setOrderNumber("123");
		elc.setDiscount(new BigDecimal("20"));
		elc.setTaxPercent(new BigDecimal("40"));
		
		elc.setTotal(new BigDecimal("60"));
		elc.setTotalTax(new BigDecimal("80"));
		elc.setGrandTotal(new BigDecimal("100"));
		elc.setStatus("Shipped");
		boolean status = result.equals(elc);
		assertEquals(false, status);
		
	}
	
	
	
	
	@SuppressWarnings("serial")
	class Electronics extends Order{
	
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
		ord.setDiscount(null);
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
