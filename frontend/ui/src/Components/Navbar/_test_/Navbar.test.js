import React from "react";
import { fireEvent, render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import NavBar from "../NavBar";
import { BrowserRouter, MemoryRouter } from "react-router-dom";
import userEvent from "@testing-library/user-event";

describe("Navbar Component", () => {
    
  test('renders "Traveller" logo when user not logged in', () => {
    render(
      <MemoryRouter>
        <NavBar user={null} setUser={jest.fn()} setAdmin={jest.fn()} />
      </MemoryRouter>
    );
    var logoelement = screen.getByText("Traveller");
    expect(logoelement).toBeInTheDocument();
  });

  test("Home, Tour, About Us, Contact Us, Log In link is clicked when user not logged in", () => {
    render(
      <MemoryRouter>
        <NavBar user={null} setUser={jest.fn()} setAdmin={jest.fn()} />
      </MemoryRouter>
    );
    userEvent.click(screen.getByText(/home/i));
    expect(screen.getByText(/home/i)).toBeInTheDocument();

    userEvent.click(screen.getByText(/tour/i));
    expect(screen.getByText(/tour/i)).toBeInTheDocument();

    userEvent.click(screen.getByText(/about us/i));
    expect(screen.getByText(/about us/i)).toBeInTheDocument();

    userEvent.click(screen.getByText(/contact us/i));
    expect(screen.getByText(/contact us/i)).toBeInTheDocument();

    userEvent.click(screen.getByText(/log in/i));
    expect(screen.getByText(/log in/i)).toBeInTheDocument();
  });

  test("profile menu not visible when user not logged in", () => {
    const profilemenu = screen.queryByTestId("profile-menu");
    expect(profilemenu).not.toBeInTheDocument();
  });

  test("displays profile menu when user is logged in", () => {
    const user = JSON.stringify({
      username: "testUser",
      name: "Test User",
      email: "testuser@example.com",
    });
    render(
      <MemoryRouter>
        <NavBar user={user} setUser={jest.fn()} setAdmin={jest.fn()} />
      </MemoryRouter>
    );
    const profilemenu = screen.queryByTestId("profile-menu");
    expect(profilemenu).toBeInTheDocument();
  });

  test('displays "home" and and user profile avatar links when the user is logged in', () => {
    const user = JSON.stringify({
      username: "testUser",
      name: "Test User",
      email: "testuser@example.com",
    });
    render(
      <MemoryRouter>
        <NavBar user={user} setUser={jest.fn()} setAdmin={jest.fn()} />
      </MemoryRouter>
    );
    const profileicon = screen.getByTestId("profile-icon");
    fireEvent.click(profileicon);

    const profilemenu = screen.getByTestId("profile-menu");

    // expect(screen.getByText(/Test User/i)).toBeInTheDocument();
    expect(profilemenu).toBeInTheDocument();
  });

  test('should log out the user when "Log Out" is clicked', () => {
    const user = JSON.stringify({
      username: "shrutiChoudhary",
      name: "Shruti Choudhary",
      email: "shruti@example.com",
    });

    const setUser = jest.fn();
    const setAdmin = jest.fn();

    render(
      <BrowserRouter>
        <NavBar user={user} setUser={setUser} setAdmin={setAdmin} />
      </BrowserRouter>
    );

    const profileicon = screen.getByTestId("profile-icon");
    fireEvent.click(profileicon);

    const logOutButton = screen.getByText(/log out/i);

    fireEvent.click(logOutButton);
    expect(setUser).toHaveBeenCalledWith(null);
    expect(setAdmin).toHaveBeenCalledWith(false);
    expect(window.location.pathname).toBe("/");
  });
});
