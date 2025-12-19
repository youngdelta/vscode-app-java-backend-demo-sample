package web.example.app.contoroller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import web.example.app.domain.entity.Emp;
 
@Controller
@RequestMapping("/th")
public class ThymeleafController {
    @GetMapping("/")
    public String index(Model m, HttpServletRequest session) {
        m.addAttribute("greeting", "Hello world!");
        m.addAttribute("url", "th/");
        m.addAttribute("msg", "Happy New Year!");
        m.addAttribute("success", true);
        m.addAttribute("gender", "M");
        m.addAttribute("nums", new int[] { 1, 3, 5, 7, 9 });
        m.addAttribute("scores", Arrays.asList(5, 7, 9, 10));
        m.addAttribute("names", Arrays.asList("smith", "mary", "jone"));
        // asList = List 를 생성해줌
        session.setAttribute("userid", "login-userid");
        // m.addAttribute("emp", new Emp(11, "smith", 20, java.sql.Date.valueOf("2022-12-25")));
        m.addAttribute("emp", Emp.builder().empno(11).name("smith").deptno(20).hiredate(java.sql.Date.valueOf("2022-12-25")).build() );
        Map<String, Integer> map = new HashMap<>();
        map.put("smith", 78);
        map.put("mary", 88);
        map.put("jone", 66);
        m.addAttribute("credit", map);
        return "emp/index";
    }
}
