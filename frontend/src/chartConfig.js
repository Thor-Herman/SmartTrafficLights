import { CHART_BG_COLOR } from "./consts";
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

export const options = {
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
  },
};
