package web.example.app.domain.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import web.example.app.domain.common.CommonVO;

@Data
@EqualsAndHashCode(callSuper=true)
public class Country extends CommonVO {
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
}
