package com.sms.ppm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.ppm.domain.PPMUser;

@Repository
public interface PPMUserRepository extends JpaRepository<PPMUser, Long>{
	PPMUser findByUsername(String username);
	PPMUser getById(Long id);
}
