import {action, computed, makeAutoObservable, observable} from "mobx";
import {Deposit} from "../entity/Deposit";
import {DepositsStore} from "./DepositsStore";

export class SelectStore {

    constructor(depStore: DepositsStore<Deposit>) {
        this._depStore = depStore;
    }

    private _depStore: DepositsStore<Deposit>;

    @observable
    private _selectedType: number|null = null;

    @observable
    private _selectedCur: number|null = null;

    @observable
    private _selectedDeposit: Deposit|null = null;

    @action
    setSelectedType(newId: number) {
        this._selectedType = newId;
        if (this._selectedCur) {
            this._selectedDeposit = null;
            this._depStore.fetchItemsByTypeAndCur(this._selectedCur, this._selectedType);
        }
    }

    @action
    setSelectedCur(newId: number) {
        this._selectedCur = newId;
        if (this._selectedType) {
            this._selectedDeposit = null;
            this._depStore.fetchItemsByTypeAndCur(this._selectedCur, this._selectedType);
        }
    }

    @action
    setSelectedDeposit(newId: number) {
        this._selectedDeposit = this._depStore.items.find(dep => dep.id == newId)!;
    }

    @computed
    get selectedType() {
        return this._selectedType;
    }

    @computed
    get selectedCur() {
        return this._selectedCur;
    }

    get selectedDeposit() {
        return this._selectedDeposit;
    }

    @computed
    get deposits() {
        return this._depStore.items;
    }
}