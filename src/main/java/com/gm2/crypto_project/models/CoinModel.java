package com.gm2.crypto_project.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "tb_coin")
public class CoinModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id_item;
    @Column(unique = true)
    private String name;
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());
    private BigDecimal price, quantity;
    private boolean active_tb = true;

    public UUID getId_item() {
        return id_item;
    }

    public void setId_item(UUID id_item) {
        this.id_item = id_item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public boolean isActive_tb() {
        return active_tb;
    }

    public void setActive_tb(boolean active_tb) {
        this.active_tb = active_tb;
    }
}
