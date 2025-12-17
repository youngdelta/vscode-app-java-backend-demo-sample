package web.example.app.contoroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import web.example.app.config.global.SuccessCode;
import web.example.app.domain.common.CodeVO;
import web.example.app.service.CodeService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/code")
public class CodeController {


    @Autowired
    private CodeService codeService;

	/**
     * [API] 코드 등록
     *
     * @return ResponseEntity<Integer> : 응답 결과 및 응답 코드 반환
     */
    @PostMapping("/insertCode")
    // @Operation(summary = "코드 등록", description = "코드 등록")
    public ResponseEntity<Integer> insertCode(@RequestBody @Valid CodeVO codeVO) {
        log.debug("코드를 등록합니다.");
        Integer resultList = codeService.insertCode(codeVO);

        // ApiResponse apiResponse = ApiResponse.builder()
        //         // .code(resultList)
        //         // .message("코드 등록이 완료되었습니다.")
        //         .result(resultList)
        //         .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
        //         .resultMessage(SuccessCode.INSERT_SUCCESS.getMessage())
        //         .build();

        return new ResponseEntity<>(resultList, HttpStatus.OK);
        // return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    
}