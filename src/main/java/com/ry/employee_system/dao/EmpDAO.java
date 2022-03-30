package com.ry.employee_system.dao;

import com.ry.employee_system.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpDAO {
    //查询所有员工信息
    List<Emp> findAll();
    //保存员工信息
    void save(Emp emp);
    //删除员工信息
    void delete(String id);
    Emp findOne(String id);

    //修改员工信息
    void update(Emp emp);
}
