package web.example.app.contoroller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class BaseController {

    @GetMapping("/basic")
    public String textBasic(Model model) {
        model.addAttribute("name", "Spring");
        model.addAttribute("version", "6.0");
        model.addAttribute("data", "Hello <b>String-boot</b>");

        return "basic/text-basic";
    }

    @GetMapping("/basic-objects")
    public String basicObjects(HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        return "basic/basic-objects";
    }

     @GetMapping("/basic-objects2")
    public String basicObjects2(
                Model model, 
                HttpServletRequest request,
                HttpServletResponse response,
                HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());

        return "basic/basic-objects";
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello " + data;
        }
    }

}
