package web.example.app.config.components;

import web.example.app.config.aop.Pageable;

public interface PageableWithSort extends Pageable {
    String getOrderBy();
    String getOrderDirection();
    void setOrderBy(String orderBy);
    void setOrderDirection(String orderDirection);
    
}
