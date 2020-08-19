package com.javakonst.entity;

import lombok.Data;
import java.util.Date;

@Data
public class History extends Entity{
    private Date tradedate;
    private Double numtrades;
    private Double open;
    private Double close;
}
