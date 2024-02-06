package com.example.constructionmanagementspring.repository;

import com.example.constructionmanagementspring.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {

}
