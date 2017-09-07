/**
 * 
 */
package com.avenuecode.orders.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.service.SearchService;

/**
 * @author santhosh
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SearchResource.class)
@AutoConfigureMockMvc(secure=false)
public class SearchResourceTest {
	
	private MockMvc mockMvc;
	
	Product pd;
	
	Order order;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
    private SearchResource controller;

	@MockBean
    private SearchService searchServiceMock;
	
	@InjectMocks
	private SearchResource srchResourceMock;
	
	
	@Before
    public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Before
	public void setupOrders(){
		order = setUpOrder1();
	}
	
	@Before
	public void setupProducts(){
		pd = setupProd1();
	}
	
	@Test
    public void controllerInitialization() throws Exception {
        assertThat(controller).isNotNull();
    }
    
    @Test
    public void testListOrders() throws Exception {
    	assertThat(this.searchServiceMock).isNotNull();
    }
    
       
    @Test
	public void testGetOrdersByStatus() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.get("/search/orders/{status}","shipped")).andExpect(status().isOk()).andDo(print()).andReturn();
    }
	
  
    
    @Test
   	public void testVerifyOrder_Exception() throws Exception {
    	try {
    		when(searchServiceMock.getOrdersByStatus(Mockito.anyString())).thenThrow(new Exception());
    	}catch(Exception ex) {
    		srchResourceMock.getOrdersByStatus("shipped");
    	}
    }
    
    @Test
   	public void testgetDiscountedOrders_Exception() throws Exception {
    	try {
    		when(searchServiceMock.getDiscountedOrders()).thenThrow(new Exception());
    	}catch(Exception ex) {
    		srchResourceMock.getDiscountedOrders();
    	}
    }
    
    @Test
   	public void testGetMinProductOrders_Exception() throws Exception {
    	try {
    		when(searchServiceMock.getMinProductOrders(Mockito.anyInt())).thenThrow(new Exception());
    	}catch(Exception ex) {
    		srchResourceMock.getMinProductOrders(2);
    	}
    }
    
    @Test
   	public void testGetMinPriceProducts_Exception() throws Exception {
    	try {
    		when(searchServiceMock.getMinPriceProducts(Mockito.any(BigDecimal.class))).thenThrow(new Exception());
    	}catch(Exception ex) {
    		srchResourceMock.getMinPriceProducts(new BigDecimal("20"));
    	}
    }
    
    @Test
   	public void testGetDiscountedOrders() throws Exception {    	
    	MvcResult result = mockMvc.perform(get("/search/orders/discounted")).andExpect(status().isOk()).andDo(print()).andReturn();
    	MockHttpServletResponse mockResponse=result.getResponse();
        assertThat(mockResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");

   	}
    
    @Test
	public void tesGetMinProductOrders() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.get("/search/orders?productCount=2")).andExpect(status().isOk()).andDo(print()).andReturn();
    }
    
    @Test
	public void testGetMinProdNull() throws Exception {
    	try {
    		mockMvc.perform(MockMvcRequestBuilders.get("/search/orders?productCount=-1").accept(MediaType.APPLICATION_JSON)).andDo(print()).andReturn();
    	}catch(Exception ex) {
    		assertEquals(ex.getMessage(), "Request processing failed; nested exception is java.lang.IllegalArgumentException: The productCount parameter cannot be less than zero");
    	}
    }
    
    @Test
	public void testGetMinPriceProducts() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.get("/search/products?minPrice=30")).andExpect(status().isOk()).andDo(print()).andReturn();
    }
    
    @Test
	public void testGetminPriceNull() throws Exception {
    	try {
    		mockMvc.perform(MockMvcRequestBuilders.get("/search/products?minPrice=-1").accept(MediaType.APPLICATION_JSON)).andDo(print()).andReturn();
    	}catch(Exception ex) {
    		assertEquals(ex.getMessage(), "Request processing failed; nested exception is java.lang.IllegalArgumentException: The minPrice parameter cannot be less than zero or empty");
    	}
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

}
