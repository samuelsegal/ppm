package com.sms.ppm;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sms.ppm.domain.PPMUser;
import com.sms.ppm.domain.Project;
import com.sms.ppm.repositories.PPMUserRepository;
import com.sms.ppm.services.ProjectService;
import com.sms.ppm.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PpmApplicationTests {
	@Autowired
	protected ProjectService projectService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected PPMUserRepository ppmUserRepository;

	static PPMUser testUser;
	static Project project;
	
	@BeforeClass
	public static void init() {
		testUser = new PPMUser();
		testUser.setFullname("Tester");
		testUser.setUsername("tester@tester.com");
		testUser.setPassword("password");
		
		project = new Project();
		project.setPpmuser(testUser);
		project.setProjectIdentifier("junit");
		project.setDescription("descript");
		project.setProjectName("junit name");
	}
	@Test
	public void getAllProjects() {


		userService.saveUser(testUser);

		projectService.saveOrUpdateProject(project, testUser.getUsername());
		Iterable<Project> projects = projectService.findAllProjects();
		if (log.isDebugEnabled()) {
			projects.forEach(p -> {
				log.debug("Found project: {}", p);
			});
		}
		projectService.deleteProjectByIdentifier(project.getProjectIdentifier(), testUser.getUsername());
		ppmUserRepository.delete(testUser);
	}

	@Test
	public void contextLoads() {
	}

}
