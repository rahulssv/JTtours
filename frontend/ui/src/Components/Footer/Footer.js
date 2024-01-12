import React from "react";
import { Link } from "react-router-dom";
import "../../App.css";
import { Facebook, Twitter, Linkedin, Instagram } from "react-bootstrap-icons";

export default function Footer() {
  return (
    <div id="footer" className="container-fluid mt-3 ">
      <footer className="pt-2">
        <div className="row">
          <div className="col text-center">
            <h3>Traveller</h3>
            <ul className="nav flex-column">
              <li className="nav-item mb-2">
                <Link to="/" className="nav-link p-0">
                  Home
                </Link>
              </li>
              <li className="nav-item mb-2">
                <Link to="/" className="nav-link p-0">
                  Features
                </Link>
              </li>
              <li className="nav-item mb-2">
                <Link to="/" className="nav-link p-0">
                  Pricing
                </Link>
              </li>
              <li className="nav-item mb-2">
                <Link to="/" className="nav-link p-0">
                  About
                </Link>
              </li>
            </ul>
          </div>
          <div className="col"></div>
          <div className="col"> </div>
          <div className="col"></div>
          <div className="col text-center">
            <h3>Connect</h3>
            <ul className="nav flex-column">
              <li className="nav-item mb-2">
                <Link to="/" className="nav-link p-0">
                  <Facebook
                    className="me-2"
                    data-testid="facebook-icon"
                  ></Facebook>
                  <div className="d-none d-md-inline">Facebook</div>
                </Link>
              </li>
              <li className="nav-item mb-2">
                <Link to="/" className="nav-link p-0">
                  <Linkedin
                    className="me-2"
                    data-testid="linkedin-icon"
                  ></Linkedin>
                  <div className="d-none d-md-inline">LinkedIn</div>
                </Link>
              </li>
              <li className="nav-item mb-2">
                <Link to="/" className="nav-link p-0">
                  <Twitter
                    className="me-2"
                    data-testid="twitter-icon"
                  ></Twitter>
                  <div className="d-none d-md-inline">Twitter</div>
                </Link>
              </li>
              <li className="nav-item mb-2">
                <Link to="#" className="nav-link p-0">
                  <Instagram
                    className="me-2"
                    data-testid="instagram-icon"
                  ></Instagram>
                  <div className="d-none d-md-inline">Instagram</div>
                </Link>
              </li>
            </ul>
          </div>
        </div>
        <div className="container-fluid">
          <p className="text-center">&copy; 2023 JT Travellers</p>
        </div>
      </footer>
    </div>
  );
}
