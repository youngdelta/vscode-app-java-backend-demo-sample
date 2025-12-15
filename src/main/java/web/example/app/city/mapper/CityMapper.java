package web.example.app.city.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;

import lombok.RequiredArgsConstructor;
import web.example.app.domain.model.City;

// @Mapper
@RequiredArgsConstructor
public class CityMapper {

	private final SqlSession sqlSession;
//	private final SqlSessionTemplate sqlSessionTemplate;

//	public CityMapper(SqlSession sqlSession) {
//		this.sqlSession = sqlSession;
//	}

	/**
	 * @param id
	 * @return
	 */
	public City findByState(String id) {
		
//		return sqlSessionTemplate.selectOne("selectCityByid", id);
		return sqlSession.selectOne("selectCityByid", id);
	}

	/**
	 * @param id
	 * @return
	 */
	public City selectCity(String id) {
		return sqlSession.selectOne("selectCityByid", id);
//		return sqlSessionTemplate.selectOne("selectCityByid", id);
	}

}
