package com.avenuecode.orders.repository;

import com.avenuecode.orders.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Serializable> {
	
	@Query(value="SELECT * FROM products p WHERE p.price > :minPrice", nativeQuery=true)
	List<Product> getMinPriceProducts(@Param("minPrice") BigDecimal minPrice);
}
