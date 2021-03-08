import React, {useContext, useEffect, useState} from 'react';
import {Button, Form, Switch,} from 'antd';
import {User} from 'app/entity/User';
import {RootStoreContext} from 'app/RootStoreContext';
import {StringFormItem} from 'app/ui/form/StringFormItem';
import {DateFormItem} from 'app/ui/form/DateFormItem';
import {observer} from 'mobx-react-lite';
import {SelectFormItem} from 'app/ui/form/SelectFormItem';
import {getDefaultValuesFromObject, getObjectFromValues} from 'app/ui/form/utils';
import moment from 'moment';

interface UserFormProps {
    user?: User,
    onSave: (user: User) => void;
    onCancel: () => void;
    onOpenDeposit?: (user: User) => void;
}

const NAME = 'name';
const SURNAME = 'surname';
const PATRONYMIC = 'second_name';
const BIRTHDAY = 'date_of_birth';

const PASSPORT_SERIES = 'passport_series';
const PASSPORT_NUMBER = 'passport_number';
const PASSPORT_ISSUER = 'issued_by';
const PASSPORT_DATE_OF_ISSUE = 'date_of_issue';
const PASSPORT_IDENTIFICATION_NUMBER = 'identification_number';

const PLACE_BIRTH = 'place_of_birth';

const RESIDENTIAL_ADDRESS = 'address_of_residence';
const RESIDENTIAL_CITY = 'city_of_residence';

const HOME_PHONE = 'home_phone';
const MOBILE_PHONE = 'mobile_phone';
const EMAIL = 'email';

const JOB_PLACE = 'place_of_work';
const JOB_POSITION = 'position';

const MARTIAL_STATUS = 'marital_status';
const DISABILITY = 'disability';
const CITIZENSHIP = 'citizenship';
const PENSIONER = 'retiree';
const MONTHLY_INCOME = 'monthly_income';
const MILITARY = 'liable_for_military';


export const UserForm = observer((props: UserFormProps) => {

    const {citiesStore, citizenshipStore, martialStatusesStore, disabilitiesStore} = useContext(RootStoreContext);

    const [hide, setHide] = useState(true);

    useEffect(() => {
        citiesStore.fetchItems();
        disabilitiesStore.fetchItems();
        martialStatusesStore.fetchItems();
        citizenshipStore.fetchItems();
    }, [citiesStore, citizenshipStore, martialStatusesStore, disabilitiesStore]);

    const onFinish = (values: any) => {
        console.log(values);
        let user = getObjectFromValues(values) as User;
        user.city_of_residence = Number(user.city_of_residence);
        user.disability = Number(user.disability);
        user.citizenship = Number(user.citizenship);
        user.marital_status = Number(user.marital_status);
        user.monthly_income = Number(user.monthly_income);
        if (!props.user) {
            props.onSave(user);
        } else {
            user.id = props.user.id;
            props.onSave(user);
        }
    };

    const tailLayout = {
        wrapperCol: {offset: 8, span: 16},
    };

    let initialValues = undefined;
    if (props.user) {
        initialValues = getDefaultValuesFromObject(props.user);
        initialValues[BIRTHDAY] = moment(initialValues[BIRTHDAY]);
        initialValues[PASSPORT_DATE_OF_ISSUE] = moment(initialValues[PASSPORT_DATE_OF_ISSUE]);
        initialValues[MONTHLY_INCOME] = initialValues[MONTHLY_INCOME] ? String(initialValues[MONTHLY_INCOME]) : '0';
    }
    return (
        <>
            <Form
                labelCol={{span: 4}}
                wrapperCol={{span: 10}}
                layout="horizontal"
                size={'large'}
                initialValues={initialValues}
                /*onValuesChange={(vf, af) => {
                console.log("VALUES");
                af = {...af, JOB_POSITION: "HOOOO"};
            }}*/
                onFinish={onFinish}
            >
                <StringFormItem name={NAME}/>
                <StringFormItem name={SURNAME}/>
                <StringFormItem name={PATRONYMIC}/>
                <DateFormItem name={BIRTHDAY} shortName={'Birthday'}/>

                <Form.Item valuePropName="checked" name={PENSIONER} label="Pensioner">
                    <Switch defaultChecked={false}/>
                </Form.Item>
                <Form.Item valuePropName="checked" name={MILITARY} label="Military">
                    <Switch/>
                </Form.Item>
                <SelectFormItem items={citizenshipStore.items} name={CITIZENSHIP} label={'Citizenship'} val="country"/>

                <StringFormItem name={PASSPORT_SERIES}
                                shortName={'Passport series'}/>
                <StringFormItem name={PLACE_BIRTH}
                                shortName={'Birth place'}/>
                <StringFormItem name={PASSPORT_NUMBER}
                                shortName={'Passport number'}/>
                <StringFormItem name={PASSPORT_ISSUER}
                                shortName={'Passport issued by'}/>
                <DateFormItem name={PASSPORT_DATE_OF_ISSUE}
                              shortName={'Passport date of issued'}/>
                <StringFormItem name={PASSPORT_IDENTIFICATION_NUMBER}
                                shortName={'Passport identification number'}/>
                <SelectFormItem items={citiesStore.items}
                                name={RESIDENTIAL_CITY} label={'Residential city'} val="name"/>
                <StringFormItem name={RESIDENTIAL_ADDRESS} shortName={'Residential address'}/>

                <StringFormItem name={MOBILE_PHONE}
                                shortName={'Mobile phone number'} isNotRequire={true}/>
                <StringFormItem name={HOME_PHONE} shortName={'Home phone number'}
                                isNotRequire={true}/>
                <StringFormItem name={EMAIL} shortName={'Email'} isNotRequire={true}/>
                <StringFormItem name={JOB_PLACE} shortName={'Work place'}
                                isNotRequire={true} props={{onBlur: ((e: { target: { value: any; }; }) => {
                                     setHide(!e.target.value);
                                })}}/>
                <StringFormItem name={JOB_POSITION} shortName={'Work position'}
                                isNotRequire={true} props={{hidden: hide}}/>

                <SelectFormItem items={martialStatusesStore.items}
                                name={MARTIAL_STATUS} label={'Martial status'} val="title"/>

                <SelectFormItem items={disabilitiesStore.items} name={DISABILITY}
                                label={'Disability'} val="stage"/>
                <StringFormItem name={MONTHLY_INCOME} shortName={'Monthly income'} isNotRequire={true}/>
                <Form.Item {...tailLayout}>
                    <Button type="primary" htmlType="submit">Save</Button>
                </Form.Item>
                {props.user && props.onOpenDeposit
                    ? <Form.Item {...tailLayout}>
                        <Button type="default" htmlType="button" onClick={() => props.onOpenDeposit!(props.user!)}>Open Deposit</Button>
                    </Form.Item>
                    : <></>}
            </Form>
        </>)
})