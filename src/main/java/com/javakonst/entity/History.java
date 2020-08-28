package com.javakonst.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "history")
public class History extends Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "secid")
    private Security security;

    @Column
    private Date tradedate;

    @Column
    private Double numtrades;

    @Column
    private Double open;

    @Column
    private Double close;

    public int getId() {
        return id;
    }

    public Date getTradedate() {
        return tradedate;
    }

    public void setTradedate(Date tradedate) {
        this.tradedate = tradedate;
    }

    public Double getNumtrades() {
        return numtrades;
    }

    public void setNumtrades(Double numtrades) {
        this.numtrades = numtrades;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "History{" +
                "id=" + id +
                ", security=" + security.toString() +
                ", tradedate=" + dateFormat.format(tradedate) +
                ", numtrades=" + numtrades +
                ", open=" + open +
                ", close=" + close +
                '}';
    }

}
