import {makeAutoObservable, runInAction, toJS} from "mobx";
import {RequestService} from 'app/api/RequestService';
import {ItemsStore} from "./ItemsStore";
import {AxiosResponse} from "axios";


export class DepositsStore<T>{

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

    public closeDay = (): Promise<any> => {
        return RequestService.post(`${this.endpoint}/close_day`).then(this.fetchItems);
    }


    get items(): T[] {
        return toJS(this._items);
    }

    public fetchItemsByTypeAndCur = (currencyId: number, typeId: number, callback?: (resp: AxiosResponse) => void) => {
        RequestService.get(`${this.endpoint}?currency_id=${currencyId}&type_id=${typeId}`)
            .then((response) => {
            runInAction(() => {
                console.log(response);
                this._items = response.data;
            })
        });
    }

}