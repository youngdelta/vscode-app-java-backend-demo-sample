package web.example.app.city.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import web.example.app.city.mapper.CountryMapper;
import web.example.app.domain.model.Country;

@Service
public class CountryService<R, T, E> {

    @Autowired
    private CountryMapper<List<Country>, T, E> countryMapper;

    /**
     * 
     * @param pageNum
     * @param pageSize
     * @param country
     * @return
     */
    // public PageInfo<Country> getPagedCountrys(int pageNum, int pageSize, Country country) {
    public PageInfo<Country> getPagedCountrys(Country country) {
        
        // PageHelper.startPage(pageNum, pageSize);

        // List<Country> countrys = countryMapper.getCountrys(country);
        // return new PageInfo<>(countrys);

        return new PageInfo<>(countryMapper.getCountrys(country));

        // return PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> countryMapper.getCountrys(country));
    }
    
    /**
     * 
     * @param pageNum
     * @param pageSize
     * @param country
     * @return
     */
    public PageInfo<T> selectCountryList(E country) {
    // public PageInfo<Country> selectCountryList(int pageNum, int pageSize, Country country) {
        // PageHelper.startPage(pageNum, pageSize);
        // List<Country> countryList = countryMapper.selectCountryList(country);
        // return new PageInfo<>(countryList);

        return new PageInfo<>(countryMapper.selectCountryList(country));
    }

    /**
     * 
     * @param country
     * @return
     */
    public List<T> getCountryList(E country) {  //x
    // public PageInfo<Country> selectCountryList(int pageNum, int pageSize, Country country) {
        // PageHelper.startPage(pageNum, pageSize);
        // List<Country> countryList = countryMapper.selectCountryList(country);
        // return new PageInfo<>(countryList);

        return countryMapper.getCountryList(country);
    }
}



