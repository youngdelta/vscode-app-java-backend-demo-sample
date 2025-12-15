package web.example.app.city.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import lombok.RequiredArgsConstructor;
import web.example.app.city.mapper.CityMapper;
import web.example.app.city.mapper.MapperCity;
import web.example.app.domain.model.City;
import web.example.app.domain.model.Country;

@Repository
@RequiredArgsConstructor
public class CityDao {

//	private final CityMapper cityMapper;
	
	private final MapperCity mapperCity;

//	public CityDao(CityMapper cityMapper) {
//		this.cityMapper = cityMapper;
//	}
//	
	/**
	 * @return
	 */
//	public City getCity() {
//		return this.cityMapper.findByState("CA");
//	}
	
	/**
	 * @return
	 */
	public City getCityOne() {
		return mapperCity.getCity("CA");
	}

	/**
	 * @return
	 */
	public Page<List<City>> selectCityList(String id) {
		return mapperCity.selectCityList(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public City selectCity(String id) {
		return mapperCity.getCity(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public Page<City> selectListCity(String id) {
		return mapperCity.selectListCity(id);
	}

	/**
	 * @param string
	 * @return
	 */
	public Page<Country> doSearch(Country country) {
		return mapperCity.doSearch(country);
	}

	

}
