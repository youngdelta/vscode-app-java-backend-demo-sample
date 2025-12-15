package web.example.app.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import web.example.app.config.global.GlobalVariables;

@Data
@RequiredArgsConstructor
public class BoardListSearchDTO {
    private int pageNum;
    
    // @Min(value = 10)
    // @Size(min = 1, max = 16, message = "grpCd must be between 1 and 16")
    // @Min(value = 1, message = "grpCd must be between 1 and 16")
    private int pageSize;
    private String searchType;
    private String keyword;

    private String code;
	private String name;
	private String continent;
	private String region;
	private Double surfaceArea;
	private Integer indepYear;
	private Integer population;
	private Double lifeExpectancy;
	private Double gnp;
	private Double gnpOld;
	private String localName;
	private String governmentForm;
	private String headOfState;
	private Integer capital;
	private String code2;

    /* public void setPageSze(int pageSize) {

        if (pageSize == 0) {
            this.pageSize = GlobalVariables.DEFAULT_PAGE_SIZE;
        }
        
    } */
}
