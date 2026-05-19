package com.example.eVanigam.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eVanigam.backend.dto.DtoMapper;
import com.example.eVanigam.backend.dto.ProductDTO;
import com.example.eVanigam.backend.model.Product;
import com.example.eVanigam.backend.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService service;
    
    public ProductController (ProductService service){
        this.service=service;
    }
    @GetMapping
    public List<ProductDTO> getProducts() {
        return service.getProducts()
            .stream()
            .map(DtoMapper::toProductDTO)
            .toList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable("id") Long productId) {
        return DtoMapper.toProductDTO(service.getProductById(productId));
    }

    @PostMapping
    public ProductDTO addProduct(@RequestBody Product product) {
        return DtoMapper.toProductDTO(service.addProduct(product));
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable("id") Long productId, @RequestBody Product product) {
        return DtoMapper.toProductDTO(service.updateProduct(productId, product));
    }
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}
