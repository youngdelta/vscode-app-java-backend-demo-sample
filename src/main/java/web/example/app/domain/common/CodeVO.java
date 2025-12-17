package web.example.app.domain.common;

import java.sql.Timestamp;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeVO {

    @NotBlank(message = "grpCd is mandatory")
    @Size(min = 1, max = 16, message = "grpCd must be between 1 and 16")
    private String grpCd;

    @NotBlank
    @Size(min = 1, max = 16)
    private String cd;

    @NotBlank
    @Size(min = 1, max = 50)
    private String grpCdNm;

    @NotBlank
    @Size(min = 1, max = 50)
    private String cdNm;

    @Min(1)
    private int sortOrder;

    private Timestamp regDt;

    private boolean useYn;

    @Builder
    public CodeVO(String grpCd, String cd, String grpCdNm, String cdNm, int sortOrder, Timestamp regDt, boolean useYn) {
        this.grpCd = grpCd;
        this.cd = cd;
        this.grpCdNm = grpCdNm;
        this.cdNm = cdNm;
        this.sortOrder = sortOrder;
        this.regDt = regDt;
        this.useYn = useYn;
    }
}