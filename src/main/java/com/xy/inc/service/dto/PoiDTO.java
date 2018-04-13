package com.xy.inc.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class PoiDTO implements Serializable {

    @NotNull
    @Min(value = 0)
    private Integer x;

    @NotNull
    @Min(value = 0)
    private Integer y;

    @NotNull
    private String name;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PoiDTO)) return false;
        PoiDTO poiDTO = (PoiDTO) o;
        return Objects.equals(getX(), poiDTO.getX()) &&
                Objects.equals(getY(), poiDTO.getY()) &&
                Objects.equals(getName(), poiDTO.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getX(), getY(), getName());
    }

    @Override
    public String toString() {
        return "PoiDTO{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", name='" + getName() + '\'' +
                '}';
    }
}
