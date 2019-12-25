package com.shangchao.repository;

import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.CollectProduct;
import com.shangchao.entity.CollectTopic;

import java.util.List;

public interface CollectionRepository {

  CollectTopic saveCollectionTopic(CollectTopic ct);

  CollectProduct saveCollectionProduct(CollectProduct cp);

  DeleteResult delCollectionTopic(String topicId);

  DeleteResult delCollectionProduct(String productId);

  List<CollectTopic> getTopics(String userId);

  List<CollectProduct> getProducts(String userId);
}
