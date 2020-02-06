package com.shangchao.repository;

import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.BrowseProduct;

import java.util.List;

public interface BrowseRepository {
    BrowseProduct save(BrowseProduct bp);

    List<BrowseProduct> getProducts(String userId);

    DeleteResult removeProduct(String userId, String productId);
}
