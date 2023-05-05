package net.nvsoftware.iProductService.model;

import lombok.Data;

@Data
public class ProductRequest {
    private long productId;
    private String productName;
    private long productPrice;
    private long productQuantity;
}
