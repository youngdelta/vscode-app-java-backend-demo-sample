package web.example.app.mapper;

import java.util.List;

import web.example.app.domain.common.CodeVO;
import web.example.app.domain.dto.CodeDto;

public interface CodeMapper {
    
    public int insertCode(CodeVO codeVO);

	public List<CodeDto> selectCodeList(CodeDto codeDto);

    public CodeDto selectCodeByCd(String cd);

    public int deleteCode(CodeDto codeDto);
}