import {ItemsStore} from 'app/store/ItemsStore';
import {User} from 'app/entity/User';
import {endpoints} from 'app/api/endpoints';
import {SimpleItem} from "../ui/form/SelectFormItem";
import {Deposit} from "../entity/Deposit";
import {DepositsStore} from "./DepositsStore";
import {DepositContractsStore} from "./DepositsContractStore";
import {DepositContract} from "../entity/DepositContract";
import {Account} from "../entity/Account";

export class RootStore {
    private readonly _clientsStore = new ItemsStore<User>(endpoints.clients.base);
    private readonly _citiesStore = new ItemsStore<SimpleItem>(endpoints.clients.cities);
    private readonly _citizenship = new ItemsStore<SimpleItem>(endpoints.clients.citizenship);
    private readonly _martialStatuses = new ItemsStore<SimpleItem>(endpoints.clients.marital_statuses);
    private readonly _disabilities = new ItemsStore<SimpleItem>(endpoints.clients.disabilities);
    private readonly _depositTypes = new ItemsStore<SimpleItem>(endpoints.deposits.types);
    private readonly _depositContracts = new DepositContractsStore<DepositContract>(endpoints.deposits.base);
    private readonly _currencies = new ItemsStore<SimpleItem>(endpoints.deposits.currencies);
    private readonly _deposits = new DepositsStore<Deposit>(endpoints.deposits.base);
    private readonly _accounts = new ItemsStore<Account>(endpoints.deposits.accounts);

    get clientsStore() {
        return this._clientsStore;
    }

    get citiesStore() {
        return this._citiesStore;
    }

    get citizenshipStore() {
        return this._citizenship;
    }

    get martialStatusesStore() {
        return this._martialStatuses;
    }

    get disabilitiesStore() {
        return this._disabilities;
    }

    get currenciesStore() {
        return this._currencies;
    }

    get depositTypesStore() {
        return this._depositTypes;
    }

    get depositsStore() {
        return this._deposits;
    }

    get depositContractsStore() {
        return this._depositContracts;
    }

    get accountsStore() {
        return this._accounts;
    }
}