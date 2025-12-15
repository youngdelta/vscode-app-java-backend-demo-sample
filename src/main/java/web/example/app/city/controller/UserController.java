package web.example.app.city.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.log4j.Log4j2;
import web.example.app.city.mapper.UserMapper;
import web.example.app.config.aop.Pageable;
import web.example.app.domain.dto.PageRequest;
import web.example.app.domain.model.User;

@Controller
@RequestMapping("/users")
@Log4j2
public class UserController {

     private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Pageable(defaultPageNum = 1, defaultPageSize = 5)
    @GetMapping("/user1")
    @ResponseBody
    public List<User> getUsers(@RequestParam Map<String, Object> params) {
        log.info("### getUsers START");
        log.info("### getUsers params : {}",params);
        return userMapper.selectAllUsers(params);
    }

    @GetMapping("/users2")
    @ResponseBody
    public List<User> getUsers(PageRequest pageRequest) {
        log.info("### getUsers2 START");


        PageHelper.startPage(pageRequest.pageNum(), pageRequest.pageSize());
        log.info("### getUsers2 pageNum : {} , pageSize : {}",pageRequest.pageNum(), pageRequest.pageSize());

        List<User> users = userMapper.selectAllUsers(Collections.emptyMap());
        return new PageInfo<>(users).getList();
    }

    @GetMapping("/users3")
     @ResponseBody
    public Map<String, Object> getUsers3(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        RowBounds rowBounds = new RowBounds(offset, pageSize);
        List<User> users = userMapper.selectPagingAllUsers(rowBounds);

        // 전체 개수 조회
        int total = userMapper.selectAllUsersCount(); // 별도 COUNT 쿼리 필요

        Map<String, Object> result = new HashMap<>();
        result.put("list", users);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);

