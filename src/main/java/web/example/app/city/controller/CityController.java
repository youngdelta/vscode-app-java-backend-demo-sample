package web.example.app.city.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import web.example.app.city.service.CityService;
import web.example.app.domain.model.City;
import web.example.app.domain.model.Country;

@Log4j2
@RestController
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	/**
	 * @return
	 */
	@GetMapping("/getCity")
	public List<City> getCityList( @RequestParam(name="id", required = false) String id, @RequestParam(name="city", required = false) String city) {
		
		List<City> cityList = new ArrayList<>();
		
		City ct = cityService.selectCity(id);
		
		cityList.add(ct);
		
		return cityList;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getListCity")
	public Page<List<City>> getListCity(Model model, @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(name="id", required = false) String id, @RequestParam(name="city", required = false) String city) {
		
		PageInfo<City> pageInfo = PageHelper.startPage(page, 10)
                .doSelectPageInfo(() -> cityService.selectCityList(id));

		log.info("TotalCount : {}, CurrentPage : {}, PageSize : {}, TotalPage : {}", pageInfo.getTotal()
		                                                           , pageInfo.getPageNum()
		                                                           , pageInfo.getPageSize()
		                                                           , pageInfo.getPages());
		
//		List<City> cityList = cityService.selectCityList(id);
	    
	    PageHelper.startPage(page, 10);

		return cityService.selectCityList(id);
	}
	
	/**
	 * @param model
	 * @param page
	 * @param req
	 * @return
	 */
	// list로 요청이 오면.. 도서 목록을 request 에 담아 반환하자. 결과 페이지는 list
	@GetMapping("/list")
	public String list(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) String id, HttpServletRequest req) {
	    if(page==null) {
	        page = 1;
	    }
	    
	    PageInfo<City> pageInfo = PageHelper.startPage(page, 10)
                .doSelectPageInfo(() -> cityService.selectCityList(id));

		log.info("TotalCount : {}, CurrentPage : {}, PageSize : {}, TotalPage : {}", pageInfo.getTotal()
		                                                           , pageInfo.getPageNum()
		                                                           , pageInfo.getPageSize()
		                                                           , pageInfo.getPages());
		
//		List<City> cityList = cityService.selectCityList(id);
	    
	    // PageHelper.startPage(page, 10);
	    // business logic
	    Page<City> countries = cityService.search(id);
	    
	    String path = req.getContextPath()+"/countries/list?page";
	    // PageNavigationForPageHelper helper = new PageNavigationForPageHelper(countries, path);
	    // model.addAttribute("countries", helper);
	    return "country/list";
	}



	@GetMapping("/search")
	public PageInfo<Country> search(Country country, 
			@RequestParam(required = false, defaultValue = "1") int pageNum, 
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<>(cityService.doSearch(country));
	}

}
