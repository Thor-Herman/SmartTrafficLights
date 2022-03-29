import React, { useEffect, useState } from 'react';
import './App.css';
import ChartComponent from './ChartComponent';

const App = () => {
  const [currentX, setCurrentX] = useState(Math.round(Math.random()));
  const [time, setCurrentTime] = useState(0);

  useEffect(
    () =>
      setInterval(() => {
        setCurrentX(Math.round(Math.random()));
        setCurrentTime((t) => {
          const newTime = Number(t) + 0.1;
          return newTime.toFixed(1);
        });
      }, 1000),
    []
  );

  return (
    <div className='app'>
      <ChartComponent currentX={currentX} time={time} />
    </div>
  );
};

export default App;
