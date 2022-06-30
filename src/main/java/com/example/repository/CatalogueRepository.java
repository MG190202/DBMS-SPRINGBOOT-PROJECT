package com.example.repository;

import com.example.entity.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue,Integer>{
    public Catalogue getCatalogueByAccessID(@Param("x") Integer roomno);
}
