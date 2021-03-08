import {Entity} from 'app/entity/Entity';

export interface Address  extends Entity{
    country: string,
   
    city: string,
 
    street: string,
   
    buildingNumber: number,

    apartmentsNumber: number,
}