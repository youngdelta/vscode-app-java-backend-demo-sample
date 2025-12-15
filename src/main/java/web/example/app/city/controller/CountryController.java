package web.example.app.city.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import web.example.app.city.service.CountryService;
import web.example.app.city.service.MybatisPagingTestService;
import web.example.app.domain.dto.BoardListSearchDTO;
import web.example.app.domain.model.Country;

@Log4j2
@Controller
public class CountryController {

    @Autowired
    private CountryService<List<Country>,Country, Country> countryService;

    @Autowired
    private MybatisPagingTestService<List<Country>,Country, BoardListSearchDTO> mybatisPagingTestService;

    /**
     * Country 목록 데이터를 JSON으로 반환 (API)
     */
    @GetMapping("/countrys")
    @ResponseBody
    public PageInfo<Country> getEntities(//@RequestParam(defaultValue = "1") int pageNum,
                                         //@RequestParam(defaultValue = "10") int pageSize,
                                         Country country) {
        // return countryService.getPagedCountrys(pageNum, pageSize, country);

        // Page<Country> page = new Page<>(country.getPageNum(), country.getPageSize());
        
        
        // PageHelper.startPage(country.getPageNum(), 10);

        // return countryService.getPagedCountrys(country);
        // return PageHelper.startPage(country.getPageNum(), 10).doSelectPageInfo(() -> countryService.getPagedCountrys(country));
        return PageHelper.startPage(country).doSelectPageInfo(() -> countryService.getPagedCountrys(country));
    }

    /**
     * Country 목록 데이터를 JSON으로 반환 (API)
     */
    @GetMapping("/country/list")
    @ResponseBody
    public PageInfo<?> list(Country country) {
        return countryService.selectCountryList(country);
    }

    /**
     * Country 목록 조회 페이지를 렌더링
     */
    @GetMapping("/countrys-view")
    public String getCountrysPage(//@RequestParam(defaultValue = "1") int pageNum,
                                  //@RequestParam(defaultValue = "10") int pageSize,
                                  HttpServletRequest request,
                                  Country country,
                                  BoardListSearchDTO boardListSearchDTO,
                                  Page<Country> page,
                                //   PageInfo pageInfo,
                                  Model model) {

        request.getParameterNames().asIterator().forEachRemaining(param -> log.info("@@@ -------------- param: {} @value: {}", param, request.getParameter(param)));


        log.info("~~~~~   page  pageNum: {} , pageSize: {}",page.getPageNum(), page.getPageSize());
        log.info("~~~~~   Country  pageNum: {} , pageSize: {}",country.getPageNum(), country.getPageSize());
        log.info("~~~~~     boardListSearchDTO: {}", boardListSearchDTO);
        log.info("~~~~~     country: {}", country);

        // boardListSearchDTO.setPageSize(pageSize);

        // 무궁화 꽃이 피었습니다.

        // 안녕하세요.
        // 행복합니다.

        // 우리는 우리의 인생을 행복하게 서로 돕고 이어 주고 받아주고 살아야 한다.
        

        // int pageNum = country.getPageNum() == 0 ? GlobalVariables.DEFAULT_PAGE_NUM: country.getPageNum();
        // int pageSize = country.getPageSize() == 0 ? GlobalVariables.DEFAULT_PAGE_SIZE: country.getPageSize();

        log.info("### /countrys-view - getCountrysPage START");
        // log.info("### pageNum: {}", pageNum);
        // log.info("### pageSize: {}", pageSize);
        log.info("### country: {}", country);

        // PageInfo<Country> countries = countryService.getPagedCountrys(pageNum, pageSize, country);
        // PageInfo<?> countries = countryService.getPagedCountrys(country);

        // PageInfo<?> countries = countryService.selectCountryList(country);

        // PageInfo<Country> countries = PageHelper.startPage(country).doSelectPageInfo(()->countryService.selectCountryList(country));

        // PageInfo<?> countries = page.doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));//x
        // PageInfo<?> countries = PageHelper.startPage(boardListSearchDTO).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));

        // PageInfo<?> countries = PageHelper.startPage(page).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));//x
        PageInfo<?> countries = PageHelper.startPage(country).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));
        // PageInfo<?> countries = PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));

        // PageInfo<?> countries = new PageInfo<>(mybatisPagingTestService.getCountryList(boardListSearchDTO));    //pass-ok

        
        model.addAttribute("countries", countries);

        return "country"; // templates/country.html 렌더링
    }

    /**
     * 
     * @param <T>
     * @param country
     * @param boardListSearchDTO
     * @return
     */
    @GetMapping("/paging")
    public <T> ResponseEntity<?> getCountryList( Country country, BoardListSearchDTO boardListSearchDTO) {

        if (country.getPageNum() <= 0) {
            // boardListSearchDTO.setPageNum(1);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else if (country.getPageSize() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // PageInfo<T> pageInfo = PageHelper.startPage(boardListSearchDTO).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));

        // return ResponseEntity.ok(pageInfo);
        return ResponseEntity.ok(PageHelper.startPage(country).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO)));
    }


    /**
     * 
     * @param <T>
     * @param boardListSearchDTO
     * @return
     */
    @GetMapping("/country-list")
    @ResponseBody
    public <T> PageInfo<T> selectCountryList( Country country) {

        // if (boardListSearchDTO.getPageNum() <= 0) {
        //     // boardListSearchDTO.setPageNum(1);
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        // } else if (boardListSearchDTO.getPageSize() <= 0) {
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        // }

        // PageInfo<T> pageInfo = PageHelper.startPage(boardListSearchDTO).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));

        // return ResponseEntity.ok(pageInfo);
        return PageHelper.startPage(country).doSelectPageInfo(()->countryService.selectCountryList(country));
    }


    /**
     * 
     * @param <T>
     * @param page
     * @param country
     * @return
     */
    @GetMapping("/country")
    @ResponseBody
    public <T> PageInfo<T> countryList(Page<Country> page, Country country) {

        // if (boardListSearchDTO.getPageNum() <= 0) {
        //     // boardListSearchDTO.setPageNum(1);
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        // } else if (boardListSearchDTO.getPageSize() <= 0) {
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        // }

        // PageInfo<T> pageInfo = PageHelper.startPage(boardListSearchDTO).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));

        // return ResponseEntity.ok(pageInfo);

        return PageHelper.startPage(country).doSelectPageInfo(()->countryService.getCountryList(country));


    }
}

