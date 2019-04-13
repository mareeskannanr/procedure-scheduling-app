import React, { Component } from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import DatePicker from "react-datepicker";

import axios from '../axios';
 
import "react-datepicker/dist/react-datepicker.css";

export default class PatientModal extends Component {

    sex = ['', 'Male', 'Female', 'Other'];
    state = {
        patient : {
            name: '',
            sex: '',
            dob: null
        },
        result: null
    };

    savePatient() {
        let patient = {...this.state.patient};
        if(patient.dob) {
            let dob = new Date(patient.dob);
            let month = dob.getMonth() + 1;
            let date = dob.getDate();
            
            month = (month < 10 ? '0' : '') + month;
            date = (date < 10 ? '0' : '') + date;
            patient.dob = [dob.getFullYear(), month, date].join('-');
        }

        axios.post('/api/patient', patient)
            .then(result => {
                console.log(result.data);
                this.setState({
                    result: `Patient Added Successfully!`
                });
            })
            .catch(error => {
                console.error(error.response);
                this.setState({
                    result: 'Sorry, something went wrong! Try Again!'
                });
            });
    }

    onChange(prop, value) {
        let patient = this.state.patient;

        patient[prop] = value;
        this.setState({patient});
    }

    render() {
        return (
            <Modal isOpen={true}>
                <ModalHeader><span className="fas fa-user"></span> Patient</ModalHeader>
                <ModalBody>
                    <div className="col">
                        {(this.state.result == null) ? (
                            <div className="col-8 offset-2">
                                <p className='text-center'>Fields marked with (<span className='text-danger'>*</span>) are required</p>
                                <div className='row form-group'>
                                    <div className='col-2'>
                                        Name<sup className='text-danger'>*</sup>
                                    </div>
                                    <div className='col-9'>
                                        <input type="text" className="form-control"  onChange={e => this.onChange('name', e.target.value)} />
                                    </div>
                                </div>
                                <div className='row form-group'>
                                    <div className='col-2'>
                                        Sex
                                    </div>
                                    <div className='col-9'>
                                        <select value={this.state.patient.sex} className="form-control" onChange={e => this.onChange('sex', e.target.value)} >
                                            {this.sex.map(item => <option key={item} value={item}>{item}</option>)}
                                        </select>
                                    </div>
                                </div>
                                <div className='row form-group'>
                                    <div className='col-2'>
                                        DOB
                                    </div>
                                    <div className='col-9'>
                                    <DatePicker selected={this.state.patient.dob} onChange={dob => this.onChange('dob', dob)} dateFormat="dd/MM/yyyy" className="form-control" />
                                    </div>
                                </div>
                            </div>) : (
                                <h5 className={this.state.result.includes('Patient') ? 'text-info' : 'text-danger'}>
                                    {this.state.result}
                                </h5>
                            )}
                    </div>
                </ModalBody>
                <ModalFooter>
                    <div className="col-12 text-center">
                        {!this.state.result && <button type="button" className="btn btn-success" onClick={() => this.savePatient()} disabled={!this.state.patient.name}>
                            <span className="fas fa-save"></span> Save
                        </button>}    
                        &nbsp;&nbsp;&nbsp;
                        <button type="button" className="btn btn-danger" onClick={() => this.props.closeModal()}>
                            <span className="fas fa-times"></span> Close
                        </button>
                    </div>
                </ModalFooter>
            </Modal>
        );
    }

}