import React, { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';
import { pieChartOptions } from './chartConfig';
import { SERVER_URL } from './consts';
import DropDown from './DropDown';
import { customCanvasBackgroundColor } from './plugins';

const PieChart = () => {
  const [trafficLightId, setTrafficLightId] = useState(0);
  const [trafficLightPercentages, setTrafficLightPercentages] = useState({
    GREEN: 33,
    RED: 33,
    YELLOW: 33,
  });

  useEffect(() => {
    const response = fetch(
      SERVER_URL + '/gridTrafficLightMetrics?tf_id=' + trafficLightId
    )
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setTrafficLightPercentages({
          GREEN: data.greenDuration,
          RED: data.redDuration,
          YELLOW: data.yellowDuration,
        });
      });
  }, [trafficLightId]);

  const data = {
    labels: ['Red', 'Green', 'Yellow'],
    datasets: [
      {
        label: 'Percentage of light status',
        data: [
          trafficLightPercentages.RED,
          trafficLightPercentages.GREEN,
          trafficLightPercentages.YELLOW,
        ],
        backgroundColor: [
          'rgb(255, 99, 132)',
          'rgb(54, 140, 120)',
          'rgb(255, 205, 86)',
        ],
      },
    ],
  };

  return (
    <div className='pie-chart'>
      <div>
        <p>{trafficLightId}</p>
        <Pie
          options={pieChartOptions}
          data={data}
          plugins={[customCanvasBackgroundColor]}
        />
        <DropDown>
          <p onClick={() => setTrafficLightId(1)}>1</p>
          <p onClick={() => setTrafficLightId(2)}>2</p>
        </DropDown>
      </div>
    </div>
  );
};

export default PieChart;
