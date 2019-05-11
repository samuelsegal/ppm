import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
class Landing extends Component {
	render() {
		const landingPage =
			this.props.security && this.props.security.validToken ? (
				<div />
			) : (
				<div className="landing">
					<div className="light-overlay landing-inner text-dark">
						<div className="container">
							<div className="row">
								<div className="col-md-12 text-center">
									<h1 className="display-3 mb-4">Project Management</h1>
									<p className="lead">Create your account to join active projects or start you own</p>
									<hr />
									<Link to="/register" className="btn btn-lg btn-primary mr-2">
										Sign Up
									</Link>
									<Link to="/login" className="btn btn-lg btn-secondary mr-2">
										Login
									</Link>
								</div>
							</div>
						</div>
					</div>
				</div>
			);
		return landingPage;
	}
}
Landing.propTypes = {
	errors: PropTypes.object.isRequired,
	security: PropTypes.object.isRequired,
};
const mapStateToProps = state => ({
	security: state.security,
	errors: state.errors,
});
export default connect(
	mapStateToProps,
	null
)(Landing);
