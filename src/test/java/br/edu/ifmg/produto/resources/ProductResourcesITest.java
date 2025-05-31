package br.edu.ifmg.produto.resources;

import br.edu.ifmg.produto.dtos.ProductDTO;
import br.edu.ifmg.produto.util.Factory;
import br.edu.ifmg.produto.util.TokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourcesITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;

    @Autowired
    private TokenUtil tokenUtil;
    private String username;
    private String password;
    private String token;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2000L;
    }

    @Test
    public void findAllShouldReturnSortByName() throws Exception{
        ResultActions resultActions =  mockMvc.perform(get("/product?page=0&size=10&sort=name,asc").accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
        resultActions.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));

    }

    @Test
    public void updateShouldReturnDtoWhenIdExixts() throws Exception{

        ProductDTO dto = Factory.createProductDTO();

        String dtoJson = objectMapper.writeValueAsString(dto);

        String nameExpected = dto.getName();

        String descriptionExpected = dto.getDescription();

        ResultActions result =  mockMvc.perform(put("/product/{id}",existingId).header("Authorization", "Bearer "+ token).content(dtoJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.name").value(nameExpected));
        result.andExpect(jsonPath("$.description").value(descriptionExpected));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
        ProductDTO dto = Factory.createProductDTO();

        String dtoJson = objectMapper.writeValueAsString(dto);

        ResultActions result =  mockMvc.perform(put("/product/{id}",nonExistingId).header("Authorization", "Bearer "+ token).content(dtoJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

    }

    @Test
    public void insertShouldReturnNewObjectWhenDataAreCorrect() throws Exception{

        ProductDTO dto = Factory.createProductDTO();

        String dtoJson = objectMapper.writeValueAsString(dto);

        String nameExpected = dto.getName();
        Long IdExpected = 26L;

        ResultActions result =  mockMvc.perform(post("/product",existingId).header("Authorization", "Bearer "+ token).content(dtoJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").value(IdExpected));
        result.andExpect(jsonPath("$.name").value(nameExpected));

    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception{

        ResultActions result =  mockMvc.perform(delete("/product/{id}",existingId).header("Authorization", "Bearer "+ token));
        result.andExpect(status().isNoContent());

    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{

        ResultActions result =  mockMvc.perform(delete("/product/{id}",nonExistingId).header("Authorization", "Bearer "+ token));

        result.andExpect(status().isNotFound());

    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception{
        ResultActions result =  mockMvc.perform(get("/product/{id}",existingId).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        String resultJson = result.andReturn().getResponse().getContentAsString();
        System.out.println(resultJson);
        ProductDTO dto = objectMapper.readValue(resultJson, ProductDTO.class);
        Assertions.assertEquals(existingId, dto.getId());
        Assertions.assertEquals("The Lord of the Rings", dto.getName());
    }
}
