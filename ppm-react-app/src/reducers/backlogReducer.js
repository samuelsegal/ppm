import { GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK } from '../actions/types';

const initialState = {
	projectTasks: [],
	projectTask: {},
};

export default function(state = initialState, action) {
	switch (action.type) {
		case GET_BACKLOG:
			return {
				...state,
				projects: action.payload,
			};
		case GET_PROJECT_TASK:
			return {
				...state,
				project: action.payload,
			};
		case DELETE_PROJECT_TASK:
			return {
				...state,
				projects: state.projectTasks.filter(project => project.projectIdentifier !== action.payload),
			};
		default:
			return state;
	}
}
