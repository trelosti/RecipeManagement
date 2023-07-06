import React from 'react';
import { render, screen } from '@testing-library/react';
import Box from './Box';

describe('Box', () => {
  it('renders without errors', () => {
    render(<Box>hello</Box>);
    expect(screen.getByText('hello')).toBeInTheDocument();
  });

  it('adds atom props styles', () => {
    render(
      <Box margin="10px" padding="10px" className="testClass">
        hello
      </Box>
    );
    const boxElement = screen.getByText('hello');
    expect(boxElement).toHaveStyle({
      margin: '10px',
      padding: '10px',
    });
    expect(boxElement).toHaveClass('testClass');
  });

  it('adds self props styles', () => {
    render(
      <Box
        width="15px"
        height="15px"
        radius="10px"
        flex
        justify="flex-start"
        align="center"
      >
        hello
      </Box>
    );
    expect(screen.getByText('hello')).toHaveStyle({
      height: '15px',
      width: '15px',
      borderRadius: '10px',
      display: 'flex',
      justifyContent: 'flex-start',
      alignItems: 'center',
    });
  });
});
