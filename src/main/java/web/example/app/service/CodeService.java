package web.example.app.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.example.app.config.global.ErrorCode;
import web.example.app.domain.common.CodeVO;
import web.example.app.domain.dto.CodeDto;
import web.example.app.global.BusinessExceptionHandler;
import web.example.app.mapper.CodeMapper;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "code")
public class CodeService {
    
    private final SqlSession sqlSession;

    // public CodeService(SqlSession ss) {
    //     this.sqlSession = ss;
    // }


    /**
	 * 코드 리스트를 조회합니다.
	 *
	 * @param codeDto CodeDto
	 * @return List<CodeDto>
	 */
	@Transactional(readOnly = true)
	// @Cacheable(value = "codeCacheInfo", key = "#codeDto.grpCd")
    @Caching(
        cacheable = {
                @Cacheable(value = "codeCacheInfo", key = "#codeDto.grpCd")
        },
        put = {
                @CachePut(value = "codeCacheInfo", key = "#codeDto.grpCd"),
        }
)
	public List<CodeDto> selectCodeList(CodeDto codeDto) {
	    CodeMapper cm = sqlSession.getMapper(CodeMapper.class);
	    return cm.selectCodeList(codeDto);
	}


    /**
	 * 코드 키 값을 기반으로 코드 정보를 조회합니다
	 *
	 * @param cd String
	 * @return CodeDto
	 */
	@Transactional(readOnly = true)
	@CachePut(value = "codeCacheInfo", key = "#cd")
	public CodeDto selectCodeByCd(String cd) {
	    CodeMapper cm = sqlSession.getMapper(CodeMapper.class);
	    return cm.selectCodeByCd(cd);
	}


    /**
	 * 코드 삭제
	 *
	 * @param codeDto CodeDto
	 * @return int
	 */
	@Transactional
	@CacheEvict(value = "codeCacheInfo", key = "#codeDto.grpCd", beforeInvocation = false)
	public int deleteCode(CodeDto codeDto) {
	    CodeMapper cm = sqlSession.getMapper(CodeMapper.class);
	    try {
	        return cm.deleteCode(codeDto);
	    } catch (Exception e) {
	        throw new BusinessExceptionHandler(e.getMessage(), ErrorCode.INSERT_ERROR);
	    }
	}



    /**
     * 코드 INSERT
     *
     * @param codeVO 코드
     */
    @Transactional
    public Integer insertCode(CodeVO codeVO) {

        CodeMapper cm = sqlSession.getMapper(CodeMapper.class);
        int result = 0;
        try {
            result = cm.insertCode(codeVO);
            // 강제로 에러를 발생 시킵니다.
            throw new BusinessExceptionHandler(ErrorCode.INSERT_ERROR.getMessage(), ErrorCode.INSERT_ERROR);
        } catch (Exception e) {
            throw new BusinessExceptionHandler(e.getMessage(), ErrorCode.INSERT_ERROR); // errorCode : 9999
        }
    }
}