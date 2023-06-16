package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private int c_id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "customer")
    private List<Customer_Product> customer_product;

    @Transient
    private int totalValue; // Итоговая цена

    public Customer() {
        customer_product = new ArrayList<>();
    }

    public Customer(String name) {
        this.name = name;
        customer_product = new ArrayList<>();
    }
    public int getC_id() {
        return c_id;
    }
    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Customer_Product> getCustomer_product() {
        return customer_product;
    }
    public void setCustomer_product(List<Customer_Product> customer_product) {
        this.customer_product = customer_product;
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

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
