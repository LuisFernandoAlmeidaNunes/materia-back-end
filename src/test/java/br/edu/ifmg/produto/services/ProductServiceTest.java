package br.edu.ifmg.produto.services;

import br.edu.ifmg.produto.dtos.ProductDTO;
import br.edu.ifmg.produto.entities.Product;
import br.edu.ifmg.produto.repositories.ProductRepository;
import br.edu.ifmg.produto.services.exceptions.ResourceNotFound;
import br.edu.ifmg.produto.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    public ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Long existingId;
    private Long nonExistingId;
    private PageImpl<Product> page;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        Product product = Factory.createProduct();
        product.setId(1L);
        page = new PageImpl<>(List.of(product));
    }

    @Test
    @DisplayName("Verificando se o objeto foi deletado no BD")
    public void deleteShouldDoNothingWhenIdExists() {
        when(productRepository.existsById(existingId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(existingId);

        Assertions.assertDoesNotThrow(
                () -> productService.delete(existingId)
        );

        verify(productRepository, Mockito.times(1)).deleteById(existingId);

    }

    @Test
    @DisplayName("Verificando se levanta uma exceção, se o objeto não existe no BD")
    public void deleteShouldThrowExceptionWhenIdNonExists() {

        when(productRepository.existsById(nonExistingId)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFound.class,
                () -> productService.delete(nonExistingId)
        );

        verify(productRepository, times(0)).deleteById(nonExistingId);

    }

    @Test
    @DisplayName("Verificando se o findAll retorna os dados paginados")
    public void findAllShouldReturnOnePage() {

        when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Pageable page = PageRequest.of(0, 10);
        Page<ProductDTO> result = productService.findAll(page);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getContent().getFirst().getId());
        verify(productRepository, times(1)).findAll(page);

    }

    @Test
    @DisplayName("Verificando se findAll retorna os dados paginados")
    void findByIdShoudReturnProductWhenIdExists() {
        Product product = Factory.createProduct();
        product.setId(1L);
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        ProductDTO dto = productService.findById(existingId);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(product.getId(), dto.getId());
        verify(productRepository, times(1)).findById(existingId);
    }
}