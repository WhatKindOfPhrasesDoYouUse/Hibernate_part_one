package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int p_id;
    @Column(name = "product_name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private int price;
    @OneToMany(mappedBy = "product")
    public List<Customer_Product> customer_product;
    @Column(name = "value")
    public int value;

    public Product() {
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void buy() {
        value--;
    }

    public List<Customer_Product> getProducts() {
        List<Customer_Product> productList = new ArrayList<>();

        for (Customer_Product cp : customer_product) {
            if (cp.getValue() > 0) {
                productList.add(cp);
            }
        }

        return productList;
    }

    public void updatePrice(int newPrice) {
        this.price = newPrice;
        for (Customer_Product cp : customer_product) {
            cp.setValue(newPrice);
        }
    }
}
