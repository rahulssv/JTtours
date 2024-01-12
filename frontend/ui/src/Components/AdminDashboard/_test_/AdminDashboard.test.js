import React from "react";
import { fireEvent, render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import AdminDashboard from "../AdminDashboard";
import { MemoryRouter, Router } from "react-router-dom";
import { createMemoryHistory } from "history";
import user from "@testing-library/user-event";

describe(" AdminDashboard Component ", () => {
  it("renders AdminDashboard Component correctly", () => {
    render(
      <MemoryRouter>
        <AdminDashboard />
      </MemoryRouter>
    );
  });

  it("renders Add a new Tour button", () => {
    render(
      <MemoryRouter>
        <AdminDashboard />
      </MemoryRouter>
    );

    const addBtn = screen.getByRole("button", { name: /Add a New Tour/i });
    expect(addBtn).toBeInTheDocument();
  });

  it("triggers navigation when Add a New Tour button clicked", async () => {
    const { getByRole } = render(
      <MemoryRouter>
        <AdminDashboard />
      </MemoryRouter>
    );

    const addBtn = getByRole("button", { name: "Add a New Tour" });
    await user.click(addBtn);
    expect(screen.getByText("Enter Tour Details")).toBeInTheDocument();
  });
});
