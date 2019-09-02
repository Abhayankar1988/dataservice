package com.blackrock.dataservice.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "instruments")
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double price;
    private String product;
    @Temporal(TemporalType.TIME)
    private Date date;
    private Integer seconds;

    public Instrument() {
    }

    public Instrument(String product, double price, Integer seconds) {
        this.price = price;
        this.product = product;
        this.seconds = seconds;
        this.date = new Date();
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "id=" + id +
                ", price=" + price +
                ", product='" + product + '\'' +
                ", date=" + date +
                ", seconds=" + seconds +
                '}';
    }
}
