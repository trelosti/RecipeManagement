import React from 'react';
import { render } from '@testing-library/react';
import AddImage from './AddImage';

describe('Avatar', () => {
  it('renders correctly', () => {
    const onChangeMock = jest.fn();
    render(<AddImage name="test" onChange={onChangeMock} />);
    expect(onChangeMock).toHaveBeenCalledTimes(0);
  });
  afterEach(() => {
    jest.clearAllMocks();
  });
});
