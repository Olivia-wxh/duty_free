package com.shangchao.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shangchao.entity.Topic;
import org.bson.types.ObjectId;

import java.util.List;

public interface TopicRepository {

  DeleteResult deleteById(String topicId);

  Topic save(Topic topic);

  public UpdateResult update(String topicId, ObjectId productId);

  List<Topic> findAll();

  Topic findById(String toString);

  List<Topic> getByPage(Integer currentPage, Integer pageSize);
}
