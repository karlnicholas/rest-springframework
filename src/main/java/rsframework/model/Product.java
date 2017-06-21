package rsframework.model;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import rsframework.types.ProductType;

/**
 * Entity implementation class for Entity: Product
 * @author Karl Nicholas
 * @version 2017.04.02
 *
 */
@Entity
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String name;
    private String description;
    private String category;
    
    /**
     * Fill out properties from PurchaseOrderType. Copies all dependencies.   
     * @param productType {@link ProductType}
     * @return Product
     */
    public Product fromProductType(ProductType productType) {
        this.id = productType.getId();
        this.sku = productType.getSku();
        this.name = productType.getName();
        this.description = productType.getDescription();
        this.category = productType.getCategory();
        return this;
    }

    /**
     * Create and return ProductType representation. 
     * @return {@link ProductType}
     */
    public ProductType asProductType() {
        ProductType productType = new ProductType(); 
        productType.setId(id);
        productType.setSku(sku);
        productType.setName(name);
        productType.setDescription(description);
        productType.setCategory(category);
        return productType;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the sku property.
     * 
     * @return {@link String }
     *     
     */
    public String getSku() {
        return sku;
    }

    /**
     * Sets the value of the sku property.
     * 
     * @param value {@link String }
     *     
     */
    public void setSku(String value) {
        this.sku = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }
}
