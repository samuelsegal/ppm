import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import classnames from 'classnames';
import { getProjectTask, updateProjectTask } from '../../../actions/backlogActions';
import PropTypes from 'prop-types';
import { TO_DO, IN_PROGRESS, COMPLETE } from '../../../util/constants';
class UpdateProjectTask extends Component {
	constructor(props) {
		super(props);
		const { id } = this.props.match.params;
		this.state = {
			summary: '',
			projectSequence: '',
			acceptanceCriteria: '',
			status: '',
			priority: 0,
			dueDate: '',
			projectIdentifier: id,
			errors: {},
		};
	}
	componentWillReceiveProps = nextProps => {
		if (nextProps.errors) {
			this.setState({ errors: nextProps.errors });
		}
		const {
			id,
			summary,
			projectSequence,
			acceptanceCriteria,
			status,
			priority,
			dueDate,
			projectIdentifier,
		} = nextProps.project_task;
		this.setState({
			id,
			summary,
			projectSequence,
			acceptanceCriteria,
			status,
			priority,
			dueDate,
			projectIdentifier,
		});
	};
	componentDidMount = () => {
		const { backlog_id, pt_sequence } = this.props.match.params;
		this.props.getProjectTask(backlog_id, pt_sequence, this.props.history);
	};
	onSubmit = e => {
		e.preventDefault();
		const updateProjectTask = {
			id: this.state.id,
			projectSequence: this.state.projectSequence,
			summary: this.state.summary,
			acceptanceCriteria: this.state.acceptanceCriteria,
			status: this.state.status,
			priority: this.state.priority,
			dueDate: this.state.dueDate,
			projectIdentifier: this.state.projectIdentifier,
		};

		this.props.updateProjectTask(
			this.state.projectIdentifier,
			this.state.projectSequence,
			updateProjectTask,
			this.props.history
		);
	};
	onChange = e => {
		this.setState({ [e.target.name]: e.target.value });
	};
	render() {
		const { backlog_id } = this.props.match.params;
		const { errors } = this.state;
		return (
			<div className="add-PBI">
				<div className="container">
					<div className="row">
						<div className="col-md-8 m-auto">
							<Link to={`/projectBoard/${backlog_id}`} className="btn btn-light">
								Back to Project Board
							</Link>
							<h4 className="display-4 text-center">Add / Update Project Task</h4>
							<p className="lead text-center">Project Name + Project Code</p>
							<form onSubmit={this.onSubmit}>
								<div className="form-group">
									<input
										type="text"
										className={classnames('form-control form-control-lg', {
											'is-invalid': errors.summary,
										})}
										name="summary"
										placeholder="Project Task summary"
										onChange={this.onChange}
										value={this.state.summary}
									/>
									{errors.summary && <div className="invalid-feedback">{errors.summary}</div>}
								</div>

								<div className="form-group">
									<textarea
										className="form-control form-control-lg"
										placeholder="Acceptance Criteria"
										name="acceptanceCriteria"
										onChange={this.onChange}
										value={this.state.acceptanceCriteria}
									/>
								</div>
								<h6>Due Date</h6>
								<div className="form-group">
									<input
										type="date"
										className="form-control form-control-lg"
										name="dueDate"
										onChange={this.onChange}
										value={this.state.dueDate}
									/>
								</div>
								<div className="form-group">
									<select
										className="form-control form-control-lg"
										name="priority"
										onChange={this.onChange}
										value={this.state.priority}
									>
										<option value={0}>Select Priority</option>
										<option value={1}>High</option>
										<option value={2}>Medium</option>
										<option value={3}>Low</option>
									</select>
								</div>

								<div className="form-group">
									<select
										className="form-control form-control-lg"
										name="status"
										onChange={this.onChange}
										value={this.state.status}
									>
										<option value="">Select Status</option>
										<option value={TO_DO}>TO_DO</option>
										<option value={IN_PROGRESS}>IN_PROGRESS</option>
										<option value={COMPLETE}>COMPLETE</option>
									</select>
								</div>

								<input type="submit" className="btn btn-primary btn-block mt-4" />
							</form>
						</div>
					</div>
				</div>
			</div>
		);
	}
}
UpdateProjectTask.propTypes = {
	getProjectTask: PropTypes.func.isRequired,
	updateProjectTask: PropTypes.func.isRequired,
	errors: PropTypes.object.isRequired,
};
const mapStateToProps = state => ({
	errors: state.errors,
	project_task: state.backlog.project_task,
});
export default connect(
	mapStateToProps,
	{ getProjectTask, updateProjectTask }
)(UpdateProjectTask);
