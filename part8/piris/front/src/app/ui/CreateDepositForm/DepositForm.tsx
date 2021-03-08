import React, {useContext, useEffect, useState} from 'react';
import {Button, Form, Switch,} from 'antd';
import {RootStoreContext} from 'app/RootStoreContext';
import {StringFormItem} from 'app/ui/form/StringFormItem';
import {DateFormItem} from 'app/ui/form/DateFormItem';
import {observer} from 'mobx-react-lite';
import {SelectFormItem} from 'app/ui/form/SelectFormItem';
import {getObjectFromValues} from 'app/ui/form/utils';
import {Deposit} from "../../entity/Deposit";
import {SelectStore} from "../../store/SelectStore";
import moment from "moment";
import {DepositContract} from "../../entity/DepositContract";
import {User} from "../../entity/User";

const DEPOSIT_TYPE = 'depositType';
const CLIENT_NAME = 'Client Name';
const CLIENT_SURNAME = 'Client Surname';
const CLIENT_PATRONYMIC = 'Client Patronymic';
const CURRENCY = 'currency';
const DEPOSIT = 'deposit';
const START_DATE = 'startDate';
const END_DATE = 'endDate';
const DEPOSIT_AMOUNT = 'depositAmount';
const INTEREST_ON_DEPOSIT = 'interestOnDeposit';
const TERM_IN_MONTH = 'termInMonth';
const CLIENT_ID = 'clientId';
const CAPITALIZATION = 'capitalization';

interface DepositFormProps {
    user?: User,
    onSave: (depositContract: DepositContract) => void
}

export const DepositForm = observer((props: DepositFormProps) => {

    const {depositTypesStore, currenciesStore, clientsStore, depositsStore} = useContext(RootStoreContext);
    const [selectsStore] = useState(() => new SelectStore(depositsStore));
    const [form] = Form.useForm();

    useEffect(() => {
        depositTypesStore.fetchItems();
        currenciesStore.fetchItems();
        clientsStore.fetchItems();
    }, [depositTypesStore, currenciesStore, clientsStore, depositsStore]);

    const onFinish = (values: any) => {
        console.log(values);
        let deposit = getObjectFromValues(values) as DepositContract;
        deposit.deposit = Number(deposit.deposit);
        deposit.depositType = Number(deposit.depositType);
        deposit.currency = Number(deposit.currency);
        deposit.clientId = Number(deposit.clientId);
        deposit.depositAmount = Number(deposit.depositAmount);
        deposit.termInMonth = Number(deposit.termInMonth);
        deposit.interestOnDeposit = Number(deposit.interestOnDeposit);
        props.onSave(deposit);
    };

    const tailLayout = {
        wrapperCol: {offset: 8, span: 16},
    };

    let initialValues: any = {};
    if (props.user) {
        initialValues[CLIENT_ID] = `${props.user.id}`;
        initialValues[CLIENT_NAME] = props.user.name;
        initialValues[CLIENT_SURNAME] = props.user.surname;
        initialValues[CLIENT_PATRONYMIC] = props.user.second_name;
    }
    initialValues[START_DATE] = moment();
    return (
        <>
            <Form
                labelCol={{span: 4}}
                wrapperCol={{span: 10}}
                layout="horizontal"
                size={'large'}
                onFinish={onFinish}
                initialValues={initialValues}
                form={form}
                onFinishFailed={v => console.log("FAILED " + JSON.stringify(v))}
            >
                <StringFormItem name={CLIENT_ID} props={{hidden: true}}/>
                <StringFormItem name={CLIENT_NAME} props={{disabled: true}}/>
                <StringFormItem name={CLIENT_SURNAME} props={{disabled: true}}/>
                <StringFormItem name={CLIENT_PATRONYMIC} props={{disabled: true}}/>

                <SelectFormItem items={currenciesStore.items} name={CURRENCY} label={'Currency'} val="currency"
                                selectedValue={selectsStore.selectedCur}
                                onChange={e => selectsStore.setSelectedCur(e)}/>
                <SelectFormItem items={depositTypesStore.items} name={DEPOSIT_TYPE} label={'Deposit Type'} val="type"
                                selectedValue={selectsStore.selectedType}
                                onChange={e => selectsStore.setSelectedType(e)}/>

                <SelectFormItem items={selectsStore.deposits} name={DEPOSIT} label={'Deposit'} val="title"
                                selectedValue={selectsStore.selectedDeposit?.id}
                                onChange={e => {
                                    selectsStore.setSelectedDeposit(e);
                                    let fv: any = {};
                                    let tm = selectsStore.selectedDeposit?.termInMonth;
                                    fv[TERM_IN_MONTH] = `${tm}`;
                                    fv[INTEREST_ON_DEPOSIT] = `${selectsStore.selectedDeposit?.interestOnDeposit}`;
                                    fv[END_DATE] = moment(form.getFieldValue(START_DATE)).add(tm,'M');
                                    form.setFieldsValue(fv);
                                }}/>
                <StringFormItem name={TERM_IN_MONTH} isNotRequire={true}
                                props={{disabled: true}}/>
                <StringFormItem name={INTEREST_ON_DEPOSIT} isNotRequire={true}
                                props={{disabled: true}}/>

                <DateFormItem name={START_DATE} shortName={'Start date'}/>

                <DateFormItem name={END_DATE} shortName={'End date'} isNotRequire={true}
                              props={{disabled: true}}/>

                <Form.Item valuePropName="checked" name={CAPITALIZATION} label="Capitalization">
                    <Switch defaultChecked={false}/>
                </Form.Item>

                <StringFormItem name={DEPOSIT_AMOUNT} props={{type: 'number'}}/>

                <Form.Item {...tailLayout}>
                    <Button type="primary" htmlType="submit">Save</Button>
                </Form.Item>
            </Form>
        </>)
});