/**
 * 
 */
package com.avenuecode.orders.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.avenuecode.orders.service.OrderService;

/**
 * @author santhosh
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = OrderResource.class)
@AutoConfigureMockMvc(secure=false)
public class OrderResourceTest {

	private MockMvc mockMvc;
	
	Order order;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
    private OrderResource controller;

	@MockBean
    private OrderService orderServiceMock;
	
	@Before
    public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Before
	public void setupOrders(){
		order = setUpOrder1();
	}
	
    @Test
    public void controllerInitialization() throws Exception {
        assertThat(controller).isNotNull();
    }
    
    @Test
    public void testListOrders() throws Exception {
    	assertThat(this.orderServiceMock).isNotNull();
    }
    
    @Test
	public void verifyAllOrders() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/orders").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}
    
    @Test
	public void verifyOrderById() throws Exception {
    	when(orderServiceMock.getOrder("1")).thenReturn(order);
    	MvcResult result= mockMvc.perform(get("/orders/{orderId}/", 1)).andExpect(status().isOk()).andDo(print()).andReturn();
    	
    	MockHttpServletResponse mockResponse=result.getResponse();
        assertThat(mockResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        assertEquals(1, responseHeaders.size());
        String responseAsString=mockResponse.getContentAsString();
        assertTrue(responseAsString.contains("orderNumber"));
        assertTrue(responseAsString.contains("status"));
	}
    
    @Test
	public void verifyNullOrder() throws Exception {
    	when(orderServiceMock.getOrder("6")).thenReturn(null);
    	mockMvc.perform(get("/orders/{orderId}/", 6)).andExpect(status().is4xxClientError()).andDo(print()).andReturn();
		
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
