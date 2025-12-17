package web.example.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeDto {
    private String codeId;
    private String grpCd;
    private String codeName;
    private String codeValue;
    private String codeType;
    private String codeDesc;
    private String useYn;
    private String regId;
    private String regDt;
    private String modId;
    private String modDt;
}
