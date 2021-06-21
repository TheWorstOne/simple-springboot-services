package spring.microservice.serviceproduct.service;

import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.microservice.serviceproduct.entity.Category;
import spring.microservice.serviceproduct.entity.Product;
import spring.microservice.serviceproduct.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

   private final ProductRepository productRepository;

   @Override
   public List<Product> listAllProduct() {
      return productRepository.findAll();
   }

   @Override
   public Product getProduct(Long id) {
      return productRepository.findById(id).orElse(null);
   }

   @Override
   public Product createProduct(Product product) {
      product.setStatus("CREATED");
      product.setCreateAt(new Date());
      return productRepository.save(product);
   }

   @Override
   public Product updateProduct(Product product) {
      Product productDB = getProduct(product.getId());

      if (null==productDB)
         return null;

      productDB.setName(product.getName());
      productDB.setDescription(product.getDescription());
      productDB.setCategory(product.getCategory());
      productDB.setPrice(product.getPrice());

      return productRepository.save(productDB);
   }

   @Override
   public Product deleteProduct(Long id) {
      Product productDB = getProduct(id);

      if (null==productDB)
         return null;

      productDB.setStatus("DELETED");

      return productRepository.save(productDB);
   }

   @Override
   public List<Product> findByCategory(Category category) {
      return productRepository.findByCategory(category);
   }

   @Override
   public Product updateStock(Long id, Double quantity) {
      Product productDB = getProduct(id);

      if (null == productDB)
         return null;

      Double stock = productDB.getStock() + quantity;
      productDB.setStock(stock);

      return productRepository.save(productDB);
   }
   
}
