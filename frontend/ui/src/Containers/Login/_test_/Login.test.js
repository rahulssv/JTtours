import React from "react";
import { fireEvent, render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Login from "../Login";
import { MemoryRouter } from "react-router-dom";

describe("Login Component", () => {
  it("renders the Login component", () => {
    render(
      <MemoryRouter>
        <Login />
      </MemoryRouter>
    );
  });
});
