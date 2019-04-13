import React, { Component } from 'react';
import { withRouter } from 'react-router';
import axios from '../axios';

import PatientModal from './PatientModal';

class PatientTable extends Component {

    headerArray = ["ID", "Name", "Sex", "DOB", "Study"];
    state = {
        patients: [],
        filteredPatients: [],
        open: false
    };

    componentDidMount() {
        this.getAllPatients();
    }

    getAllPatients() {
        axios.get('/api/patients')
            .then(result => this.setState({patients: result.data, filteredPatients: result.data}))
            .catch(err => console.error(err.response));
    }

    createStudy(patient) {
        console.log(this.props);
        this.props.history.push({
            pathname: '/createStudy',
            patient
        });
    }

    searchByName(name) {

        if(!name) {
            return this.setState({
                filteredPatients: [...this.state.patients]
            });
        }

        let filteredData = this.state.filteredPatients.filter(item => item.name.toLowerCase().indexOf(name.toLowerCase()) === 0);
        return this.setState({
            filteredPatients: filteredData
        });
    }

    openPatientModal() {
        this.setState({open: true}); 
    }

    closeModal() {
        this.setState({open: false}); 
        this.getAllPatients();
    }

    render() {
        return (
        <div className="col">
            {this.state.open && <PatientModal closeModal={() => this.closeModal()} />}
            <div className="col-12">
                <div className="col-12 form-inline clearfix" style={{marginBottom: "20px", marginTop: "20px"}}>
                    <div className='col-4'></div>
                    <div className='col-4 form-group'>
                        <div className="input-group mb-3">
                            <div className="input-group-prepend">
                                <span className="input-group-text fas fa-search"></span>
                            </div>
                            <input type="text" value={this.state.searchValue} className="form-control" placeholder="Enter Name" onChange={e => this.searchByName(e.target.value)} />
                        </div>
                    </div>
                    <div className='col-4'>
                        <button type="button" className="btn btn-success float-right" onClick={() => this.openPatientModal()}>
                            <span className="fas fa-plus"></span> Patient
                        </button>
                    </div>
                </div>
                <div className="col-12">
                    <table className="table table-striped">
                        <thead className="table-dark">
                            <tr>
                                {this.headerArray.map(header => <th key={header}>{header}</th>)}
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.filteredPatients.map((row, index) =>
                                    <tr key={index}>
                                        <td>{row.id}</td>
                                        <td>{row.name}</td>
                                        <td>{row.sex}</td>
                                        <td>{row.dob}</td>
                                        <td>
                                            <button type='button' className='btn btn-primary' onClick={() => this.createStudy(row)}>
                                                <span className="fas fa-plus"></span> Study
                                            </button>
                                        </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                    {
                        (this.state.filteredPatients.length === 0 && <div className='d-flex justify-content-center' style={{'marginTop': '20%'}}><h2 className='text-info'>Patient Table is Empty!</h2></div>)
                    }
                </div>
            </div>
        </div>
        );
    }

}

export default withRouter(PatientTable);