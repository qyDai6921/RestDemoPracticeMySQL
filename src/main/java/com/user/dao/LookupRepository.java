package com.user.dao;

import com.user.entity.LookupEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LookupRepository extends JpaRepository<LookupEntity, Long> {
    public List<LookupEntity> findByType(String type);
}
