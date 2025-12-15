package web.example.app.city.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import web.example.app.domain.dto.BoardListSearchDTO;
import web.example.app.domain.model.Country;

// @Repository
@Mapper
public interface MybatisPagingTestMapper<R,T, E> {

    public List<T> selectCountryList(E boardListSearchDTO);
    

}
