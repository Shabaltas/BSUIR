import {createContext} from 'react';
import {RootStore} from 'app/store/RootStore';

export const defaultRootStore = new RootStore();

export const RootStoreContext = createContext(defaultRootStore);