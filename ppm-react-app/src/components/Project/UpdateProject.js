import React, { Component } from 'react';
import classnames from 'classnames';
import { getProject, createProject } from '../../actions/projectActions';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

/*
Note: For demonstration purposes, this class demonstrates 2 ways for handling input:
  * 1) Controlled component (https://reactjs.org/docs/forms.html) 
  * 2) Uncontrolled component (https://reactjs.org/docs/uncontrolled-components.html)
  * The uncontrolled components are created in constructor
  * this.projectName = React.createRef();
  * this.description = React.createRef();
  * The ref attribute is used instead of value attribute for input element
  * The controlled component uses combination of onChange(e) and value attribute
  * Using uncontrolled component changes source of truth to be directly from DOM.
  * Good article for when to use one or the other - https://goshakkk.name/controlled-vs-uncontrolled-inputs-react/
*/
class UpdateProject extends Component {
	constructor() {
		super();
		this.state = {
			id: '',
			projectName: '',
			projectIdentifier: '',
			description: '',
			start_date: '',
			end_date: '',
			errors: {},
		};
		this.onChange = this.onChange.bind(this);
		this.onSubmit = this.onSubmit.bind(this);

		this.projectName = React.createRef();
		this.description = React.createRef();
		console.dir(this.projectName);
	}
	componentWillReceiveProps(nextProps) {
		if (nextProps.errors) {
			this.setState({ errors: nextProps.errors });
		}
		const { id, projectName, projectIdentifier, description, start_date, end_date } = nextProps.project;
		this.setState({ id, projectName, projectIdentifier, description, start_date, end_date });
		this.projectName.value = projectName;
		this.description.current.value = description;
	}
	componentDidMount() {
		const { id } = this.props.match.params;
		this.props.getProject(id, this.props.history);
	}

	onChange(e) {
		this.setState({ [e.target.name]: e.target.value });
	}
	onSubmit(e) {
		e.preventDefault();

		const updateProject = {
			id: this.state.id,
			projectName: this.projectName.value,
			projectIdentifier: this.state.projectIdentifier,
			description: this.description.current.value,
			start_date: this.state.start_date,
			end_date: this.state.end_date,
		};
		this.props.createProject(updateProject, this.props.history);
	}
	render() {
		const { errors } = this.state;

		return (
			<div className="project">
				<div className="container">
					<div className="row">
						<div className="col-md-8 m-auto">
							<h5 className="display-4 text-center">Update Project form</h5>
							<hr />
							<form onSubmit={this.onSubmit}>
								<div className="form-group">
									<input
										type="text"
										placeholder="Project Name"
										name="projectName"
										ref={input => (this.projectName = input)}
										//value={this.state.projectName}
										//onChange={this.onChange}
										className={classnames('form-control form-control-lg', {
											'is-invalid': errors.projectName,
										})}
									/>
									{errors.projectName && <div className="invalid-feedback">{errors.projectName}</div>}
								</div>

								<div className="form-group">
									<input
										type="text"
										placeholder="Unique Project ID"
										name="projectIdentifier"
										value={this.state.projectIdentifier}
										onChange={this.onChange}
										className={classnames('form-control form-control-lg', {
											'is-invalid': errors.projectIdentifier,
										})}
										disabled
									/>
									{errors.projectIdentifier && (
										<div className="invalid-feedback">{errors.projectIdentifier}</div>
									)}
								</div>
								<div className="form-group">
									<textarea
										placeholder="Project Description"
										name="description"
										//value={this.state.description}
										//onChange={this.onChange}
										ref={this.description}
										className={classnames('form-control form-control-lg', {
											'is-invalid': errors.description,
										})}
									/>
									{errors.description && <div className="invalid-feedback">{errors.description}</div>}
								</div>
								<h6>Start Date</h6>
								<div className="form-group">
									<input
										type="date"
										className="form-control form-control-lg"
										name="start_date"
										value={this.state.start_date}
										onChange={this.onChange}
									/>
								</div>
								<h6>Estimated End Date</h6>
								<div className="form-group">
									<input
										type="date"
										className="form-control form-control-lg"
										name="end_date"
										value={this.state.end_date}
										onChange={this.onChange}
									/>
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

UpdateProject.propTypes = {
	getProject: PropTypes.func.isRequired,
	createProject: PropTypes.func.isRequired,
	project: PropTypes.object.isRequired,
	errors: PropTypes.object.isRequired,
};

const mapStateToProps = state => ({
	project: state.project.project,
	errors: state.errors,
});

export default connect(
	mapStateToProps,
	{ getProject, createProject }
)(UpdateProject);
