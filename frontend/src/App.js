import React, { useEffect, useState } from 'react';
import './App.css';
import ChartComponent from './ChartComponent';

const App = () => {
  const [state, setState] = useState({
    currentX: Math.round(Math.random()),
    time: 0,
  });

  useEffect(
    () =>
      setInterval(() => {
        setState((state) => {
          const newTime = Number(state.time) + 0.5;
          const newCurrentX = Math.round(Math.random());
          return { currentX: newCurrentX, time: newTime.toFixed(1) };
        });
        fetch('127.0.0.1:8080/readTrafficLightState/1')
          .then((response) => response.json)
          .then((data) => console.log(data));
      }, 500),
    []
  );

  return (
    <div className='app'>
      <ChartComponent currentX={state.currentX} time={state.time} />
    </div>
  );
};

export default App;
