import React from "react";
import { fireEvent, getByText, render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Pagination from "../Pagination";
import { MemoryRouter } from "react-router-dom";

describe("Pagination Component", () => {
  test("renders Pagination component", () => {
    const { container } = render(
      <MemoryRouter>
        <Pagination />
      </MemoryRouter>
    );
    expect(container).toBeInTheDocument();
  });

  test("should get initial Pagination state", () => {
    render(
      <MemoryRouter initialEntries={["/?_page=1&limit=5"]}>
        <Pagination />
      </MemoryRouter>
    );
    expect(screen.getByText("1")).toHaveClass("active");
  });

  test("should handle previous page click", () => {
    render(
      <MemoryRouter initialEntries={["/?_page=1&limit=5"]}>
        <Pagination />
      </MemoryRouter>
    );
    fireEvent.click(screen.getByText("Previous"));
    expect(screen.getByText("1")).toHaveClass("active");
  });

  test("should handle next page click", () => {
    render(
      <MemoryRouter initialEntries={["/?_page=1&limit=5"]}>
        <Pagination />
      </MemoryRouter>
    );

    fireEvent.click(screen.getByText("Next"));
    expect(screen.getByText("2")).toHaveClass("active");
  });
});
