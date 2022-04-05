import React from 'react';
import './App.css';
import LiveChartComponent from './LiveChartComponent';
import PieChart from './PieChart';

const App = () => {
  return (
    <div className='app'>
      <LiveChartComponent />
      <PieChart />
    </div>
  );
};

export default App;
