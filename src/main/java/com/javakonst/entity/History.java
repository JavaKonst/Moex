package com.javakonst.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class History extends Entity {
    private Date tradedate;
    private Double numtrades;
    private Double open;
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
