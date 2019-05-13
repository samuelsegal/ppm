package com.sms.ppm.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.ppm.domain.ProjectTask;
import com.sms.ppm.services.MapValidationErrorService;
import com.sms.ppm.services.ProjectTaskService;

/**
 * @author samuelsegal
 * Control the backlog project task REST end points.
 */
@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	private ProjectTaskService projectTaskService;
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	public BacklogController(ProjectTaskService projectTaskService,
			MapValidationErrorService mapValidationErrorService) {
		super();
		this.projectTaskService = projectTaskService;
		this.mapValidationErrorService = mapValidationErrorService;
	}

	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id, Principal principal) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null) {
			return errorMap;
		}
		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

	}

	@GetMapping("/all")
	public Iterable<ProjectTask> getAllProjects() {
		Iterable<ProjectTask> allProjects = projectTaskService.findAllProjectTasks();
		return allProjects;
	}

	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getaProjectBacklog(@PathVariable String backlog_id, Principal principal) {
		return projectTaskService.findBacklogByIdAndUsername(backlog_id, principal.getName());
	}

	@GetMapping("/{backlog_id}/{pt_sequence}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_sequence,
			Principal principal) {

		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_sequence,
				principal.getName());
		return new ResponseEntity<>(projectTask, HttpStatus.OK);
	}

	@PatchMapping("/{backlog_id}/{pt_sequence}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlog_id, @PathVariable String pt_sequence, Principal principal) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null) {
			return errorMap;
		}
		ProjectTask updateTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_sequence,
				principal.getName());
		return new ResponseEntity<>(updateTask, HttpStatus.OK);
	}

	@DeleteMapping("/{backlog_id}/{pt_sequence}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_sequence,
			Principal principal) {
		projectTaskService.deleteByProjectSequence(backlog_id, pt_sequence, principal.getName());
		return new ResponseEntity<>("Project Task " + pt_sequence + " has been deleted.", HttpStatus.OK);
	}
}
