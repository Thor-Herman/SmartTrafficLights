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

const ChartComponent = () => {
  // var canvas = document.getElementById('');
  // var parent = document.getElementById('app');
  // canvas.width = parent.offsetWidth;
  // canvas.height = parent.offsetHeight;

  return (
    <div className='chart'>
      <Bar data={data} options={options}/>
    </div>
  );
};

export default ChartComponent;
