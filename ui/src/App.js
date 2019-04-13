import React, { Component } from 'react';
import './App.css';

import PatientTable from  './Components/PatientTable';

class App extends Component {
  render() {
    return (
      <div className='row'>
        <PatientTable />
      </div>
    );
  }
}

export default App;
