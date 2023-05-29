package com.vicente.microservicioproductos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "photos")
public class Photo {

    @Id
    private String id;
    private String url;
    private String publicId;
    private String productId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", publicId='" + publicId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(publicId, photo.publicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicId);
    }
}
