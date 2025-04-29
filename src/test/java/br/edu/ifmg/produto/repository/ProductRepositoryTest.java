package test.java.br.edu.ifmg.produto.repository;

import java.beans.Transient;
import java.util.Optional;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest

public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName(value = "Verificando se o objeto existe no bd depois do delete")
    public void deleteShouldDeleteObjectWhenIdExists(){

        productRepository.deleteById(1L);
        Optional<Product> obj = productRepository.findById(1L);

        AssertionError.assertFalse(obj.isPresent());    

    }

}
