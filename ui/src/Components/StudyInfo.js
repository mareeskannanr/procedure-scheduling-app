import React, { Component } from 'react';
import axios from '../axios';
import PatientCard from './PatientCard';
import ScheduleCard from './ScheduleCard';
import StudyCard from './StudyCard';
import MessageModal from './MessageModal';

export default class StudyInfo extends Component {

    state = {
        rooms: [],
        doctors: [],
        schedule: {
            id: '',
            patientId: '',
            doctorId: '',
            roomId: '',
            plannedStartTime: null,
            estimatedEndTime: null,
            description: '',
            status: ''
        },
        patient: {},
        showMessage: false,
        message: '',
        disablePlan: true
    };

    constructor(props) {
        super(props);

        this.getAllRooms();
        this.getAllDoctors();

        if(this.props.match.path === '/study/:id') {
            this.getScheduleById(this.props.match.params.id);
        } else {
            let schedule = {...this.state.schedule};
            schedule.patientId = this.props.location.patient.id;
            this.state.schedule = schedule;
            this.state.patient = this.props.location.patient;
        }
    }

    getScheduleById(id) {
        axios.get(`/api/schedule/${id}`)
            .then(result => {
                let study = result.data.study;
                let schedule = {...this.state.schedule};
                schedule.id = result.data.id;
                schedule.patientId = study.patient.id;
                schedule.doctorId = result.data.doctor.id;
                schedule.roomId = result.data.room.id;
                schedule.studyId = study.id;
                schedule.description = study.description;
                schedule.status = study.status;

                if(study.plannedStartTime) {
                    console.log(study.plannedStartTime);
                   let date = new Date(study.plannedStartTime);
                   date = date.setTime(date.getTime() + (5.5 * 60 * 60 * 1000));
                   schedule.plannedStartTime = new Date(date);
                   console.log(study.plannedStartTime);
                }
        
                if(study.estimatedEndTime) {
                    let date = new Date(study.estimatedEndTime);
                    date = date.setTime(date.getTime() + (5.5 * 60 * 60 * 1000));
                    schedule.estimatedEndTime = new Date(date);
                }

                this.setState({
                    patient: study.patient, schedule
                });
            })
            .catch(err => console.error(err.response));
    }
    
    getAllRooms() {
        axios.get('/api/rooms')
            .then(result => this.setState({rooms: result.data}))
            .catch(err => console.error(err.response));
    }

    getAllDoctors() {
        axios.get('/api/doctors')
            .then(result => this.setState({doctors: result.data}))
            .catch(err => console.error(err.response));
    }

    updateStatus(status) {
        axios.post('/api/status', {studyId: this.state.schedule.studyId, status})
            .then(() => this.setState({showMessage : true, message: 'status'}))
            .catch(err => console.error(err.response));
    }

    saveSchedule() {
        let schedule = {...this.state.schedule};

        axios.post('/api/schedule', schedule)
            .then(() => this.setState({showMessage : true}))
            .catch(err => console.error(err.response));
    }

    onChange(key, value) {
        let schedule = {...this.state.schedule};
        let disablePlan = true;
        schedule[key] = value;

        disablePlan = !(schedule.description && schedule.doctorId && schedule.roomId && schedule.plannedStartTime);

        if(!disablePlan) {
            disablePlan = !disablePlan && schedule.estimatedEndTime && (schedule.plannedStartTime.getTime() >  schedule.estimatedEndTime.getTime());
        }
        
        this.setState({schedule, disablePlan});
    }

    closeModal() {
        this.props.history.push({
            pathname: '/studies'
        });
        
        this.setState({
            showMessage: false,
            message: ''
        });
    }

    render() {
        return (<div className='col'>
            {this.state.showMessage && <MessageModal type={this.state.message} closeModal={() => this.closeModal()} />}
            <div className='col mt-15'>
                { this.state.schedule.id && <div className='card-header'>
                    <b>Schedule / <span className='badge badge-secondary'>{this.state.schedule.id}</span> / <span className='badge badge-primary'>{this.state.schedule.status}</span></b>
                </div> }
            </div>
            <br />
            <p className='text-center'>Fields marked with (<span className='text-danger'>*</span>) are required</p>
            <div className='col mt-15'>
                <PatientCard patient={this.state.patient} />
            </div>
            <div className='col mt-15'>
                <StudyCard schedule={this.state.schedule} onChange={(key, value) => this.onChange(key, value)} disable={this.state.schedule.id > 0} />
            </div>
            <div className='col mt-15'>
                <ScheduleCard rooms={this.state.rooms} doctors={this.state.doctors} 
                    schedule={this.state.schedule} onChange={(key, value) => this.onChange(key, value)} disable={this.state.schedule.id > 0} />
            </div>
            <div className="col card mt-15">
                <div className="card-header">
                    <p className='text-center'>
                    {
                        ['', 'Resolved'].indexOf(this.state.schedule.status) >= 0 && <button type='button' className='btn btn-success' style={{'marginRight': '10px'}} onClick={() => this.saveSchedule()} disabled={this.state.disablePlan}>
                            {this.state.schedule.status ? <span><span className='fas fa-save'></span> Save</span> : <span><span className='fas fa-check'></span> Plan</span>}
                        </button>
                    }
                    {
                        this.state.schedule.status === 'Planned' && 
                        <button type='button' className='btn btn-info' style={{'marginRight': '10px'}} onClick={e => this.updateStatus('In Progress')}>
                            <span className='fas fa-tasks'></span> In Progress
                        </button>
                    }
                    {
                        ['Planned', 'In Progress'].indexOf(this.state.schedule.status) >= 0 &&
                        <button type='button' className='btn btn-primary' style={{'marginRight': '10px'}} onClick={e => this.updateStatus('Finished')}>
                            <span className='fas fa-thumbs-up'></span> Finish
                        </button>
                    }
                    <button type='button' className='btn btn-danger' onClick={() => this.props.history.goBack()}>
                        <span className='fas fa-times'></span> Close
                    </button>
                    </p>
                </div>
            </div>
        </div>);
    }

}