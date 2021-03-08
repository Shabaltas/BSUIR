import {Entity} from 'app/entity/Entity';
import {SimpleItem} from "../ui/form/SelectFormItem";



export interface Deposit extends SimpleItem{

    typeId: number,

    currencyId: number,

    interestOnDeposit: number,

    termInMonth: number
}