import React from 'react';
import {Routes} from 'app/constants/Routes';
import {BrowserRouter, Redirect, Route, Switch} from 'react-router-dom';

import {Users} from 'app/ui/Users';
import {EditUserComponent} from 'app/ui/EditUserComponent';
import {HeaderContainer} from 'app/ui/HeaderContainer';
import {Layout} from 'antd';
import {Content} from 'antd/es/layout/layout';
import {CreateUSerForm} from 'app/ui/CreateUserForm';
import {CreateDepositForm} from "./ui/CreateDepositForm/CreateDepositForm";
import {Accounts} from "./ui/AllAccounts/Accounts";
import {AccountForm} from "./ui/AllAccounts/Account";

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
                            <Route exact path={Routes.deposits + '/new/:clientId'} component={CreateDepositForm}/>
                            <Route exact path={Routes.depositAccounts} component={Accounts}/>
                            <Route exact path={Routes.depositAccounts + '/:accountId'} component={AccountForm}/>
                            {/*<Redirect to={Routes.clients}/>*/}
                        </Switch>
                    </Content>
                </BrowserRouter>
            </Layout>
        </div>
    );
}

export default App;
