<!--
 * @FilePath: \nertc-web-template\src\pages\Debug\form\test.vue
 * @Author: abc-0886kAX-code
 * @Date: 2023-02-03 17:54:43
 * @LastEditors: abc-0886kAX-code
 * @LastEditTime: 2023-02-03 17:55:00
 * @Description:
-->
<script setup>
import { FormInput, defineFormInput, FormSelect, defineFormSelect, FormDatePicker, defineFormDatePicker, FormCheckbox, defineFormCheckbox } from './form';
const { setupInputSchema, value } = defineFormInput('input');
const { setupSelectSchema, value: selectValue, target } = defineFormSelect('select');
const { setupDatePickerSchema, value: datePickerValue } = defineFormDatePicker('DatePicker');
const { setupCheckboxSchema, value: checkboxValue } = defineFormCheckbox('checkbox');
setupSelectSchema({
    content: '123',
    options: [
        {
            'ceshi1': '123',
            'ceshi2': '标题',
            'other': 'ok'
        },
        {
            'ceshi1': '456',
            'ceshi2': '标题1',
            'other': 'no'
        }
    ],
    usevalue: 'ceshi1',
    uselabel: 'ceshi2'
})
setupInputSchema({
    content: '测试'
})
setupDatePickerSchema({
    content: ''
})
setupCheckboxSchema({
    content: false
})
</script>

<template>
    <div class="debug">
        <FormInput sign="input"></FormInput>
        {{ value }}
        <FormSelect sign="select"></FormSelect>
        {{ selectValue }}-{{ target }}
        <FormDatePicker sign="DatePicker"></FormDatePicker>
        {{ datePickerValue }}
        <FormCheckbox sign="checkbox"></FormCheckbox>
        {{ checkboxValue }}
    </div>
</template>

<style scoped lang='scss'>
.debug {
    position: absolute;
    top: 35%;
    left: 35%;
    width: 600px;
    height: 300px;
    background: antiquewhite;
    box-shadow: 5px 5px 10px #ccc;
    display: flex;
    flex-direction: column;
}
</style>
