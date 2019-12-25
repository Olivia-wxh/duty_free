package com.shangchao.repository;

import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.Topic;

import java.util.List;

public interface TopicRepository {

  DeleteResult deleteById(String topicId);

  Topic save(Topic topic);

  List<Topic> findAll();

  Topic findById(String toString);
}
