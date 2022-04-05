import React from 'react';
import './DropDown.css';

const DropDown = (props) => {
  return (
    <div className='dropdown'>
      <div className='button'>Dropdown</div>
      <div className='dropdown-content'>{props.children}</div>
    </div>
  );
};

export default DropDown;
