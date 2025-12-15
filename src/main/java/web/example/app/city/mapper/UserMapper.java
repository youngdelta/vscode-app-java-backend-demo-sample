package web.example.app.city.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import web.example.app.domain.model.User;

@Mapper
public interface UserMapper {

    /**
     * 
     * @param params
     * @return
     */
    List<User> selectAllUsers(Map<String, Object> params);

    int selectAllUsersCount();

    List<User> selectPagingAllUsers(RowBounds rowBounds);
}