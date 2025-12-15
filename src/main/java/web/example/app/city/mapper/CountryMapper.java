package web.example.app.city.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import web.example.app.domain.model.Country;

/**
 * 
 */
@Repository
@Mapper
public interface CountryMapper<R, T, E> {
    
    public List<T> selectCountryList(E country);
    
    public List<Country> doSearch(Country country);

    public List<Country> getCountrys(Country country);

    public List<T> getCountryList(E country);


}
