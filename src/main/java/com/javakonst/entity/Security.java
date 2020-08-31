package com.javakonst.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@javax.persistence.Entity
@Table(name = "securities")
public class Security extends Entity {
    
    @Id
    @Getter
    @Setter
    private String secid;
    
    @Column
    @Getter
    @Setter
    private String name;
    
    @Column
    @Getter
    @Setter
    private String emitent_title;
    
    @Column
    @Getter
    @Setter
    private String regnumber;
    
    @Getter
    @Setter
    @OneToMany(mappedBy = "security", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<History> historyList;
    
    @Override
    public String toString() {
        String string = historyList == null ? "null" : String.valueOf(historyList.size());
        return "Security{" +
                "secid='" + secid + '\'' +
                ", name='" + name + '\'' +
                ", emitent_title='" + emitent_title + '\'' +
                ", regnumber='" + regnumber + '\'' +
                ", historyList=" + string +
                '}';
    }
}
