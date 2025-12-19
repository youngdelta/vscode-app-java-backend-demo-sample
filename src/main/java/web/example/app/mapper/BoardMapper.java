package web.example.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import web.example.app.domain.AttachVO;
import web.example.app.domain.BoardVO;
 
@Mapper
public interface BoardMapper {
    void saveAttach(List<AttachVO> list);
    void saveBoard(BoardVO board);
    List<Map<String,Object>> getList();
}
 
