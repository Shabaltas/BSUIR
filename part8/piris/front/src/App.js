import './App.css';
import withSuspense from "./hoc/wishSuspense";
import React, {useEffect} from "react";
import {BrowserRouter, Redirect, Route, Switch, withRouter} from 'react-router-dom';
import {connect, Provider} from "react-redux";
import store from "./redux/reduxStore";
import {initializeApp} from "./redux/reducer/appReducer";
import Header from "./components/Header/Header";
import AllClients from "./components/AllClients/AllClients";
import Error from "./components/Error/Error";
import Dialog from "@material-ui/core/Dialog";
import Preloader from "./components/Preloader/Preloader";
import ClientContainer from "./components/Client/ClientContainer";
import {clearErrorAction} from "./redux/reducer/errorReducer";

const App = (props) => {
    const catchAllUnhandledRejection = (event) => {
        console.log(event.promise);
        alert(event.reason);
    };


    useEffect(() => {
        props.initializeApp();
        window.addEventListener("unhandledrejection", catchAllUnhandledRejection);
        return () => window.removeEventListener("unhandledrejection", catchAllUnhandledRejection);
    }, []);

    const closeDialog = () => {
        props.clearError();
    };

    const rows = [
        { id: 1, surname: 'Snow', name: 'Jon', patronymic: 'Yop', date_of_birth: '14/01/2000'},
        { id: 2, surname: 'Wint', name: 'April', patronymic: 'Yop', date_of_birth: '14/01/2000' },
        { id: 3, surname: 'Wendort', name: 'Yana', patronymic: 'Yop', date_of_birth: '14/01/2000' },
        { id: 4, surname: 'Hotr', name: 'August', patronymic: 'Yop', date_of_birth: '14/01/2000' },
    ];
  return (
      props.error
          ? <Dialog aria-labelledby="simple-dialog-title" open={true} onClose={closeDialog}>
              <Error reason={props.error}/>
          </Dialog> :
          props.initialized
              ? <BrowserRouter>
                  <div className="App">
                      <Header/>
                      <div  className="Body">
                          <Switch>
                              <Route path='/clients' exact render={() => <AllClients clients={rows}/>}/>
                              <Route path='/clients/new' render={() => <ClientContainer />}/>
                             <Route path='/clients/:clientId' render={withSuspense(ClientContainer)}/>
                              <Route path='/' exact render={() => <Redirect to='/clients'/>}/>
                              <Route path='*' component={() => <div>404 NOT FOUND</div>}/>
                          </Switch>
                      </div>
                  </div>
              </BrowserRouter>
              : <Preloader/>
  );
}


let mapStateToProps = (state) => {
    return {
        initialized: state.app.initialized,
        error: state.err.error
    }
};

const AppContent = connect(mapStateToProps, {initializeApp, clearError: clearErrorAction})(App);

export default () => {
    return (
        <React.StrictMode>
            <Provider store={store}>
                <AppContent/>
            </Provider>
        </React.StrictMode>
    )
}

