package com.avenuecode.orders.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order
  implements Serializable
{
  public static final int PRECISION = 2;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @JsonIgnore
  private String orderId;
  @Column(unique=true, nullable=false, length=8)
  private String orderNumber;
  @Column
  private BigDecimal discount;
  @Column(nullable=false)
  private BigDecimal taxPercent;
  private BigDecimal total;
  private BigDecimal totalTax;
  private BigDecimal grandTotal;
  @Column(length=10)
  private String status;
  
  public void setOrderId(String orderId)
  {
    this.orderId = orderId;
  }
  
  public void setOrderNumber(String orderNumber)
  {
    this.orderNumber = orderNumber;
  }
  
  public void setDiscount(BigDecimal discount)
  {
    this.discount = discount;
  }
  
  public void setTaxPercent(BigDecimal taxPercent)
  {
    this.taxPercent = taxPercent;
  }
  
  public void setTotal(BigDecimal total)
  {
    this.total = total;
  }
  
  public void setTotalTax(BigDecimal totalTax)
  {
    this.totalTax = totalTax;
  }
  
  public void setGrandTotal(BigDecimal grandTotal)
  {
    this.grandTotal = grandTotal;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public void setProducts(List<Product> products)
  {
    this.products = products;
  }
  
  public boolean equals(Object o)
  {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Order)) {
      return false;
    }
    Order other = (Order)o;
    if (!other.canEqual(this)) {
      return false;
    }
    Object this$orderId = getOrderId();Object other$orderId = other.getOrderId();
    if (this$orderId == null ? other$orderId != null : !this$orderId.equals(other$orderId)) {
      return false;
    }
    Object this$orderNumber = getOrderNumber();Object other$orderNumber = other.getOrderNumber();
    if (this$orderNumber == null ? other$orderNumber != null : !this$orderNumber.equals(other$orderNumber)) {
      return false;
    }
    Object this$discount = getDiscount();Object other$discount = other.getDiscount();
    if (this$discount == null ? other$discount != null : !this$discount.equals(other$discount)) {
      return false;
    }
    Object this$taxPercent = getTaxPercent();Object other$taxPercent = other.getTaxPercent();
    if (this$taxPercent == null ? other$taxPercent != null : !this$taxPercent.equals(other$taxPercent)) {
      return false;
    }
    Object this$total = getTotal();Object other$total = other.getTotal();
    if (this$total == null ? other$total != null : !this$total.equals(other$total)) {
      return false;
    }
    Object this$totalTax = getTotalTax();Object other$totalTax = other.getTotalTax();
    if (this$totalTax == null ? other$totalTax != null : !this$totalTax.equals(other$totalTax)) {
      return false;
    }
    Object this$grandTotal = getGrandTotal();Object other$grandTotal = other.getGrandTotal();
    if (this$grandTotal == null ? other$grandTotal != null : !this$grandTotal.equals(other$grandTotal)) {
      return false;
    }
    Object this$status = getStatus();Object other$status = other.getStatus();
    if (this$status == null ? other$status != null : !this$status.equals(other$status)) {
      return false;
    }
    Object this$products = getProducts();Object other$products = other.getProducts();return this$products == null ? other$products == null : this$products.equals(other$products);
  }
  
  public boolean canEqual(Object other)
  {
    return other instanceof Order;
  }
  
  public int hashCode()
  {
    int PRIME = 59;int result = 1;Object $orderId = getOrderId();result = result * 59 + ($orderId == null ? 43 : $orderId.hashCode());Object $orderNumber = getOrderNumber();result = result * 59 + ($orderNumber == null ? 43 : $orderNumber.hashCode());Object $discount = getDiscount();result = result * 59 + ($discount == null ? 43 : $discount.hashCode());Object $taxPercent = getTaxPercent();result = result * 59 + ($taxPercent == null ? 43 : $taxPercent.hashCode());Object $total = getTotal();result = result * 59 + ($total == null ? 43 : $total.hashCode());Object $totalTax = getTotalTax();result = result * 59 + ($totalTax == null ? 43 : $totalTax.hashCode());Object $grandTotal = getGrandTotal();result = result * 59 + ($grandTotal == null ? 43 : $grandTotal.hashCode());Object $status = getStatus();result = result * 59 + ($status == null ? 43 : $status.hashCode());Object $products = getProducts();result = result * 59 + ($products == null ? 43 : $products.hashCode());return result;
  }
  
  public String toString()
  {
    return "Order(orderId=" + getOrderId() + ", orderNumber=" + getOrderNumber() + ", discount=" + getDiscount() + ", taxPercent=" + getTaxPercent() + ", total=" + getTotal() + ", totalTax=" + getTotalTax() + ", grandTotal=" + getGrandTotal() + ", status=" + getStatus() + ", products=" + getProducts() + ")";
  }
  
  public String getOrderId()
  {
    return this.orderId;
  }
  
  public String getOrderNumber()
  {
    return this.orderNumber;
  }
  
  public BigDecimal getDiscount()
  {
    return this.discount;
  }
  
  public BigDecimal getTaxPercent()
  {
    return this.taxPercent;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  @ManyToMany(fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.ALL})
  @JoinTable(name="order_product", joinColumns={@javax.persistence.JoinColumn(name="order_id", updatable=false, nullable=false)}, inverseJoinColumns={@javax.persistence.JoinColumn(name="product_id", updatable=false, nullable=false)})
  private List<Product> products = new ArrayList();
  
  public List<Product> getProducts()
  {
    return this.products;
  }
  
  public BigDecimal getTotal()
  {
    BigDecimal total = new BigDecimal(BigInteger.ZERO);
    for (Product product : this.products) {
      total = total.add(product.getPrice());
    }
    return scaled(total);
  }
  
  public BigDecimal getTotalTax()
  {
    return scaled(getTotal().multiply(this.taxPercent.divide(new BigDecimal("100"))));
  }
  
  public BigDecimal getGrandTotal()
  {
    BigDecimal total = getTotal().add(getTotalTax());
    if (this.discount != null) {
      return scaled(total.subtract(this.discount));
    }
    return scaled(total);
  }
  
  private BigDecimal scaled(BigDecimal value)
  {
    return value.setScale(2, 3);
  }
}
