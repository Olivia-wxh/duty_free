package com.shangchao.repository;

import com.mongodb.client.result.DeleteResult;
import com.shangchao.entity.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository{

    DeleteResult deleteById(String topicId);

    Topic save(Topic topic);

    List<Topic> findAll();

    Topic findById(String toString);
}
