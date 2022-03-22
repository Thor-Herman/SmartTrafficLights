import {
  BarElement,
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LinearScale,
  Title,
  Tooltip,
} from 'chart.js';
import { useEffect, useState } from 'react';
import { Bar } from 'react-chartjs-2';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

ChartJS.defaults.color = '#AAAAAA';
const bgColor = '#20232a';

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
        color: bgColor,
      },
    },
    x: {
      title: {
        display: true,
        text: 'Time (s)',
      },
      grid: {
        color: bgColor,
      },
    },
  },
};

const custom_canvas_background_color = {
  id: 'custom_canvas_background_color',
  beforeDraw: (chart, args, options) => {
    const {
      ctx,
      chartArea: { top, left, width, height },
    } = chart;
    ctx.save();
    ctx.globalCompositeOperation = 'destination-over';
    ctx.fillStyle = bgColor;
    ctx.fillRect(left, top, width, height);
    ctx.restore();
  },
};

const ChartComponent = ({ currentX, time }) => {
  const [yDataSet, setYDataSet] = useState([]);
  const [xDataSet, setXDataSet] = useState([]);

  useEffect(() => {
    setYDataSet((ds) => {
      if (ds.length > 30)
        // Don't want to display all values. Only most recent 3 seconds
        ds = ds.slice(ds.length - 30);
      return [...ds, time];
    });
    setXDataSet((ds) => {
      if (ds.length > 30)
        // Don't want to display all values. Only most recent 3 seconds
        ds = ds.slice(ds.length - 30);
      return [...ds, currentX];
    });
  }, [currentX, time]);

  const data = {
    labels: yDataSet,
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
      <Bar
        data={data}
        options={options}
        plugins={[custom_canvas_background_color]}
      />
    </div>
  );
};

export default ChartComponent;
