import React from "react";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Main from "../Main";

//mocking data that the main comp uses
const mockData = {
  obj: [
    { name: "Tour 1", image: "tour1.jpg" },
    { name: "Tour 2", image: "tour2.jpg" },
    { name: "Tour 3", image: "tour3.jpg" },
  ],
};

//mocking Card comp
//the below callback function returns a function which is a react component
jest.mock("../../Card/Card", () => ({ data }) => (
  <div data-testid="mock-card">
    <img src={data.image} alt={data.name}></img>
    <p>{data.name}</p>
  </div>
));

describe("Main Component", () => {
  beforeEach(() => {
    //mocking data import to return mock data
    jest.mock("../../../assets/Json/info.json", () => ({
      obj: mockData.obj,
    }));
  });

  it("renders Main component", () => {
    render(<Main />);
  });

  it("displays header and popular tours label", () => {
    render(<Main />);
    expect(screen.getByText(/explore the world with us/i)).toBeInTheDocument();
    expect(screen.getByText("Popular Tours")).toBeInTheDocument();
  });

  it("renders correct number of tour cards", () => {
    render(<Main />);
    const tourCards = screen.getAllByTestId("mock-card");
    expect(tourCards.length).toBe(mockData.obj.length);
  });

  it("displays image and tour names in the card", () => {
    render(<Main />);
    const tourCards = screen.getAllByTestId("mock-card");
    tourCards.forEach((card) => {
      const image = card.querySelector("img"); //find image element within the card
      const name = card.textContent; //get the text content of the card
      expect(name).toBeTruthy();
      expect(image).toBeInTheDocument();
    });
  });
});
