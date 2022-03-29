import React, { useEffect, useState } from 'react';
import './App.css';
import ChartComponent from './ChartComponent';
import { SERVER_URL } from './consts';

const App = () => {
  const [state, setState] = useState({
    1: { currentX: 0, time: 0 },
    2: { currentX: 0, time: 0 },
  });

  useEffect(
    () =>
      setInterval(async () => {
        await fetch(SERVER_URL + '/readTrafficLightState?tf_id=1')
          .then((response) => response.text())
          .then((data) => {
            setState((state) => {
              const newTime = Number(state[1].time) + 0.5;
              const newCurrentX = 1;
              return {
                ...state,
                1: { currentX: newCurrentX, time: newTime.toFixed(1) },
              };
            });
            console.log(data);
          });
      }, 500),
    []
  );

  return (
    <div className='app'>
      <ChartComponent currentX={state[1].currentX} time={state[1].time} />
    </div>
  );
};

export default App;
