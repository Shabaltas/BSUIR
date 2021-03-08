import React from 'react';
import {Routes} from 'app/constants/Routes';
import {BrowserRouter, Redirect, Route, Switch} from 'react-router-dom';

import {Users} from 'app/ui/Users';
import {EditUserComponent} from 'app/ui/EditUserComponent';
import {HeaderContainer} from 'app/ui/HeaderContainer';
import {Layout} from 'antd';
import {Content} from 'antd/es/layout/layout';
import {CreateUSerForm} from 'app/ui/CreateUserForm';

function App() {
    return (
        <div className="App">
            <Layout>
                <BrowserRouter>
                    <HeaderContainer/>
                    <Content style={{padding: '0 50px', marginTop: 64}}>
                        <Switch>
                            <Route exact path={Routes.clients} component={Users}/>
                            <Route exact path={Routes.clients + '/edit/:id'} component={EditUserComponent}/>
                            <Route exact path={Routes.createClient} component={CreateUSerForm}/>
                            <Redirect to={Routes.clients}/>
                        </Switch>
                    </Content>
                </BrowserRouter>
            </Layout>
        </div>
    );
}

export default App;
