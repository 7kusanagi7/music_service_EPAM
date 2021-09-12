package kz.epam.store.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Order {

    int id;

    int userId;

    BigDecimal price;

    int diskId;

    private Date startDate;

    private Date endDate;

    public int getId() {
        return id;
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Order setUserId(int userId) {
        this.userId = userId;
        return this;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public Order setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getDiskId() {
        return diskId;
    }

    public Order setDiskId(int diskId) {
        this.diskId = diskId;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Order setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Order setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id == order.id && userId == order.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }
}
