import React from 'react';
import {observer} from 'mobx-react-lite';
import {Form, Select} from 'antd';
import {getRequireRule} from 'app/ui/form/rules';

export interface SimpleItem {
    id: number,
    [key: string]: string | number
}

interface SelectFormItemProps {
    items: SimpleItem[],
    name: string | (string | number)[],
    label: string,
    val: string,
    onChange?: (event: any) => void,
    selectedValue?: any
}

export const SelectFormItem = observer(({items, label, name,
                                            val= "name", onChange, selectedValue}
                                            : SelectFormItemProps) => {
    return (
        items ? <Form.Item name={name} label={label} rules={[getRequireRule(label)]}>
            <Select onChange={onChange} value={selectedValue}>
                {
                    items.map((item) => {
                        return <Select.Option key={item['id']} value={item['id']}>{item[val]}</Select.Option>
                    })
                }
            </Select>
        </Form.Item>: null
    )
})