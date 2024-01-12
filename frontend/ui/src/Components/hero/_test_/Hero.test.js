import { fireEvent, render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Hero from "../Hero";
import { MemoryRouter, useNavigate } from "react-router-dom";

//jest.mock() is used to mock the useNavigate hook from react-router-dom
jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"), //used to retrieve actual, unmocked implementation of react-router-dom and ... includes all actual functionalities from react-router-dom while only mocking useNavigate
  useNavigate: jest.fn(),
}));

describe("Hero Component", () => {
  test('should display "Explore your dream destination" heading and followng paragraph', () => {
    render(
      <MemoryRouter>
        <Hero />
      </MemoryRouter>
    );
    const heading = screen.getByRole("heading", {
      name: /explore your dream/i,
    });
    expect(heading).toBeInTheDocument();

    const text = screen.getByText(/Make your travel memorable/i);

    expect(text).toBeInTheDocument();
  });

  test('navigates to "/tours" when button is clicked', () => {
    //useNavigate is mocked to return Jest mock function navigateMock
    const navigateMock = jest.fn();
    require("react-router-dom").useNavigate.mockReturnValue(navigateMock);

    render(
      <MemoryRouter>
        <Hero />
      </MemoryRouter>
    );

    const buttonElement = screen.getByRole("button", { name: /explore/i });
    fireEvent.click(buttonElement);
    //when button clicked, jest checks if navigateMock() called with argument "/tours"
    expect(navigateMock).toHaveBeenCalledWith("/tours");
  });
});
