package com.shangchao.dao;



import com.shangchao.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 */
@Repository
public interface IUserDAO {
    public User findByName(String userName);

    public List<User> getAllUser(Map<String, Object> param);

    public Integer getUserTotal(Map<String, Object> param);

    public User listRolesAndPermissions(String userName);

    public boolean doCreate(User vo);

    public boolean doUpdate(User vo);

    public boolean doRemove(Object[] ids);

    public List<User> listUserByNams(Object[] names);


}
