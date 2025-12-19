package web.example.app.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import web.example.app.domain.entity.Emp;

@Mapper
public interface EmpMapper {

    Object getlist();

    Object listByDeptno(int deptno);

    int deleteByEmpno(Emp emp);

    int updateByDeptno(Map<String,Integer> map);

    Object getListWithDeptno();
    
}
