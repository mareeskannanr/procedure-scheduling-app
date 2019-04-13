import React from 'react';

const PatientCard = props => (
    <div className="col-12 card">
        <div className="card-header">
            <h3><span className='fas fa-user'></span> Patient Details</h3>
        </div>
        <div className="card-body">
            <div className='form-row clearfix'>
                {
                    props.patient.id && <div className='col'>
                        <label>ID</label>
                        <input className='form-control' value={props.patient.id} disabled />
                    </div>
                }
                {
                    props.patient.name && <div className='col'>
                        <label>Name</label>
                        <input className='form-control' value={props.patient.name} disabled />
                    </div>
                }
                {
                    props.patient.sex && <div className='col'>
                        <label>Sex</label>
                        <input className='form-control' value={props.patient.sex} disabled />
                    </div>
                }
                {
                    props.patient.dob && <div className='col'>
                        <label>DOB</label>
                        <input className='form-control' value={props.patient.dob} disabled />
                    </div>
                }
            </div>
        </div> 
    </div>
);

export default PatientCard;