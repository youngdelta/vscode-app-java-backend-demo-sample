package web.example.app.city.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import web.example.app.domain.model.City;
import web.example.app.domain.model.Country;

@Repository
@Mapper
public interface MapperCity {
	
	/**
	 * @param id
	 * @return
	 */
	public City getCity(String id);

	/**
	 * @param string
	 * @return
	 */
	public Page<List<City>> selectCityList(String string);

	/**
	 * @param string
	 * @return
	 */
	public Page<Country> doSearch(Country country);

	public Page<City> selectListCity(String id);

}
