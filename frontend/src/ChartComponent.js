import { useEffect, useState } from 'react';
import { Line } from 'react-chartjs-2';

import { customCanvasBackgroundColor } from './plugins';
import { MAX_POINTS_DISPLAYED_ON_CHART } from './consts';
import { options } from './chartConfig';

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
