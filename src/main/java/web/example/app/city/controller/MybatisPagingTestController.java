package web.example.app.city.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import web.example.app.city.service.MybatisPagingTestService;
import web.example.app.domain.dto.BoardListSearchDTO;
import web.example.app.domain.model.Country;


@RequiredArgsConstructor
@RequestMapping("/api/mybatis-paging")
// @RestController
@Log4j2
@Controller
public class MybatisPagingTestController {

    private final MybatisPagingTestService<List<Country>,Country, BoardListSearchDTO> mybatisPagingTestService;

    
    @GetMapping("/paging")
    @ResponseBody
    // public ResponseEntity<?> getCountryList( BoardListSearchDTO boardListSearchDTO) {
    public <T> PageInfo<T> getCountryList( BoardListSearchDTO boardListSearchDTO) {

        if (boardListSearchDTO.getPageNum() <= 0) {
            // boardListSearchDTO.setPageNum(1);
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else if (boardListSearchDTO.getPageSize() <= 0) {
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // PageInfo<T> pageInfo = PageHelper.startPage(boardListSearchDTO).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));

        // return ResponseEntity.ok(pageInfo);
        // return ResponseEntity.ok(PageHelper.startPage(boardListSearchDTO).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO)));
        return PageHelper.startPage(boardListSearchDTO).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));
    }


    /**
     * 
     * @param <T>
     * @param boardListSearchDTO
     * @return
     */
    @GetMapping("/country-list")
    public String  selectCountryList( BoardListSearchDTO boardListSearchDTO , Model model) {
    // public <T> PageInfo<T> selectCountryList( BoardListSearchDTO boardListSearchDTO) {

        // if (boardListSearchDTO.getPageNum() <= 0) {
        //     // boardListSearchDTO.setPageNum(1);
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        // } else if (boardListSearchDTO.getPageSize() <= 0) {
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        // }

        // PageInfo<T> pageInfo = PageHelper.startPage(boardListSearchDTO).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));

        // return ResponseEntity.ok(pageInfo);
        // return PageHelper.startPage(boardListSearchDTO).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));

        PageInfo<Country> pageInfo = PageHelper.startPage(boardListSearchDTO).doSelectPageInfo(()->mybatisPagingTestService.getCountryList(boardListSearchDTO));

        model.addAttribute("countries", pageInfo);


        return  "country";


    }
    
}
