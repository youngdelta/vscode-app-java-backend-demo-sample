package web.example.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import web.example.app.domain.entity.Emp;
 
@Mapper
public interface CRUDMapper {
    List<Emp> search(Map<String,Object> map);
    void addBoardAndAttach(Map<String,Object> map);
}
 
