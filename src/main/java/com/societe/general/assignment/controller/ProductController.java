package com.societe.general.assignment.controller;

import com.societe.general.assignment.repo.ProductRepo;

import java.net.URI;
import java.util.Optional;

import com.societe.general.assignment.models.*;

@RestController
public class ProductController {
	@AutoWired
	private ProductRepo productRepo;

	@GetMapping("/products")
	public List<ProductMaster> retrieveAllProducts() {
		return productRepo.findAll();
	}

	@GetMapping("/products/{id}")
	public ProductMaster retrieveProduct(@PathVariable long id) {
		Optional<ProductMaster> product = productRepo.findById(id);

		if (!product.isPresent())
			throw new ProductNotFoundException("id-" + id);

		return product.get();
	}

	@DeleteMapping("/products/{id}")
	public void deleteProduct(@PathVariable long id) {
		productRepo.deleteById(id);
	}

	@PostMapping("/products")
	public ResponseEntity<Object> createProduct(@RequestBody ProductMaster product) {
		ProductMaster productMaster = productRepo.save(product);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productMaster.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProduct(@RequestBody ProductMaster product, @PathVariable long id) {

		Optional<ProductMaster> productOptional = productRepo.findById(id);

		if (!productOptional.isPresent())
			return ResponseEntity.notFound().build();

		product.setId(id);

		productRepo.save(product);

		return ResponseEntity.noContent().build();
	}
}
