package com.shangchao.dao;

import org.springframework.stereotype.Repository;

import java.util.Set;

/** */
@Repository
public interface IRoleDAO {

  public Set<String> listRole(String userName);
}
