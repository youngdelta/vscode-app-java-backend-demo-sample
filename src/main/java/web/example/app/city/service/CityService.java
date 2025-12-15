package web.example.app.city.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import lombok.RequiredArgsConstructor;
import web.example.app.city.dao.CityDao;
import web.example.app.domain.model.City;
import web.example.app.domain.model.Country;

@Service
@RequiredArgsConstructor
public class CityService {
	
//	@Autowired
//	private CityDao cityDao;
	
	private final CityDao cityDao;
	
	
	/**
	 * @param id
	 * @return
	 */
//	public City getCity(String id) {
//		return cityDao.getCity();
//	}

	/**
	 * @param id
	 * @return
	 */
	public Page<List<City>> selectCityList(String id) {
		 return cityDao.selectCityList(id);
	}

	public City selectCity(String id) {
		return cityDao.selectCity(id);
	}
	

	public Page<City> search(String id) {
		 return cityDao.selectListCity(id);
	}

	/**
	 * @param string
	 * @return
	 */
	public Page<Country> doSearch(Country country) {
		return cityDao.doSearch(country);
	}

}
