/**
 * 
 */
package com.avenuecode.orders.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Collection;

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

import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.service.ProductService;

/**
 * @author santhosh
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductResource.class)
@AutoConfigureMockMvc(secure=false)
public class ProductResourceTest {
	
	private MockMvc mockMvc;
	
	Product pd;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
    private ProductResource controller;

	@MockBean
    private ProductService prdServiceMock;
	
	@MockBean
    private Product prd;
	
	
	@Before
    public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
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
    	assertThat(this.prdServiceMock).isNotNull();
    }
    
    @Test
	public void verifyAllProucts() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andReturn();
	}
    
    @Test
   	public void verifyProductById() throws Exception {
       when(prdServiceMock.getProduct("1")).thenReturn(pd);
       MvcResult result= mockMvc.perform(get("/products/{productId}/", 1)).andExpect(status().isOk()).andDo(print()).andReturn();
       	
       MockHttpServletResponse mockResponse=result.getResponse();
       assertThat(mockResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
       Collection<String> responseHeaders = mockResponse.getHeaderNames();
       assertNotNull(responseHeaders);
       assertEquals(1, responseHeaders.size());
       String responseAsString=mockResponse.getContentAsString();
       assertTrue(responseAsString.contains("132424"));
   	}
    
    @Test
	public void verifyNullOrder() throws Exception {
    	when(prdServiceMock.getProduct("6")).thenReturn(null);
    	mockMvc.perform(get("/products/{productId}/", 6)).andExpect(status().is4xxClientError()).andDo(print()).andReturn();
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
    
    

}
