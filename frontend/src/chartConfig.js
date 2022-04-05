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
          switch(value) {
            case 0: return 'RED';
            case 1: return 'YELLOW';
            case 2: return 'GREEN';
            default: return '';
          }
        },
      },
      min: 0,
      max: 3,
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
