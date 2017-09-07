/**
 * 
 */
package com.avenuecode.orders;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author santhosh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersApplicationTest {
	
	@Test
	public void contextLoads() {
	}
	
	@Test
    public void test()
    {
		OrdersApplication.main(new String[]{
                "--spring.main.web-environment=false",
        });
    }
}
