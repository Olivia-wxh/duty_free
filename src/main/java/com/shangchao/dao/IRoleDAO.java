package com.shangchao.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by Administrator on 2017/10/11.
 */
@Repository
public interface IRoleDAO {

    public Set<String> listRole(String userName);
}
