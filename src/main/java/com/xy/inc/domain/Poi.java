package com.xy.inc.domain;

import org.hibernate.annotations.Check;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "poi")
@EntityListeners(AuditingEntityListener.class)
@Check(constraints = "x > 0")
public class Poi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @Min(0)
    private Integer x;

    @Column
    @NotNull
    @Min(0)
    private Integer y;

    @Column
    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Poi{" +
                "id=" + getId() +
                ", x=" + getX() +
                ", y=" + getY() +
                ", name='" + getName() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Poi)) return false;
        Poi poi = (Poi) o;
        return Objects.equals(getId(), poi.getId()) &&
                Objects.equals(getX(), poi.getX()) &&
                Objects.equals(getY(), poi.getY()) &&
                Objects.equals(getName(), poi.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

}
