package web.example.app.repository;

import java.util.List;
import java.util.Optional;

import web.example.app.domain.entity.Emp;
 
/**
 * 
 */
public interface EmpRepository 
// extends JpaRepository<Emp,Integer> 
{
    //emp는 테이블의 정의 INTEGER는 프라이머리 키의 자료형
    Optional<Emp> findByName(String name);
 
    Optional<Emp> findByDeptno(int deptno);
 
    Optional<Emp> findByDeptnoAndName(int deptno,String name);

    /***************************************************** */
	Emp save(Emp emp);

    List<Emp> findAll();

    Optional<Emp> findById(int empno);

    void deleteById(int empno);
}
 