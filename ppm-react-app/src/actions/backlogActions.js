import axios from 'axios';
import { GET_ERRORS, GET_BACKLOG, GET_PROJECT_TASK } from './types';

export const addProjectTask = (backlog_id, project_task, history) => async dispatch => {
	try {
		await axios.post(`/api/backlog/${backlog_id}`, project_task);
		history.push(`/projectBoard/${backlog_id}`);
		dispatch({
			type: GET_ERRORS,
			payload: {},
		});
	} catch (err) {
		dispatch({
			type: GET_ERRORS,
			payload: err.response.data,
		});
	}
};

export const getBacklog = backlog_id => async dispatch => {
	try {
		console.log('backlog id: ' + backlog_id);
		const res = await axios.get(`/api/backlog/${backlog_id}`);
		console.log(res.data);
		dispatch({
			type: GET_BACKLOG,
			payload: res.data,
		});
	} catch (err) {
		dispatch({
			type: GET_ERRORS,
			payload: err.response.data,
		});
	}
};

export const getProjectTask = (backlog_id, pt_sequence, history) => async dispatch => {
	try {
		const res = await axios.get(`/api/backlog/${backlog_id}/${pt_sequence}`);
		dispatch({
			type: GET_PROJECT_TASK,
			payload: res.data,
		});
	} catch (err) {
		console.log('error getting projectTask: ' + err.response.data);
	}
};
