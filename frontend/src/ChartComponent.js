import {
  BarElement,
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LinearScale,
  Title,
  Tooltip,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const labels = ['1.0', '1.1', '1.2', '1.3', '1.4', '1.5', '1.6'];

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
    },
    x: {
      title: {
        display: true,
        text: 'Time (s)',
      },
    },
  },
};

const data = {
  labels,
  datasets: [
    {
      label: 'Traffic light on',
      data: [1, 1, 0, 0, 0, 0, 1],
      borderColor: 'rgb(255, 99, 132)',
      backgroundColor: 'rgba(255, 99, 132, 0.5)',
    },
  ],
};

const custom_canvas_background_color = {
  id: 'custom_canvas_background_color',
  beforeDraw: (chart, args, options) => {
    const {
      ctx,
      chartArea: { top, right, bottom, left, width, height },
      scales: { x, y },
    } = chart;
    ctx.save();
    ctx.globalCompositeOperation = 'destination-over';
    ctx.fillStyle = '#20232a';
    ctx.fillRect(left, top, width, height);
    ctx.restore();
  },
};

const ChartComponent = () => {
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
