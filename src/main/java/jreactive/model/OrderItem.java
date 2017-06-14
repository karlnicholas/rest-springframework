package jreactive.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import jreactive.types.OrderItemType;

/**
 * OrderItem model object
 * @author Karl Nicholas
 * @version 2017.04.02
 *
 */
@Entity
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private int quantity;
    private BigDecimal itemPrice;
    private Long productId;
   
    /**
     * Copies properties from @{link OrderItemType}. Copies all dependencies.
     * @param orderItemType @{link OrderItemType}
     * @return {@link OrderItem}
     */
    public OrderItem fromOrderItemType(OrderItemType orderItemType ) {
        id = orderItemType.getId();
        quantity = orderItemType.getQuantity();
        itemPrice = orderItemType.getItemPrice();
        productId = orderItemType.getProductIdType();
        return this;
    }
    
    /**
     * Create and return OrderItemType representation. 
     * @return {@link OrderItemType}
     */
    public OrderItemType asOrderItemType() {
        OrderItemType orderItemType = new OrderItemType();
        orderItemType.setId(id);
        orderItemType.setQuantity(quantity);
        orderItemType.setItemPrice(itemPrice);
        orderItemType.setProductIdType( productId );
        return orderItemType;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(int value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the itemPrice property.
     * 
     * @return {@link BigDecimal }
     *     
     */
    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    /**
     * Sets the value of the itemPrice property.
     * 
     * @param value {@link BigDecimal }
     *     
     */
    public void setItemPrice(BigDecimal value) {
        this.itemPrice = value;
    }
    
    public Long getProductId() {
    	return productId;
    }
    
    public void setProductId( Long productId ) {
    	this.productId = productId;
    }

}
