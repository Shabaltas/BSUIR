import {applyMiddleware, combineReducers, compose, createStore} from "redux";
import clientReducer from "./reducer/clientReducer";
import { reducer as formReducer } from 'redux-form'
import thunkMiddleware from 'redux-thunk';
import appReducer from "./reducer/appReducer";
import errorReducer from "./reducer/errorReducer";

let reducers = combineReducers({
    clientPage: clientReducer,
    form: formReducer,
    app: appReducer,
    err: errorReducer
});
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(reducers, composeEnhancers(applyMiddleware(thunkMiddleware)));
export default store;
