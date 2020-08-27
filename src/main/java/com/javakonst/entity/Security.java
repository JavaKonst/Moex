package com.javakonst.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@javax.persistence.Entity
@Table(name = "securities")
public class Security extends Entity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
    private String name;
    
    @Column
    private String emitent_title;
    
    @Column
    private String regnumber;
    
    //TODO: добавить связанный объект History
}
