package br.edu.ifmg.produto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifmg.produto.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    
    
}
