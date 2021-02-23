import clientReducer from "./reducer/clientReducer";
import clientsReducer from "./reducer/clientsReducer";

let store = {
    _subscriber() {
        console.log("no subscriber");
    },
    _state: {
        common: {
            disabilities: [],
            marital_statuses: [],
            citizenship: [],
            cities: []
        },
        clientPage: {
            clientInfo: null,
            isFetching: false
        },
        clientsPage: {
            clients: [],
            currentPage: 1,
            totalCount: 0,
            pageSize: 5,
            isFetching: false
        }
    },
    getState() {
        return this._state;
    },
    subscribe(observer) {
        this._subscriber = observer;
    },
    dispatch(action) {
        this._state.clientPage = clientReducer(this._state.clientPage, action);
        this._state.clientsPage = clientsReducer(this._state.clientsPage, action);
        this._subscriber(this);
    },
};
export default store;
