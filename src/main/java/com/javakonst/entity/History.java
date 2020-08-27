package com.javakonst.entity;

import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@javax.persistence.Entity
@Table(name = "history")
public class History extends Entity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
    private Date tradedate;
    
    @Column
    private Double numtrades;
    
    @Column
    private Double open;
    
    @Column
    private Double close;
    
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "History{" +
                "tradedate=" + dateFormat.format(tradedate) +
                ", numtrades=" + numtrades +
                ", open=" + open +
                ", close=" + close +
                '}';
    }
}
