package com.test.euax.gerenciadorproject.repository;

import com.test.euax.gerenciadorproject.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{


}
