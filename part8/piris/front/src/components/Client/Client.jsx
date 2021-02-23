import React from "react";
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import TextField from '@material-ui/core/TextField';
import DateFnsUtils from '@date-io/date-fns';
import {DatePicker, MuiPickersUtilsProvider} from '@material-ui/pickers';
import Preloader from "../Preloader/Preloader";
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import s from "./Client.module.css";
import Select from '@material-ui/core/Select';
import FormControl from '@material-ui/core/FormControl';

const Client = (props) => {
    const [selectedDate, setSelectedDate] = React.useState(new Date());
    const [city, setCity] = React.useState('');
    const [errors, setErrors] = React.useState({});
    const [maritalStatus, setMaritalStatus] = React.useState('');
    const [citizenship, setCitizenship] = React.useState('');
    const [disability, setDisability] = React.useState('');

    const handleTextFieldChange = (event) => {
        let val = event.target.value;
        let new_err = {...errors};
        new_err[event.target.id] = true;
        setErrors(new_err);
    };

    const handleDateChange = (date) => {
        setSelectedDate(date);
    };

    const handleCityChange = (event) => {
        setCity(event.target.value);
    };

    const handleMaritalStatusChange = (event) => {
        setMaritalStatus(event.target.value);
    };

    const handleCitizenshipChange = (event) => {
        setCitizenship(event.target.value);
    };
    const handleDisabilityChange = (event) => {
        setDisability(event.target.value);
    };

    return props.isFetching
        ? <Preloader/>
        : <form>
            <div className={s.line}>
                <TextField required id="standard-basic" label="Surname"/>
                <TextField required id="standard-basic" label="Name"/>
                <TextField required id="standard-basic" label="Patronymic"/>
            </div>
            <div className={s.line}>
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                    <DatePicker
                        disableFuture
                        variant="inline"
                        openTo="year"
                        format="dd/MM/yyyy"
                        label="Date of birth"
                        value={selectedDate}
                        onChange={handleDateChange}
                    />
                </MuiPickersUtilsProvider>
            </div>
            <div className={s.line}>
                <TextField required id="passport_series" label="Passport Series"/>
                <TextField required id="passport_number" label="Passport Number"/>
            </div>
            <div className={s.line}>
                <TextField required id="issued_by" label="Issued By"/>
                <MuiPickersUtilsProvider utils={DateFnsUtils}>
                    <DatePicker
                        id="date_of_issue"
                        disableFuture
                        variant="inline"
                        openTo="year"
                        format="dd/MM/yyyy"
                        label="Date of issue"
                        value={selectedDate}
                        onChange={handleDateChange}
                    />
                </MuiPickersUtilsProvider>
            </div>
            <div className={s.line}>
                <TextField required id="identification_number" label="Identification Number"/>
            </div>
            <div className={s.line}>
                <TextField required id="place_of_birth" label="Place of Birth"/>
            </div>
            <div className={s.line}>
                <FormControl style={{width: "30%"}} >
                <InputLabel id="city_label">City of Residence</InputLabel>
                <Select
                    labelId="city_label"
                    id="city"
                    value={city}
                    onChange={handleCityChange}
                >
                    <MenuItem value={10}>Ten</MenuItem>
                    <MenuItem value={20}>Twenty</MenuItem>
                    <MenuItem value={30}>Thirty</MenuItem>
                </Select>
                </FormControl>
            </div>
            <div className={s.line}>
            <TextField required id="address_of_residence" label="Address of Residence" />
            </div>
            <div className={s.line}>
                <TextField required error={errors['home_phone']} id="home_phone" label="Home Phone" onBlur={handleTextFieldChange}/>
                <TextField required error={errors['mobile_phone']} id="mobile_phone" label="Mobile Phone"/>
            </div>
            <div className={s.line}>
                <TextField type="email" error={errors['email']} required id="email" label="Email"/>
            </div>
            <div className={s.line}>
                <TextField required id="placeOfWork" label="Place of Work"/>
                <TextField required id="position" label="Position"/>
                <TextField required id="monthly_income" label="Monthly Income"/>
            </div>
            <div className={s.line}>
                <FormControl style={{width: "30%"}} >
                    <InputLabel id="maritalStatus_label">Marital Status</InputLabel>
                    <Select
                        labelId="maritalStatus_label"
                        id="marital_status"
                        value={maritalStatus}
                        onChange={handleMaritalStatusChange}
                    >
                        <MenuItem value={10}>Ten</MenuItem>
                        <MenuItem value={20}>Twenty</MenuItem>
                        <MenuItem value={30}>Thirty</MenuItem>
                    </Select>
                </FormControl>
                <FormControl style={{width: "30%"}} >
                    <InputLabel id="citizenship_label">Citizenship</InputLabel>
                    <Select
                        labelId="citizenship_label"
                        id="citizenship"
                        value={citizenship}
                        onChange={handleCitizenshipChange}
                    >
                        <MenuItem value={10}>Ten</MenuItem>
                        <MenuItem value={20}>Twenty</MenuItem>
                        <MenuItem value={30}>Thirty</MenuItem>
                    </Select>
                </FormControl>
                <FormControl style={{width: "30%"}} >
                    <InputLabel id="disability_label">Disability</InputLabel>
                    <Select
                        labelId="disability_label"
                        id="disability"
                        value={disability}
                        onChange={handleDisabilityChange}
                    >
                        <MenuItem value={10}>Ten</MenuItem>
                        <MenuItem value={20}>Twenty</MenuItem>
                        <MenuItem value={30}>Thirty</MenuItem>
                    </Select>
                </FormControl>
            </div>
            <div className={s.line}>
                <FormControlLabel
                    value="retiree"
                    control={<Switch color="primary"/>}
                    label="Retiree"
                    labelPlacement="start"
                />
            </div>
            <div className={s.line}>
            <FormControlLabel
                    value="liable_for_military"
                    control={<Switch color="primary"/>}
                    label="Liable for Military"
                    labelPlacement="start"
            />
            </div>
        </form>
};

export default Client;
