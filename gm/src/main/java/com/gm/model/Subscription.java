package com.gm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gm.util.SubscriptionType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Subscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "subscription_id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SubscriptionType type;

    @Column(name = "price", nullable = false)
    private Double Price;

    @Column(name = "yearMonth", nullable = false)
    private YearMonth subscriptionYearMonth;

    @JsonProperty("box")
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "box_id", referencedColumnName = "id")
    private Box box;

    @JsonProperty("orders")
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Order> orders;

    public Subscription() {}
    public Subscription(SubscriptionType type, Double price, YearMonth subscriptionYearMonth, Box box) {
        this.type = type;
        Price = price;
        this.subscriptionYearMonth = subscriptionYearMonth;
        this.box = box;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    public YearMonth getSubscriptionYearMonth() {
        return subscriptionYearMonth;
    }

    public void setSubscriptionYearMonth(YearMonth subscriptionYearMonth) {
        this.subscriptionYearMonth = subscriptionYearMonth;
    }

    public Subscription(Subscription sub) {
        this.id = sub.id;
        this.type = sub.type;
        Price = sub.Price;
        this.subscriptionYearMonth = sub.subscriptionYearMonth;
        this.box = sub.box;
        this.orders = sub.orders;
    }

    public Double getPrice() {
        return Price;
    }
    public void setPrice(Double price) {
        Price = price;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;
        Subscription that = (Subscription) o;
        return getId() == that.getId() &&
                getType() == that.getType() &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getSubscriptionYearMonth(), that.getSubscriptionYearMonth()) &&
                Objects.equals(getBox(), that.getBox());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getPrice(), getSubscriptionYearMonth(), getBox());
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", type=" + type +
                ", Price=" + Price +
                ", subscriptionYearMonth=" + subscriptionYearMonth +
                ", box=" + box.getId() +
                '}';
    }
}