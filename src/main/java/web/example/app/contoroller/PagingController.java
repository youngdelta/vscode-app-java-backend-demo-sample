package web.example.app.contoroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.example.app.domain.dto.SearchDto;
import web.example.app.domain.entity.PagingEntity;
import web.example.app.service.PagingService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PagingController {
    private final PagingService service;

    @GetMapping("/page")
    public String page(@ModelAttribute SearchDto search,
                       @RequestParam(required = false, defaultValue = "1") int pageNum, Model model) {

                        //PageInfo<City> pageInfo = PageHelper.startPage(page, 10)
                // .doSelectPageInfo(() -> cityService.selectCityList(id));

        PageInfo<PagingEntity> p = new PageInfo<>(service.getUserList(pageNum, search), 10);
        
        model.addAttribute("users", p);
        model.addAttribute("search", search);

        return "paging";
    }
}