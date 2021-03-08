import {ItemsStore} from 'app/store/ItemsStore';
import {User} from 'app/entity/User';
import {endpoints} from 'app/api/endpoints';
import {SimpleItem} from "../ui/form/SelectFormItem";

export class RootStore {
    private readonly _clientsStore = new ItemsStore<User>(endpoints.clients);
    private readonly _citiesStore = new ItemsStore<SimpleItem>(endpoints.cities);
    private readonly _citizenship = new ItemsStore<SimpleItem>(endpoints.citizenship);
    private readonly _martialStatuses = new ItemsStore<SimpleItem>(endpoints.marital_statuses);
    private readonly _disabilities = new ItemsStore<SimpleItem>(endpoints.disabilities);

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
}