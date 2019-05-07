import React, { Component } from 'react';
import ProjectTask from './ProjectTasks/ProjectTask';
import { TO_DO, IN_PROGRESS, COMPLETE } from '../../util/constants';

class Backlog extends Component {
	render() {
		const { project_tasks_prop } = this.props;
		const tasks = project_tasks_prop.map(project_task => (
			<ProjectTask key={project_task.id} project_task={project_task} />
		));
		let todDoTasks = [];
		let inProgressTasks = [];
		let completeTasks = [];

		for (let i = 0; i < tasks.length; i++) {
			let status = tasks[i].props.project_task.status;
			if (status === TO_DO) {
				todDoTasks.push(tasks[i]);
			} else if (status === IN_PROGRESS) {
				inProgressTasks.push(tasks[i]);
			} else if (status === COMPLETE) {
				completeTasks.push(tasks[i]);
			} else {
				console.log(`Invalid task status: ${status}`);
			}
		}
		return (
			<div className="container">
				<div className="row">
					<div className="col-md-4">
						<div className="card text-center mb-2">
							<div className="card-header bg-secondary text-white">
								<h3>TO_DO</h3>
							</div>
						</div>
						{todDoTasks}
					</div>
					<div className="col-md-4">
						<div className="card text-center mb-2">
							<div className="card-header bg-primary text-white">
								<h3>IN_PROGRESS</h3>
							</div>
						</div>
						{inProgressTasks}
					</div>
					<div className="col-md-4">
						<div className="card text-center mb-2">
							<div className="card-header bg-success text-white">
								<h3>COMPLETE</h3>
							</div>
						</div>
						{completeTasks}
					</div>
				</div>
			</div>
		);
	}
}
export default Backlog;
