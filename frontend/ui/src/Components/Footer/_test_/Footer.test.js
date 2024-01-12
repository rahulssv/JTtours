import React from "react";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Footer from "../Footer";
import { Facebook, Twitter, Linkedin, Instagram } from "react-bootstrap-icons";
import { MemoryRouter } from "react-router-dom";

describe("Footer Component", () => {
  it("renders the correct links", () => {
    render(
      <MemoryRouter>
        <Footer />
      </MemoryRouter>
    );
    const homeLink = screen.getByText(/home/i);
    expect(homeLink).toBeInTheDocument();
    const featuresLink = screen.getByText(/features/i);
    expect(featuresLink).toBeInTheDocument();
    const pricingLink = screen.getByText(/pricing/i);
    expect(pricingLink).toBeInTheDocument();
    const aboutLink = screen.getByText(/about/i);
    expect(aboutLink).toBeInTheDocument();
  });

  it("renders social media icons", () => {
    render(
      <MemoryRouter>
        <Footer>
          <Facebook />
          <Twitter />
          <Linkedin />
          <Instagram />
        </Footer>
      </MemoryRouter>
    );

    const facebookIcon = screen.getByTestId("facebook-icon");
    const twitterIcon = screen.getByTestId("twitter-icon");
    const linkedinIcon = screen.getByTestId("linkedin-icon");
    const instagramIcon = screen.getByTestId("instagram-icon");

    expect(facebookIcon).toBeInTheDocument();
    expect(twitterIcon).toBeInTheDocument();
    expect(linkedinIcon).toBeInTheDocument();
    expect(instagramIcon).toBeInTheDocument();
  });

  it("renders the footer text", () => {
    render(
      <MemoryRouter>
        <Footer />
      </MemoryRouter>
    );
    const footerText = screen.getByText(/2023 jt travellers/i);
    expect(footerText).toBeInTheDocument();
  });
});
