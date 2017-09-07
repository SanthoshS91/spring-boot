/**
 * 
 */
package com.avenuecode.orders.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.ProductRepository;

/**
 * @author santhosh
 *
 */
@RunWith(SpringRunner.class)
public class ProductServiceTest {
		
	@Mock
    private ProductRepository productRepository;
	
	@InjectMocks
	private ProductService productService;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testlistProducts(){
		List<Product> pd = new ArrayList<Product>();
		pd.add(setupProd1());
		pd.add(setupProd2());
		pd.add(setupProd3());
		when(productRepository.findAll()).thenReturn(pd);
		
		List<Product> result = productService.listProducts();
		assertEquals(3, result.size());
	}
	
	@Test
	public void testfindOneByProductId(){
		Product pd = setupProd1();
		Phone ph = new Phone();
		ph.setDescription("1h");
		ph.setProductId("12");
		when(productRepository.findOne("1")).thenReturn(pd);
		Product result = productService.getProduct("1");
		assertEquals(true, result.canEqual(pd));
		assertEquals(true, result.equals(pd));
		assertEquals(false, result.equals(ph));
		Object obj = pd.hashCode();
		assertTrue(obj instanceof Integer);
		assertTrue(result.toString().contains("productId"));
		assertEquals("1", result.getProductId());
		assertEquals(new BigDecimal("20"), result.getPrice());
		assertEquals("PD1", result.getDescription());
	}
	
	@Test
	public void testProductPojo(){
		Product pd = setupProd1();
		Phone ph = new Phone();
		ph.setDescription("1h");
		ph.setProductId("1");
		when(productRepository.findOne("1")).thenReturn(pd);
		Product result = productService.getProduct("1");
		assertEquals(false, result.equals(ph));
	}
	
	@Test
	public void testProductPojoUpcNull(){
		Product pd = setupProd1();
		pd.setUpc(null);
		Phone ph = new Phone();
		ph.setDescription("1h");
		ph.setProductId("1");
		ph.setUpc("245678");
		when(productRepository.findOne("1")).thenReturn(pd);
		Product result = productService.getProduct("1");
		assertEquals(false, result.equals(ph));
	}
	
	
	@Test
	public void testProductPojoSkuNull(){
		Product pd = setupProd1();
		pd.setSku(null);
		pd.setUpc("245678");
		Phone ph = new Phone();
		ph.setDescription("1h");
		ph.setProductId("1");
		ph.setUpc("245678");
		ph.setSku("245673t8");
		when(productRepository.findOne("1")).thenReturn(pd);
		Product result = productService.getProduct("1");
		assertEquals(false, result.equals(ph));
	}
	
	@Test
	public void testProductPojoDescNull(){
		Product pd = setupProd1();
		pd.setSku("245673t8");
		pd.setUpc("245678");
		pd.setDescription(null);
		Phone ph = new Phone();
		ph.setDescription("1h");
		ph.setProductId("1");
		ph.setUpc("245678");
		ph.setSku("245673t8");
		pd.setDescription("Apple");
		when(productRepository.findOne("1")).thenReturn(pd);
		Product result = productService.getProduct("1");
		assertEquals(false, result.equals(ph));
	}
	
	@Test
	public void testProductPojoDescNotNull(){
		Product pd = setupProd1();
		pd.setSku("245673t8");
		pd.setUpc("245678");
		pd.setDescription("Apple");
		pd.setPrice(null);
		Phone ph = new Phone();
		ph.setDescription("1h");
		ph.setProductId("1");
		ph.setUpc("245678");
		ph.setSku("245673t8");
		ph.setDescription("Apple");
		ph.setPrice(null);
		when(productRepository.findOne("1")).thenReturn(pd);
		Product result = productService.getProduct("1");
		assertEquals(true, result.equals(ph));
	}
	
	@Test
	public void testProductPojoSamePrice(){
		Product pd = setupProd1();
		pd.setSku("245673t8");
		pd.setUpc("245678");
		pd.setDescription("Apple");
		pd.setPrice(new BigDecimal("20"));
		Phone ph = new Phone();
		ph.setDescription("1h");
		ph.setProductId("1");
		ph.setUpc("245678");
		ph.setSku("245673t8");
		ph.setDescription("Apple");
		ph.setPrice(new BigDecimal("20"));
		when(productRepository.findOne("1")).thenReturn(pd);
		Product result = productService.getProduct("1");
		assertEquals(true, result.equals(ph));
	}
	
	/*@Test
	public void testProductOfDiffType(){
		Product pd = setupProd1();
		Phone ph = new Phone();
		when(productRepository.findOne("1")).thenReturn(pd);
		Product result = productService.getProduct("1");
		assertEquals(false, result.equals(ph));
	}*/
	
	
	
	@SuppressWarnings("serial")
	class Phone extends Product{
		
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

}
