import React from "react";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import TourList from "../TourList";
import { MemoryRouter } from "react-router-dom";

const testData = [
  {
    id: 1,
    name: "Tour 1",
    startAt: "Location A",
    endAt: "Location B",
    mode: "WALK",
    days: 5,
    difficultyLevel: "Medium",
  },
];

describe("TourList Component", () => {
  test("renders table with tour data", () => {
    render(
      <MemoryRouter>
        <TourList data={testData} isAdmin={true} />
      </MemoryRouter>
    );
    //check if the table is rendered
    const tableElement = screen.getByRole("table");
    expect(tableElement).toBeInTheDocument();
  });

  test("checks if the tour data is rendered in the table", () => {
    render(
      <MemoryRouter>
        <TourList data={testData} isAdmin={true} />
      </MemoryRouter>
    );
    const tourName = screen.getByText(/tour 1/i);
    expect(tourName).toBeInTheDocument();
    const tourDuration = screen.getByText(/5 Days/i);
    expect(tourDuration).toBeInTheDocument();
  });

  test("displays update option for admin users", () => {
    render(
      <MemoryRouter>
        <TourList data={testData} isAdmin={true} />
      </MemoryRouter>
    );

    //check if update button is rendered for each tour for admin users
    const updateButton = screen.getAllByRole("link", { name: /update/i });
    expect(updateButton).toHaveLength(testData.length);
  });

  test("does not display option for update for non-admin users", () => {
    render(
      <MemoryRouter>
        <TourList data={testData} isAdmin={false} />
      </MemoryRouter>
    );

    const updateButton = screen.queryByRole("link", { name: /update/i });
    expect(updateButton).not.toBeInTheDocument();
  });
});
