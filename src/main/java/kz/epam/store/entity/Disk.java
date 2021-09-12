package kz.epam.store.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Disk {

    private int id;

    private String title;

    private BigDecimal price;

    private int authorId;

    private String coverImage;

    private String description;

    public int getId() {
        return id;
    }

    public Disk setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Disk setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Disk setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getAuthorId() {
        return authorId;
    }

    public Disk setAuthorId(int authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public Disk setCoverImage(String coverImage) {
        this.coverImage = coverImage;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Disk setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disk disk = (Disk) o;
        return id == disk.id &&
                Objects.equals(title, disk.title) &&
                Objects.equals(authorId, disk.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authorId);
    }
}
