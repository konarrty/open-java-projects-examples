package com.example.constructionmanagementspring.repository;

import com.example.constructionmanagementspring.model.Tender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenderRepository extends JpaRepository<Tender, Long> {

}
