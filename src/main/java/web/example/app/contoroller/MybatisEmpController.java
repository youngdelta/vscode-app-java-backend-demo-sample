package web.example.app.contoroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// import com.ezen.spring.web.mapper.CRUDMapper;
// import com.ezen.spring.web.mapper.EmpMapper;
// import com.ezen.spring.web.vo.Emp;
import lombok.extern.slf4j.Slf4j;
import web.example.app.domain.entity.Emp;
import web.example.app.mapper.CRUDMapper;
import web.example.app.mapper.EmpMapper;
 
@Controller
@RequestMapping("/mybatis/emp")
@Slf4j
public class MybatisEmpController {
    @Autowired
    private EmpMapper dao;
    
    @Autowired
    private CRUDMapper dao2;
    
    @GetMapping("/list")
    public String list() {
        return dao.getlist().toString();
    }
    @GetMapping("/listByDeptno/{deptno}")
    public String listByDeptno(@PathVariable(name="deptno") int deptno) {
        return dao.listByDeptno(deptno).toString();
    }
    @GetMapping("/deleteByEmpno/{empno}")
    public Boolean deleteByEmpno(@PathVariable(name="empno") int empno) {
        Emp emp = new Emp();
        emp.setEmpno(empno);
        return dao.deleteByEmpno(emp)>0?true:false;
    }
    /*
     * @GetMapping("/deleteByEmpno")   주소창에 /mybatis/emp/deleteByEmpno?empno=7369
        public Boolean deleteByEmpno(Emp emp) {
        return dao.deleteByEmpno(emp)>0?true:false;
    }
     */
    @GetMapping("/updateByDeptno/{deptno}/{sal}")
    public Boolean updateByDeptno(@PathVariable Map<String,Integer> map) {
        return dao.updateByDeptno(map)>0?true:false;
    }
    @GetMapping("/getListWithDeptno")
    public String getListWithDeptno() {
        return dao.getListWithDeptno().toString();
    }
    @GetMapping("/searchform")
    public String search() {
        return "/emp/SearchEMP";
    }
    @PostMapping("/search")//리퀘스트바디를 폼데이터 자체로 받겠다. 멀맵은 자바 표준이 아니고 스프링에만 있다.
    @ResponseBody
    public String search(@RequestBody MultiValueMap<String,Object> mulmap) {
                                    //하나의 이름으로 다중 값을 가진 파라미터도 처리하기 위함
        Map<String,Object> map = mulmap.toSingleValueMap();
        log.info(map.toString());
        return dao2.search(map).toString();
    }
}
 
