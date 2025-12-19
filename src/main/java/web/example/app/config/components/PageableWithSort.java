package web.example.app.config.components;

import org.springframework.data.domain.Pageable;

public interface PageableWithSort extends Pageable {
    String getOrderBy();
    String getOrderDirection();
    void setOrderBy(String orderBy);
    void setOrderDirection(String orderDirection);
}
