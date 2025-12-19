package web.example.app.contoroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import web.example.app.domain.BoardVO;
import web.example.app.service.BoardService;
 
@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    ResourceLoader resourceLoader;
 
    @Autowired
    private BoardService svc;
 
    @GetMapping("/add")
    public String getForm() {
        return "board/boardAdd";
    }
 
    @PostMapping("/add")
    @ResponseBody
    @Transactional
    public String upload(@RequestParam("files") MultipartFile[] mfiles, HttpServletRequest request, BoardVO board) {
        if (svc.saveBoardandAttach(mfiles, request, board)) {
            String msg = String.format("파일(%d)개 저장성공(작성자:%s)", mfiles.length, board.getAuthor().toString());
            return msg;
        } else
            return "파일 저장 실패:";
    }
    @GetMapping("/page/{pageNum}/{rowNum}")
    public String getPage(@PathVariable("pageNum") int a, @PathVariable("rowNum") int b, Model m) {
        Map<String, Object> map = svc.getPage(a, b);
        m.addAttribute("pageInfo", map.get("pageInfo"));
        m.addAttribute("blist", map.get("blist"));
 
        return "board/boardList";
    }
}
