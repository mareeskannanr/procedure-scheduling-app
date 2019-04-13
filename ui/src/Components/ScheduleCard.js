import React from 'react';

const ScheduleCard = props => (
    <div className="col card">
        <div className="card-header">
            <h3><span className='fas fa-stethoscope'></span> Schedule Details</h3>
        </div>
        <div className="card-body">
            <div className='form-row'>
                <div className='col'>
                    <label>Docotor<sup className='text-danger'>*</sup></label>
                    <select value={props.schedule.doctorId} className="form-control" onChange={e => props.onChange('doctorId', e.target.value)} disabled={props.disable}>
                        <option value=''></option>
                        {props.doctors.map(item => <option key={item.id} value={item.id}>{item.name}</option>)}
                    </select>
                </div>
                <div className='col'>
                    <label>Room<sup className='text-danger'>*</sup></label>
                    <select value={props.schedule.roomId} className="form-control" onChange={e => props.onChange('roomId', e.target.value)} disabled={props.disable}>
                        <option value={''}></option>
                        {props.rooms.map(item => <option key={item.id} value={item.id}>{item.name}</option>)}
                    </select>
                </div>
                <div className='col'></div>
                <div className='col'></div>
            </div>
        </div> 
    </div>
);

export default ScheduleCard;