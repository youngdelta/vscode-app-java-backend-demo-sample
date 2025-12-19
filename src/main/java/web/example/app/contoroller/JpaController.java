package web.example.app.contoroller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import web.example.app.domain.entity.Emp;
import web.example.app.repository.EmpRepository;
 
/**
 * 
 */
// @Controller
@Slf4j
// @RequestMapping("/jpa")
public class JpaController {

    @Autowired
    private EmpRepository repo;
 

    /**
     * 
     * @return
     */
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "JPA Controller Working...";
    }
    
    /**
     * 
     * @return
     */
    @GetMapping("/add")
    @ResponseBody
    public String add() {
        Date hiredate = Date.valueOf("2022-12-28");
        Emp emp = new Emp(11,"smith", 20,300,hiredate);
        Emp saved = repo.save(emp);
        log.info(saved.toString());

        return saved.toString();
    }
    

    /**
     * 
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public String list() {
        
        List<Emp> saved = repo.findAll();
        log.info(saved.toString());
        
        return saved.toString();
    }
    @GetMapping("/find/{empno}")
    @ResponseBody
    public String findById(@PathVariable int empno) {
        
        //Emp saved = repo.findById(empno).get();
        //Optional은 없으면 에러가 뜨는게 아니라 null이 나온다.
        
        Optional<Emp> op = repo.findById(empno);
        if(op.isEmpty()/*op가 null인 경우*/) {
            return empno+"번호로 검색실패";
        }
        return op.get().toString();
    }


    /**
     * 
     * @param name
     * @return
     */
    @GetMapping("/name/{name}")
    @ResponseBody
    public String findByName(@PathVariable String name) {
        
        Optional<Emp> op = repo.findByName(name);
        if(op.isEmpty()) {
            return name+"이름으로 검색실패";
        }
        return op.get().toString();
    }

    /**
     * 
     * @param deptno
     * @return
     */
    @GetMapping("/deptno/{deptno}")
    @ResponseBody
    public String findByDeptno(@PathVariable int deptno) {
        Optional<Emp> op = repo.findByDeptno(deptno);
        if(op.isEmpty()) {
            return deptno+" 부서번호로 검색실패";
        }
        return op.get().toString();
    }

    /**
     * 
     * @param deptno
     * @param name
     * @return
     */
    //특정부서에 근무하는 인원의 이름 검색 (WHERE deptno=? and name=?)
    @GetMapping("/finddeptnoandname/{deptno}/{name}")
    @ResponseBody
    public String findByDeptnoAndName(@PathVariable("deptno") int deptno, @PathVariable("name") String name) {
        Optional<Emp> op = repo.findByDeptnoAndName(deptno,name);
        if(op.isEmpty()) {
            return deptno+"/"+name+" 근무자 검색실패";
        }
        return op.get().toString();
    }
    

    /**
     *  
     * @param empno
     * @param deptno
     * @param sal
     * @return
     */
    //수정 : Entity 객체를 생성하여 save() 하면 해당 pk 인 곳을 찾아 수정해준다.
    //save()는 수정 후 수정된 객체를 리턴하므로 수정여부를 판단하는데 사용가능하다.
    @GetMapping("/update/{empno}/{deptno}/{sal}")
    @ResponseBody
    public String updateByEmpno(@PathVariable int empno,
            @PathVariable int deptno, @PathVariable int sal) {
        Optional<Emp> op = repo.findById(empno);
        if(op.isEmpty()/*op가 null인 경우*/) {
            return empno+"번호로 검색실패";
        }
        Emp emp = op.get();
        emp.setDeptno(deptno);
        emp.setSal(sal);
        return repo.save(emp).toString();
    }

    /**
     * 
     * @param empno
     * @return
     */
    @GetMapping("/delete/{empno}")
    @ResponseBody
    public String deleteByEmpno(@PathVariable int empno) {
        Optional<Emp> op = repo.findById(empno);
        if(op.isEmpty()) {
            return empno+"번호로 검색실패";
        }
        else repo.deleteById(empno);
        return "삭제성공";
    }
}
