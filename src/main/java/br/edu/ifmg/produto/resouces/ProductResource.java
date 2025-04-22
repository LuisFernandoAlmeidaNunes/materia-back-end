package br.edu.ifmg.produto.resouces;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifmg.produto.dtos.ProductDTO;
import br.edu.ifmg.produto.services.ProductService;

@RestController
@RequestMapping(value = "/product")
@Tag(name = "Product", description = "Controller/Resource for products")
public class ProductResource {

    @Autowired
    private ProductService productService;

    @GetMapping(produces = "aplication/json")
    @Operation(
        description = "Get all products",
        summary = "Get all products",
        response = {
            @ApiResponse(description = "ok", responseCode = "200")
        }
    )
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
        Page<ProductDTO> products = productService.findAll(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    @Operation(
        description = "Get a product",
        summary = "Get a product",
        response = {
            @ApiResponse(description = "ok", responseCode = "200"),
            @ApiResponse(description = "not found", responseCode = "404")
        }
    )
    public ResponseEntity<ProductDTO> findById(Long id){
        ProductDTO product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @Operation(
        description = "Create a product",
        summary = "Create  a product",
        responses = {
            @ApiResponse(description = "ok", responseCode = "200"),
            @ApiResponse(description = "bad resquest", responseCode = "400"),
            @ApiResponse(description = "unautorized", responseCode = "401"),
            @ApiResponse(description = "forbiden", responseCode = "403"),
            @ApiResponse(description = "not found", responseCode = "404"),
        }
    )
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto){
        
        dto = productService.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
    
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    @Operation(
        description = "update a product",
        summary = "update a product",
        responses = {
            @ApiResponse(description = "ok", responseCode = "200"),
            @ApiResponse(description = "bad resquest", responseCode = "400"),
            @ApiResponse(description = "unautorized", responseCode = "401"),
            @ApiResponse(description = "forbiden", responseCode = "403"),
            @ApiResponse(description = "not found", responseCode = "404"),
        }
    )
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto){
        dto = productService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
        description = "delete a product",
        summary = "delete a product",
        responses = {
            @ApiResponse(description = "ok", responseCode = "200"),
            @ApiResponse(description = "bad resquest", responseCode = "400"),
            @ApiResponse(description = "unautorized", responseCode = "401"),
            @ApiResponse(description = "forbiden", responseCode = "403"),
            @ApiResponse(description = "not found", responseCode = "404"),
        }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
