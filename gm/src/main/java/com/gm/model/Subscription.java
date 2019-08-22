package com.gm.model;

import com.gm.util.SubscriptionType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.Objects;

@Entity
public class Subscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SubscriptionType type;

    @Column(name = "price", nullable = false)
    private Double Price;

    @Column(name = "yearMonth", nullable = false)
    private YearMonth subscriptionYearMonth;

    @Column(name = "box", nullable = false)
    private Long box;

    public Long getBox() {
        return box;
    }

    public void setBox(Long box) {
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

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
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
                ", box=" + box +
                '}';
    }