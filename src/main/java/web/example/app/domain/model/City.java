package web.example.app.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import web.example.app.domain.common.CommonVO;

@Data
@EqualsAndHashCode(callSuper=true)
public class City extends CommonVO {
	
	private String id;
	private String name;
	private String countryCode;
	private String district;
	private String population;

}