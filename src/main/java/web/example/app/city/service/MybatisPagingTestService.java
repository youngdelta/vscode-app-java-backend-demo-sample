package web.example.app.city.service;

import java.security.spec.ECField;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import lombok.RequiredArgsConstructor;
import web.example.app.city.mapper.MybatisPagingTestMapper;
import web.example.app.domain.dto.BoardListSearchDTO;
import web.example.app.domain.model.Country;

@RequiredArgsConstructor
@Service
public class MybatisPagingTestService<R,T, E> {

    private final MybatisPagingTestMapper<R, T, E> mybatisPagingTestMapper;
    
    /**
     *  
     * @param boardListSearchDTO
     * @return
     */
    // public PageInfo<T> getCountryList(E boardListSearchDTO) {
    public List<T> getCountryList(E boardListSearchDTO) {
        
        return mybatisPagingTestMapper.selectCountryList(boardListSearchDTO);
        // return PageHelper.startPage(boardListSearchDTO).doSelectPage(() -> mybatisPagingTestMapper.selectCountryList(boardListSearchDTO))
    }


}
