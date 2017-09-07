package com.avenuecode.orders.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="products")
public class Product
  implements Serializable
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @JsonIgnore
  private String productId;
  @Column(unique=true, nullable=false, length=10)
  private String upc;
  @Column(unique=true, nullable=false, length=13)
  private String sku;
  @Column(nullable=false)
  private String description;
  @Column(nullable=false)
  private BigDecimal price;
  
  public void setProductId(String productId)
  {
    this.productId = productId;
  }
  
  public void setUpc(String upc)
  {
    this.upc = upc;
  }
  
  public void setSku(String sku)
  {
    this.sku = sku;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public void setPrice(BigDecimal price)
  {
    this.price = price;
  }
  
  public boolean equals(Object o)
  {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Product)) {
      return false;
    }
    Product other = (Product)o;
    if (!other.canEqual(this)) {
      return false;
    }
    Object this$productId = getProductId();Object other$productId = other.getProductId();
    if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) {
      return false;
    }
    Object this$upc = getUpc();Object other$upc = other.getUpc();
    if (this$upc == null ? other$upc != null : !this$upc.equals(other$upc)) {
      return false;
    }
    Object this$sku = getSku();Object other$sku = other.getSku();
    if (this$sku == null ? other$sku != null : !this$sku.equals(other$sku)) {
      return false;
    }
    Object this$description = getDescription();Object other$description = other.getDescription();
    if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
      return false;
    }
    Object this$price = getPrice();
    Object other$price = other.getPrice();
    return this$price == null ? other$price == null : this$price.equals(other$price);
  }
  
  public boolean canEqual(Object other)
  {
    return other instanceof Product;
  }
  
  public int hashCode()
  {
    int PRIME = 59;int result = 1;Object $productId = getProductId();result = result * 59 + ($productId == null ? 43 : $productId.hashCode());Object $upc = getUpc();result = result * 59 + ($upc == null ? 43 : $upc.hashCode());Object $sku = getSku();result = result * 59 + ($sku == null ? 43 : $sku.hashCode());Object $description = getDescription();result = result * 59 + ($description == null ? 43 : $description.hashCode());Object $price = getPrice();result = result * 59 + ($price == null ? 43 : $price.hashCode());return result;
  }
  
  public String toString()
  {
    return "Product(productId=" + getProductId() + ", upc=" + getUpc() + ", sku=" + getSku() + ", description=" + getDescription() + ", price=" + getPrice() + ")";
  }
  
  public String getProductId()
  {
    return this.productId;
  }
  
  public String getUpc()
  {
    return this.upc;
  }
  
  public String getSku()
  {
    return this.sku;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public BigDecimal getPrice()
  {
    return this.price;
  }
}
