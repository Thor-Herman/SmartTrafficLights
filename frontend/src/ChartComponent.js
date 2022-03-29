import {
  BarElement,
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LinearScale,
  Title,
  Tooltip,
  PointElement,
  LineElement,
} from 'chart.js';
import { useEffect, useState } from 'react';
import { Line } from 'react-chartjs-2';

import { customCanvasBackgroundColor } from './plugins';
import { CHART_BG_COLOR, MAX_POINTS_DISPLAYED_ON_CHART } from './consts';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  PointElement,
  LineElement
);

ChartJS.defaults.color = '#AAAAAA';

const options = {
  responsive: true,
  scales: {
    y: {
      ticks: {
        // Include a dollar sign in the ticks
        callback: function (value, index, ticks) {
          return value === 1 ? 'On' : value === 0 ? 'Off' : '';
        },
      },
      min: 0,
      max: 2,
      grid: {
        color: CHART_BG_COLOR,
      },
    },
    x: {
      title: {
        display: true,
        text: 'Time (s)',
      },
      grid: {
        color: CHART_BG_COLOR,
      },
    },
  },
  animation: {
    duration: 0,
  },
  transitions: {
    active: 100,
  }
};

const ChartComponent = ({ currentX, time }) => {
  const [labels, setLabels] = useState([]);
  const [xDataSet, setXDataSet] = useState([]);

  useEffect(() => {
    setLabels((ds) => {
      if (ds.length > MAX_POINTS_DISPLAYED_ON_CHART)
        // Don't want to display all values. Only most recent 3 seconds
        ds = ds.slice(ds.length - MAX_POINTS_DISPLAYED_ON_CHART);
      return [...ds, time];
    });
    setXDataSet((ds) => {
      if (ds.length > MAX_POINTS_DISPLAYED_ON_CHART)
        // Don't want to display all values. Only most recent 3 seconds
        ds = ds.slice(ds.length - MAX_POINTS_DISPLAYED_ON_CHART);
      return [...ds, currentX];
    });
  }, [currentX, time]);

  const data = {
    labels: labels,
    datasets: [
      {
        label: 'Traffic light status',
        data: xDataSet,
        borderColor: 'rgb(255, 99, 132)',
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
      },
    ],
  };

  return (
    <div className='chart'>
      <Line
        data={data}
        options={options}
        plugins={[customCanvasBackgroundColor]}
      />
    </div>
  );
};

export default ChartComponent;
