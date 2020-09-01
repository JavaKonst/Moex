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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        History history = (History) o;

        if (!security.equals(history.security)) return false;
        if (!tradedate.equals(history.tradedate)) return false;
        if (!numtrades.equals(history.numtrades)) return false;
        if (!open.equals(history.open)) return false;
        return close.equals(history.close);
    }

    @Override
    public int hashCode() {
        int result = security.hashCode();
        result = 31 * result + tradedate.hashCode();
        result = 31 * result + numtrades.hashCode();
        result = 31 * result + open.hashCode();
        result = 31 * result + close.hashCode();
        return result;
    }
}
