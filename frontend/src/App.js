import React, { useEffect, useState } from 'react';
import './App.css';
import ChartComponent from './ChartComponent';
import { SERVER_URL } from './consts';

const App = () => {
  const [state, setState] = useState({
    1: { id: 1, currentX: 0, time: 0 },
    2: { id: 2, currentX: 0, time: 0 },
  });

  const fetchResponse = async (id) => {
    try {
      const response = await fetch(
        SERVER_URL + '/readTrafficLightState?tf_id=' + id
      );
      return response;
    } catch (e) {
      return 0;
    }
  };

  const fetchResponses = async () => {
    const responses = [];
    Object.keys(state).forEach(async (id) => {
      const result = await fetchResponse(id);
      responses.push(result);
    });
    return responses;
  };

  const updateState = async (id) => {
    const t1 = new Date();
    // const response = await fetchResponse(id);
    const responses = await fetchResponses();
    console.log('responses ' + responses);
    setState((state) => {
      const newTime = Number(state[id].time) + 0.5;
      return {
        ...state,
        1: {
          id: 1,
          currentX: Math.floor(Math.random() * 2),
          time: newTime.toFixed(1),
        },
        2: {
          id: 2,
          currentX: Math.floor(Math.random() * 2),
          time: newTime.toFixed(1),
        },
      };
    });
    setTimeout(updateState, 500, id);
    // setTimeout(updateState, Math.max(0, 500 - new Date() + t1), id);
  };

  useEffect(() => {
    const timeOutId = setTimeout(updateState, 500, 1);
    console.log('called');
    return () => {
      clearTimeout(timeOutId);
    };
  }, []);

  const currentXes = Object.values(state).map((entry) => entry.currentX);

  console.log('Current X es: ' + currentXes);

  return (
    <div className='app'>
      <ChartComponent currentX={currentXes} time={state[1].time} />
    </div>
  );
};

export default App;
