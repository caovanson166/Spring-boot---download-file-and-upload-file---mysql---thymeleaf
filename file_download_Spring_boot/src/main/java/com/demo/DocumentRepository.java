package com.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<entity,Integer>{
	@Query("select new entity(e.id,e.name,e.size) from entity e order by e.uploadTime desc")
	List<entity> findAll();
}
