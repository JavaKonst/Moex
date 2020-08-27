package com.javakonst.entity;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "securities")
public class Security extends Entity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String secid;

    @Column
    private String name;

    @Column
    private String emitent_title;

    @Column
    private String regnumber;

    //TODO: добавить связанный объект History

    public int getId() {
        return id;
    }

    public String getSecid() {
        return secid;
    }

    public void setSecid(String secid) {
        this.secid = secid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmitent_title() {
        return emitent_title;
    }

    public void setEmitent_title(String emitent_title) {
        this.emitent_title = emitent_title;
    }

    public String getRegnumber() {
        return regnumber;
    }

    public void setRegnumber(String regnumber) {
        this.regnumber = regnumber;
    }

    @Override
    public String toString() {
        return "Security{" +
                "id=" + id +
                ", secid='" + secid + '\'' +
                ", name='" + name + '\'' +
                ", emitent_title='" + emitent_title + '\'' +
                ", regnumber='" + regnumber + '\'' +
                '}';
    }
}
