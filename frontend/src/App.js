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
      setInterval(async () => {
        await fetch('http://localhost:8081/readTrafficLightState?tf_id=1')
          .then((response) => response.text())
          .then((data) => {
            setState((state) => {
              const newTime = Number(state.time) + 0.5;
              const newCurrentX = data;
              return { currentX: newCurrentX, time: newTime.toFixed(1) };
            });
            console.log(data);
          });
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
