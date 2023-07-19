package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Approval;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends CrudRepository<Approval, Long> {

    @Override
    List<Approval> findAll(); //Iterable 형식 말고 List 형식으로 받기
}
