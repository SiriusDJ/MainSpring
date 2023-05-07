package net.nvsoftware.iProductService.service;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import net.nvsoftware.iProductService.entity.ProductEntity;
import net.nvsoftware.iProductService.model.ProductRequest;
import net.nvsoftware.iProductService.model.ProductResponse;
import net.nvsoftware.iProductService.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long createProduct(ProductRequest productRequest) {
        log.info("ProductService createProduct start");
        ProductEntity productEntity = ProductEntity.builder()
                .productName(productRequest.getProductName())
                .productPrice(productRequest.getProductPrice())
                .productQuantity(productRequest.getProductQuantity())
                .build();
        productRepository.save(productEntity);
        log.info("ProductService createProduct done with productId: " + productEntity.getProductId());
        return productEntity.getProductId();
    }

    @Override
    public ProductResponse getProductId(long productId) {
        log.info("ProductService getProductById: " + productId);

        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("ProductService getProductById NOT FOUND with id: " + productId));
        ProductResponse productResponse = new ProductResponse();

        BeanUtils.copyProperties(productEntity, productResponse);

        log.info("ProductService getProductById done with productId " + productId);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long id, long quantity) {
        log.info("start reduceQuantity: " + quantity + " with id: " + id);

        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("reduceQuantity: Product NOT FOUND with id: " + id));

        if (productEntity.getProductQuantity() < quantity) {
            throw new RuntimeException("reduceQuantity: Product quantity not enough with id:" + id);
        }

        productEntity.setProductQuantity(productEntity.getProductQuantity() - quantity);
        productRepository.save(productEntity);
        log.info("did reduceQuantity: " + quantity + " with id: " + id);
    }

}
