package com.sms.ppm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.ppm.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByProjectIdentifier(String projectId);

    //@Query(value="select p from Project p inner join p.backlog")


    @Override
    List<Project> findAll();
}
