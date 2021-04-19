package com.shangchao.dao;

import com.shangchao.entity.History;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHistoryDAO {

    List<History> getSearchInfo(Integer userId);

    Integer removeSearchByUserId(Integer userId);

    Integer saveHistory(History history);
}
