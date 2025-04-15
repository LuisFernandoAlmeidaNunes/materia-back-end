package br.edu.ifmg.produto.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;

import br.edu.ifmg.produto.dtos.CategoryDTO;

@Entity
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    // Constructors

    public Category() {
    }
    
    public Category(long l, String string) {
        //TODO Auto-generated constructor stub
    }

    public Category(CategoryDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

    // get set

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
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    @PrePersist
    private void prePersist() {
        createdAt = Instant.now();
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    private void preUpdated() {
        updatedAt = Instant.now();
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
        Category other = (Category) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    // to string

    @Override
    public String toString() {
        return "category [id=" + id + ", name=" + name + ", getId()=" + getId() + ", getName()=" + getName()
                + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
                + "]";
    }

    

}
