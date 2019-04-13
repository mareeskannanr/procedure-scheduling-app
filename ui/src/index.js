import React from 'react';
import ReactDOM from 'react-dom';
import { Route, Link, HashRouter as Router } from 'react-router-dom';

import './index.css';

import App from './App';
import PatientTable from './Components/PatientTable';
import StudyInfo from './Components/StudyInfo';
import StudyTable from './Components/StudyTable';

const routing = (
        <Router>
        <div className='row'>
            <div className='col-12'>
                <nav className="navbar navbar-expand-sm bg-dark navbar-dark" style={{'marginTop': '20px'}}>
                    <ul className="navbar-nav">
                        <li className="navbar-brand">
                            <img src='images/caresyntax.png' alt='CareSyntax' style={{width: '150px'}} />
                        </li>
                        <li className="navbar-brand">
                            <h3 className="d-none d-sm-block">Study Scheduling Application</h3>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/patients">
                                <h4><span className="fas fa-user"></span> Patients</h4>
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/studies">
                                <h4><span className='fas fa-notes-medical'></span> Schedules</h4>
                            </Link>
                        </li>
                    </ul>
                </nav>
            </div>        
            <div className='col-12'>
                <Route exact path="/" component={App} />
                <Route exact path="/patients" component={PatientTable} />
                <Route exact path="/studies" component={StudyTable} />
                <Route exact path="/createStudy" component={StudyInfo} />
                <Route exact path="/study/:id" component={StudyInfo} />
            </div>
        </div>    
        </Router>
);

ReactDOM.render(routing, document.getElementById('root'));