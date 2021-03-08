import {makeAutoObservable, runInAction, toJS} from "mobx";
import {RequestService} from 'app/api/RequestService';
import {ItemsStore} from "./ItemsStore";
import {AxiosResponse} from "axios";


export class DepositContractsStore<T>{

    protected _items: T[] = [];
    protected readonly endpoint: string = '';

    constructor(endpoint: string) {
        makeAutoObservable(this);
        this.endpoint = endpoint;
    }

    public fetchItems = () => {
        RequestService.get(this.endpoint).then((response) => {
            runInAction(() => {
                console.log(response);
                this._items = response.data;
            })
        });
    }

    public createItem = (item: T): Promise<any> => {
        return RequestService.post(this.endpoint, {}, item).then(this.fetchItems);
    }

    get items(): T[] {
        return toJS(this._items);
    }

}