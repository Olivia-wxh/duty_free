package com.shangchao.dao;

import com.shangchao.entity.Statistics;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStatisticsDao {

    Statistics findByTime(Integer year, Integer month);

    Integer save(Statistics statistics);

    Integer delete(Integer id);

    Integer update(Statistics statistics);

    List<Statistics> findStatistics(Integer year);
}
