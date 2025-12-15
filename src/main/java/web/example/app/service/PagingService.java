package web.example.app.service;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lombok.RequiredArgsConstructor;
import web.example.app.domain.dto.SearchDto;
import web.example.app.domain.entity.PagingEntity;
import web.example.app.paging.PagingRepository;

@Service
@RequiredArgsConstructor
public class PagingService {
    
    private final PagingRepository repository;

    public Page<PagingEntity> getUserList(int pageNo, SearchDto search) {

        PageHelper.startPage(pageNo, 10);

        return repository.findUser(search);
    }
}