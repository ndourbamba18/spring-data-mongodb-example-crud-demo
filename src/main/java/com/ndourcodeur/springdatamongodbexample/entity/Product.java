package com.ndourcodeur.springdatamongodbexample.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Document(collection = "Products")
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public class Product implements Serializable {

    @Id
    private String _id;

    @Indexed(unique = true)
    @NotBlank(message = "This field is required.")
    @Field(name = "Name")
    private String name;

    @Field(name = "Price")
    private double price;

    @Field(name = "In_Stock")
    private boolean isInStock;

    @NotBlank(message = "This field is required.")
    @Field(name = "Description")
    private  String description;

    @CreatedDate
    @Field(name = "Created_At")
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "Updated_At")
    private Date updatedAt;

    public Product() {}

    public Product(String _id, String name, double price, boolean isInStock, String description,
                   Date createdAt, Date updatedAt) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.isInStock = isInStock;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && isInStock == product.isInStock && _id.equals(product._id)
                && Objects.equals(name, product.name) && Objects.equals(description, product.description)
                && Objects.equals(createdAt, product.createdAt) && Objects.equals(updatedAt, product.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name, price, isInStock, description, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Product{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isInStock=" + isInStock +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
