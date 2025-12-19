package web.example.app.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// import com.ezen.spring.web.mapper.BoardMapper;
// import com.ezen.spring.web.vo.AttachVO;
// import com.ezen.spring.web.vo.BoardVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import web.example.app.domain.AttachVO;
import web.example.app.domain.BoardVO;
import web.example.app.mapper.BoardMapper;
 
@Service
@Slf4j
public class BoardService {
 
    @Autowired
    ResourceLoader resourceLoader;
 
    @Autowired
    BoardMapper dao;
 

    /**
     * 
     * @param mfiles
     * @param request
     * @param board
     * @return
     */
    public boolean saveBoardandAttach(MultipartFile[] mfiles, HttpServletRequest request, BoardVO board) {
        ServletContext context = request.getServletContext();
        String savePath = context.getRealPath("/WEB-INF/files");
        dao.saveBoard(board);
        List<AttachVO> list = new ArrayList<>();


        try {
            for (int i = 0; i < mfiles.length; i++) {
                if (mfiles[0].getSize() == 0)
                    continue;
                mfiles[i].transferTo(new File(savePath + "/" + mfiles[i].getOriginalFilename()));
                String fname = mfiles[i].getOriginalFilename();
                long fsize = mfiles[i].getSize();
 
                AttachVO att = new AttachVO();
                att.setFname(fname);
                att.setFsize(fsize);
                list.add(att);
                dao.saveAttach(list);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }


    /**
     * 
     * @param a
     * @param b
     * @return
     */
    public Map<String, Object> getPage(int a, int b) {
        PageHelper.startPage(a, b);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(dao.getList());
        List<Map<String, Object>> mlist = pageInfo.getList();
        List<BoardVO> blist = new ArrayList<>();

        for (int i = 0; i < mlist.size(); i++) {
            Map<String, Object> m = mlist.get(i);
            BigDecimal big = (java.math.BigDecimal) m.get("BOARDID");
            BoardVO board = new BoardVO(big.intValue());
            boolean found = false;
            if (blist.contains(board)) {
                board = blist.get(blist.indexOf(board));
                found = true;
            }
            board.setTitle((String) m.get("TITLE"));
            board.setAuthor((String) m.get("AUTHOR"));
 
            
            String fileNames = (String)m.get("FNAMES");
            if (fileNames.length()<=2 ) {
                if (!found)
                    blist.add(board);
                continue;
            }else {
                String[] file = fileNames.split("/");
                for (int y = 0; y < file.length; y++) {
                    AttachVO att = new AttachVO(file[y]);
                    board.getAttList().add(att);
                }
            }
            blist.add(board);
            /*
             * AttachVO att = new AttachVO(); att.setFname((String) objFname); big =
             * (BigDecimal) m.get("ATTACHID"); att.setAttachid(big.intValue()); big =
             * (BigDecimal) m.get("FSIZE"); att.setFsize(big.intValue());
             * 
             * board.getAttList().add(att); // Board에 Attach 연결 if (!found)
             * blist.add(board);
             */
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo", pageInfo);
        map.put("blist", blist);

        return map;
    }
}