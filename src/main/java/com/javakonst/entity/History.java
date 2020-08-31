package com.javakonst.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "history")
public class History extends Entity {
    
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Getter
    @Setter
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "secid")
    private Security security;
    
    @Getter
    @Setter
    @Column
    private Date tradedate;
    
    @Getter
    @Setter
    @Column
    private Double numtrades;
    
    @Getter
    @Setter
    @Column
    private Double open;
    
    @Getter
    @Setter
    @Column
    private Double close;

//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    ", tradedate=" + dateFormat.format(tradedate) +
    
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "History{" +
                "id=" + id +
                ", security=" + security.getSecid() +
                ", tradedate=" + dateFormat.format(tradedate) +
                ", numtrades=" + numtrades +
                ", open=" + open +
                ", close=" + close +
                '}';
    }
}
