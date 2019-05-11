import { SET_CURRENT_USER } from '../actions/types';

const initialState = {
	validToken: false,
	user: {},
};

export default function(state = initialState, action) {
	switch (action.type) {
		case SET_CURRENT_USER:
			return {
				...state,
				validToken: action.payload ? true : false,
				user: action.payload,
			};
		default:
			return state;
	}
}
