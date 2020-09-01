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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Security security = (Security) o;

        if (!secid.equals(security.secid)) return false;
        if (name != null ? !name.equals(security.name) : security.name != null) return false;
        if (emitent_title != null ? !emitent_title.equals(security.emitent_title) : security.emitent_title != null)
            return false;
        if (regnumber != null ? !regnumber.equals(security.regnumber) : security.regnumber != null) return false;
        return historyList != null ? historyList.equals(security.historyList) : security.historyList == null;
    }

    @Override
    public int hashCode() {
        int result = secid.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (emitent_title != null ? emitent_title.hashCode() : 0);
        result = 31 * result + (regnumber != null ? regnumber.hashCode() : 0);
        result = 31 * result + (historyList != null ? historyList.hashCode() : 0);
        return result;
    }
}