        return result;
    }

     // 2. ArgumentResolver 방식
    @GetMapping("/pageable")
    public List<User> getUsersWithPageRequest(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.pageNum(), pageRequest.pageSize());
        List<User> users = userMapper.selectAllUsers(Collections.emptyMap());
        return new PageInfo<>(users).getList();
        
    }

    // 3. RowBounds 방식
    @GetMapping("/rowbounds")
    public Map<String, Object> getUsersWithRowBounds(@RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        RowBounds rowBounds = new RowBounds(offset, pageSize);
        List<User> users = userMapper.selectPagingAllUsers(rowBounds);
        int total = userMapper.selectAllUsersCount();

        Map<String, Object> result = new HashMap<>();
        result.put("list", users);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return result;
    }

    // 🔹 테스트용: 한 화면에서 3가지 방식 비교
    @GetMapping("/test")
    @ResponseBody
    public Map<String, Object> testPaging(@RequestParam(defaultValue = "1") int pageNum,
                                          @RequestParam(defaultValue = "5") int pageSize) {
        Map<String, Object> result = new LinkedHashMap<>();

        // 1. AOP PageHelper 방식
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        // PageInfo<User> aopPage = (PageInfo<User>) getUsers(params);
        PageInfo<User> aopPage = new PageInfo<>(getUsers(params));  
        result.put("AOP_PageHelper", Map.of(
                "list", aopPage.getList(),
                "total", aopPage.getTotal(),
                "pageNum", aopPage.getPageNum(),
                "pageSize", aopPage.getPageSize()
        ));

        // 2. ArgumentResolver + PageHelper
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        List<User> argList = getUsersWithPageRequest(pageRequest);
        PageInfo<User> argPage = new PageInfo<>(argList);
        result.put("ArgumentResolver_PageHelper", Map.of(
                "list", argPage.getList(),
                "total", argPage.getTotal(),
                "pageNum", argPage.getPageNum(),
                "pageSize", argPage.getPageSize()
        ));

        // 3. RowBounds
        Map<String, Object> rowBoundsMap = getUsersWithRowBounds(pageNum, pageSize);
        result.put("RowBounds", rowBoundsMap);

        return result;
    }

    @GetMapping("/view")
    public String viewPaging(@RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "5") int pageSize,
                            Map<String, Object> model) {

        // 1. AOP PageHelper 방식
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        PageInfo<User> aopPage = new PageInfo<>(getUsers(params));

        // 2. ArgumentResolver 방식
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        List<User> argList = getUsersWithPageRequest(pageRequest);
        PageInfo<User> argPage = new PageInfo<>(argList);

        // 3. RowBounds 방식
        Map<String, Object> rowBoundsMap = getUsersWithRowBounds(pageNum, pageSize);

         // prev / next 계산
        int prevPage = Math.max(pageNum - 1, 1);
        int nextPage = pageNum + 1;

        // 총 페이지 계산
        int totalUsers = (int) aopPage.getTotal(); // 총 개수는 동일
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);


        // 모델에 저장
        model.put("pageNum", pageNum);
        model.put("pageSize", pageSize);
        model.put("totalPages", totalPages);
        model.put("aopPage", aopPage);
        model.put("argPage", argPage);
        model.put("rowBoundsMap", rowBoundsMap);
        model.put("prevPage", prevPage);
        model.put("nextPage", nextPage);

        return "userPagingView"; // templates/userPagingView.html
    }


    /**
     * 
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/viewAjax")
    public String viewPagingAjax(@RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "5") int pageSize,
                            Map<String, Object> model) {

                return "userAjax";
            }
    /**
     * 
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/userAjax")
    public String userPagingAjax(@RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "5") int pageSize,
                            Map<String, Object> model) {

        return "userAjax";
    }


    /**
     * 
     */
    @GetMapping("/userFragment")
    public String userFragment(@RequestParam(defaultValue = "1") int pageNum,
                                @RequestParam(defaultValue = "5") int pageSize,
                                Map<String, Object> model) {

                                    

        return "users-fragment";
    }

    /**
     * 
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/ajax")
    @ResponseBody
    public Map<String, Object> ajaxPaging(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "5") int pageSize) {

        Map<String, Object> response = new LinkedHashMap<>();

        // 1. AOP PageHelper
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        // PageInfo<User> aopPage = (PageInfo<User>) getUsers(params);
        PageInfo<User> aopPage = new PageInfo<>(getUsers(params)); 

        // 2. ArgumentResolver
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        List<User> argList = getUsersWithPageRequest(pageRequest);
        PageInfo<User> argPage = new PageInfo<>(argList);

        // 3. RowBounds
        Map<String, Object> rowBoundsMap = getUsersWithRowBounds(pageNum, pageSize);

        // 총 페이지 계산
        int totalUsers = (int) aopPage.getTotal();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);
        int prevPage = Math.max(pageNum - 1, 1);
        int nextPage = Math.min(pageNum + 1, totalPages);

        response.put("pageNum", pageNum);
        response.put("pageSize", pageSize);
        response.put("totalPages", totalPages);
        response.put("prevPage", prevPage);
        response.put("nextPage", nextPage);
        response.put("aopPage", aopPage);
        response.put("argPage", argPage);
        response.put("rowBoundsMap", rowBoundsMap);

        return response;
    }

    /**
     * 
     */
    @GetMapping("/fragment")
    @ResponseBody
    public Map<String, Object> fragment(
        @RequestParam(defaultValue = "1") int pageNum,
        @RequestParam(defaultValue = "5") int pageSize,
        Map<String, Object> model) {

        // 1. AOP PageHelper 방식
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        PageInfo<User> aopPage = new PageInfo<>(getUsers(params));

        // 2. ArgumentResolver 방식
        PageRequest pageRequest = new PageRequest(pageNum, pageSize);
        List<User> argList = getUsersWithPageRequest(pageRequest);
        PageInfo<User> argPage = new PageInfo<>(argList);

        // 3. RowBounds 방식
        Map<String, Object> rowBoundsMap = getUsersWithRowBounds(pageNum, pageSize);

         // prev / next 계산
        int prevPage = Math.max(pageNum - 1, 1);
        int nextPage = pageNum + 1;

        // 총 페이지 계산
        int totalUsers = (int) aopPage.getTotal(); // 총 개수는 동일
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);


        // 모델에 저장
        model.put("pageNum", pageNum);
        model.put("pageSize", pageSize);
        model.put("totalPages", totalPages);
        model.put("aopPage", aopPage);
        model.put("argPage", argPage);
        model.put("rowBoundsMap", rowBoundsMap);
        model.put("prevPage", prevPage);
        model.put("nextPage", nextPage);


        return model;

        // return "users-fragment";
    }


    
}
