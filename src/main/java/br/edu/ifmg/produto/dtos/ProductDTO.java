package br.edu.ifmg.produto.dtos;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;
import org.springframework.boot.context.properties.bind.Name;

import br.edu.ifmg.produto.entities.Category;
import br.edu.ifmg.produto.entities.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name ="tb_product")
public class ProductDTO extends RepresentationModel<ProductDTO> {

    @Schema(description = "Database generated Id product")

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Product name")
    private String name;
    @Schema(description = "Detailed description of the product")
    private String description;
    @Schema(description = "Product price")
    private double price;
    @Schema(description = "Product URL of image")
    private String imageUrl;

    private Instant createdAt;
    private Instant updatedAt;

    @Schema(description = "Product categories (one or more)")
    private Set<CategoryDTO> categories = new HashSet<>();

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imageUrl = entity.getImageUrl();

        entity.getCategories().stream().forEach(c -> this.categories.add(new CategoryDTO(c)));
    }

    public ProductDTO(Product product, Set<Category> categories) {
        this(product);
        categories.forEach(c -> this.categories.add(new CategoryDTO(c)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductDTO other = (ProductDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductDTO [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
                + ", imageUrl=" + imageUrl + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", categories="
                + categories + "]";
    }



    
}
