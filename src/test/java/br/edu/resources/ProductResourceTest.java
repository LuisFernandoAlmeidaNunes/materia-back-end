package br.edu.resources;

import java.util.List;

import br.edu.ifmg.produto.util.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.ifmg.produto.dtos.ProductDTO;
import br.edu.ifmg.produto.resources.ProductResource;
import br.edu.ifmg.produto.services.ProductService;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ProductResource.class)
public class ProductResourceTest {

    
        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private ProductService productService;

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
            when(productService.findAll(any())).thenReturn(page);
            
            ResultActions result = mockMvc.perform(get("/produto").accept("application/json"));
            
            result.andExpect(status().isOk());
        }

        @Test
        void findByIdShouldReturnProductWhenIdExists() throws Exception {
            when(productService.findById(existingId)).thenReturn(productDTO);
            
            ResultActions result = mockMvc.perform(get("/produto/{id}", existingId).accept("application/json"));
            
            result.andExpect(status().isOk());
            result.andExpect(jsonPath("$.id").value(productDTO.getId()));
            result.andExpect(jsonPath("$.name").value(productDTO.getName()));
            result.andExpect(jsonPath("$.description").value(productDTO.getDescription()));
        }
    }

