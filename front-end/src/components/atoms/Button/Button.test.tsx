import React from 'react';
import { render, screen } from '@testing-library/react';
import Button from './Button';

describe('Button', () => {
  it('renders without errors', () => {
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    render(<Button onClick={() => {}}>Click me</Button>);
    expect(screen.getByText('Click me')).toBeInTheDocument();
  });

  it('adds atom props styles', () => {
    render(
      <Button
        margin="10px"
        padding="10px"
        className="testClass"
        // eslint-disable-next-line @typescript-eslint/no-empty-function
        onClick={() => {}}
      >
        Click me
      </Button>
    );
    const buttonElement = screen.getByText('Click me');
    expect(buttonElement).toHaveStyle({
      margin: '10px',
      padding: '10px',
    });
    expect(buttonElement).toHaveClass('testClass');
  });

  it('calls onClick when button is clicked', () => {
    const handleClick = jest.fn();
    render(<Button onClick={handleClick}>Click me</Button>);
    const buttonElement = screen.getByText('Click me');
    buttonElement.click();
    expect(handleClick).toHaveBeenCalled();
  });

  it('renders the correct variant', () => {
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    render(<Button onClick={() => {}}>Button 1</Button>);
    expect(screen.getByText('Button 1')).toHaveClass('fill');
    render(
      // eslint-disable-next-line @typescript-eslint/no-empty-function
      <Button onClick={() => {}} variant="outline">
        Button 2
      </Button>
    );
    expect(screen.getByText('Button 2')).toHaveClass('outline');
    render(
      // eslint-disable-next-line @typescript-eslint/no-empty-function
      <Button onClick={() => {}} variant="frameless">
        Button 3
      </Button>
    );
    expect(screen.getByText('Button 3')).toHaveClass('frameless');
  });
});
