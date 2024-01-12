import React from "react";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Card from "../Card";

const testData = {
  name: "Test Name",
  image: "test-image.jpg",
  desc: "Test Description",
};
describe("Card Component", () => {
    
  test("renders card with correct data", () => {
    render(<Card data={testData} />);

    //check if name rendered
    const nameElement = screen.getByText(testData.name);
    expect(nameElement).toBeInTheDocument();
  });

  test("renders card with image", () => {
    render(<Card data={testData} />);

    const image = screen.getByRole("img");
    expect(image).toBeInTheDocument();
  });

});
