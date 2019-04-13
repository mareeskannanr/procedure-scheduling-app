import React from 'react';
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";

const StudyCard = props => (
    <div className="card">
        <div className="card-header">
            <h3><span className='fas fa-notes-medical'></span> Study Details</h3>
        </div>
        <div className="card-body">
            <div className='form-row'>
                <div className='col'>
                    <label>Description<sup className='text-danger'>*</sup></label>
                    <textarea className='form-control' value={props.schedule.description} onChange={e => props.onChange('description', e.target.value)} required  disabled={props.disable}></textarea>
                </div>
                <div className='col'>
                    <label>Planned Start Time<sup className='text-danger'>*</sup></label><br />
                    <DatePicker selected={props.schedule.plannedStartTime} onChange={time => props.onChange('plannedStartTime', time)} showTimeSelect className="form-control" dateFormat="Pp" required  disabled={props.disable} />
                </div>
                <div className='col'>
                    <label>Estimated End Time</label><br />
                    <DatePicker selected={props.schedule.estimatedEndTime} onChange={time => props.onChange('estimatedEndTime', time)} showTimeSelect className="form-control" dateFormat="Pp" required  disabled={props.disable} />
                    {   props.schedule.plannedStartTime && props.schedule.estimatedEndTime && 
                        (props.schedule.plannedStartTime.getTime() >  props.schedule.estimatedEndTime.getTime()) && 
                        <small className='text-danger'><br />Must be greater than Planned Start Time</small>
                    }
                </div>
                <div className='col'></div>
            </div>
        </div> 
    </div>
);

export default StudyCard;