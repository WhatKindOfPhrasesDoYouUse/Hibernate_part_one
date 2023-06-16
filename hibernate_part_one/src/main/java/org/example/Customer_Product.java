package org.example;

import javax.persistence.*;

@Entity
@Table(name = "customer_product")
public class Customer_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buy_id", nullable = false)
    private int buy_id;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "p_id")
    private Product product;

    @Column(name = "value")
    private int value;

    @Column(name = "quantity")
    private int quantity;

    public Customer_Product() {}

    public int getBuy_id() {
        return buy_id;
    }

    public void setBuy_id(int buy_id) {
        this.buy_id = buy_id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        updateTotalValue(); // Вызов метода для пересчета итоговой цены
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private void updateTotalValue() {
        int totalValue = 0;
        for (Customer_Product cp : customer.getCustomer_product()) {
            totalValue += cp.getValue() * cp.getQuantity();
        }
        customer.setTotalValue(totalValue);
        System.out.println("Итоговая цена для клиента " + customer.getName() + ": " + totalValue);
    }
}
