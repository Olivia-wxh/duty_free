package com.shangchao.repository;

import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.CollectProduct;
import com.shangchao.entity.CollectTopic;

import java.util.List;

public interface CollectionRepository {

    CollectTopic saveCollectionTopic(CollectTopic ct);

    CollectProduct saveCollectionProduct(CollectProduct cp);

    DeleteResult delCollectionTopic(String userId, String topicId);

    DeleteResult delCollectionProduct(String userId, List<String> productIds);

    List<CollectTopic> getTopics(String userId);

    List<CollectProduct> getProducts(String userId);

    CollectTopic getByUserIdTopicId(String userId, String topicId);

    CollectProduct getByUserIdProductId(String userId, String productId);

    long getCount(String userId);
}
