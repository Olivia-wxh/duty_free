package com.shangchao.dao;

import org.springframework.stereotype.Repository;

import java.util.Set;

/** */
@Repository
public interface IPermissionDAO {

  public Set<String> listPermission(String userName);
}
