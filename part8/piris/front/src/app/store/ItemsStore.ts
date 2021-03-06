import {makeAutoObservable, runInAction, toJS} from "mobx";
import {RequestService} from 'app/api/RequestService';


export class ItemsStore<T> {

    protected _items: T[] = [];
    protected _item: T = {} as T;
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

    public fetchItemById = (id: number) => {
        return RequestService.get(`${this.endpoint}/${id}`).then((response) => {
            console.log(response)
            runInAction(() => {
                this._item = response.data;
            })
        });
    }

    public createItem = (item: T): Promise<any> => {
        return RequestService.post(this.endpoint, {}, item).then(this.fetchItems);
    }


    get items(): T[] {
        return toJS(this._items);
    }

    get fetchedItem(): T {
        return toJS(this._item);
    }

    public updateItem = (param: any, data: any): Promise<any> => {
        return RequestService.put(this.endpoint + `/${param}`, {}, data).then(this.fetchItems);
    }

    public delete = (param: any): Promise<any> => {
        return RequestService.delete(this.endpoint + `/${param}`, {}).then(this.fetchItems);
    }
}