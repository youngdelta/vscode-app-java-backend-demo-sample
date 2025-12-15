package web.example.app.paging;

import org.apache.ibatis.annotations.Mapper;


import com.github.pagehelper.Page;

import web.example.app.domain.dto.SearchDto;
import web.example.app.domain.entity.PagingEntity;


@Mapper
public interface PagingRepository {
    
    Page<PagingEntity> findUser(SearchDto search);
}