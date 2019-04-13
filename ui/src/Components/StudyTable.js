import React, { Component } from 'react';
import { withRouter } from 'react-router';
import axios from '../axios';

class StudyTable extends Component {

    headerArray = ["ID", "Patient Name", "Room Name", "Doctor Name", "Status"];
    status = ['Planned', 'In Progress', 'Finished'];
    state = {
        schedules: [],
        filteredSchedules: [],
        search: {
            patient: '',
            doctor: '',
            status: ''
        }
    };

    componentDidMount() {
        this.getAllSchedules();
    }

    getAllSchedules() {
        axios.get('/api/schedules')
            .then(result => this.setState({schedules: result.data, filteredSchedules: result.data}))
            .catch(err => console.error(err.response));
    }

    gotoStudy(id) {
        this.props.history.push({
            pathname: `/study/${id}`
        });
    }

    search(key, value) {
        let search = {...this.state.search};
        search[key] = value || '';

        console.log(key, value);
        if(!search.patient && !search.doctor && !search.status) {
            return this.setState({
                filteredSchedules: [...this.state.schedules],
                search
            });
        }

        let filteredSchedules = this.state.schedules.filter(item => {

            let patientFlag = true;
            if(search.patient && item[1].toLowerCase().indexOf(search.patient.toLowerCase()) !== 0) {
                patientFlag = false;
            }

            let doctorFlag = true;
            if(search.doctor && item[3].toLowerCase().indexOf(search.doctor.toLowerCase()) !== 0) {
                doctorFlag = false;
            }

            let statusFlag = true;
    
            if(search.status && this.statusMap(item[4]).toLowerCase().indexOf(search.status.toLowerCase()) !== 0) {
                statusFlag = false;
            }

            return patientFlag && doctorFlag && statusFlag;

        });

        return this.setState({filteredSchedules, search});
    }

    statusMap(status) {
        let result = '';
        switch(status) {
            case 'PLANNED': {
                result = 'Planned';
                break;
            }
            case 'INPROGRESS': {
                result = 'In Progress';
                break;
            }
            case 'FINISHED': {
                result = 'Finished';
                break;
            }
            default: {
                result = ''; 
            }
        }

        return result;
    }

    render() {
        return (
            <div className="col-12">
                <div className="form-row" style={{'margin': '20px 20px' }}>
                    <div className="col">
                        <input type="text" value={this.state.search.patient} className="form-control" placeholder="Search By Patient Name" onChange={e => this.search('patient', e.target.value)} />
                    </div>
                    <div className="col">
                        <input type="text" value={this.state.search.doctor} className="form-control" placeholder="Search By Doctor Name" onChange={e => this.search('doctor', e.target.value)} />
                    </div>
                    <div className="col">
                        <select value={this.state.search.status} className="form-control" onChange={e => this.search('status', e.target.value)}>
                            <option value="" defaultValue>Search By Status</option>
                            {this.status.map(item => <option key={item} value={item}>{item}</option>)}
                        </select>
                    </div>
                </div>
                <p className='text-center text-primary'>Click Row To View Schedule Details</p>
                <div className="col-12">
                    <table className="table table-striped">
                        <thead className="table-dark">
                            <tr>
                                {this.headerArray.map(header => <th key={header}>{header}</th>)}
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.filteredSchedules.map((row, index) =>
                                    <tr key={index} onClick={() => this.gotoStudy(row[0])}>
                                        <td>{row[0] || ''}</td>
                                        <td>{row[1] || ''}</td>
                                        <td>{row[2] || ''}</td>
                                        <td>{row[3] || ''}</td>
                                        <td>{this.statusMap(row[4] || '')}</td>
                                    </tr>
                                )    
                            }
                        </tbody>
                    </table>
                    {
                        (this.state.filteredSchedules.length === 0 && <div className='d-flex justify-content-center' style={{'marginTop': '20%'}}><h2 className='text-info'>Schedule Table is Empty!</h2></div>)
                    }
                </div>
            </div>
        );
    }

}

export default withRouter(StudyTable);