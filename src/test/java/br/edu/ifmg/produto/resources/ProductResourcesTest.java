package br.edu.ifmg.produto.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import br.edu.ifmg.produto.resources.ProductResource;
import br.edu.ifmg.produto.services.ProductService;
import br.edu.ifmg.produto.dtos.ProductDTO;

import br.edu.ifmg.produto.util.Factory;


@WebMvcTest(ProductResource.class)
public class ProductResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService produtoService;

    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach void setUp(){
        existingId = 1L;
        nonExistingId = 200L;
        productDTO = Factory.createProductDTO();
        productDTO.setId(1L);
        page = new PageImpl<>(List.of(productDTO));
    }


    @Test
    void findAllShouldReturnAllPage() throws Exception {  
        when(produtoService.findAll(any())).thenReturn(page);
    
        ResultActions resultActions = 
            mockMvc.perform(get("/product").accept("application/json"));
    
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findByIdShouldReturnProductWhenIdExists() throws Exception {
        when(produtoService.findById(existingId)).thenReturn(productDTO);
    
        ResultActions resultActions = 
            mockMvc.perform(get("/product/{id}", existingId).accept("application/json"));
    
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(jsonPath("$.id").value(productDTO.getId()));          // Corrigido para '$.id'
        resultActions.andExpect(jsonPath("$.name").value(productDTO.getName()));      // Corrigido para '$.nome'
        resultActions.andExpect(jsonPath("$.description").value(productDTO.getDescription()));  // Corrigido para '$.descricao'
    }
    

    
}
