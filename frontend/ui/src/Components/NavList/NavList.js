import React from "react";
import { useLocation } from "react-router-dom";

function NavList(props) {
  const location = useLocation();
  return (
    <>
      <nav aria-label="breadcrumb">
        <ol className="breadcrumb">
          <li className="breadcrumb-item">
            <a href="/">Home</a>
          </li>
          {location.pathname === "/tours" && (
            <li className="breadcrumb-item active">Tour</li>
          )}
          {location.pathname === "/tours/add-tour" && (
            <>
              <li className="breadcrumb-item">
                <a href="/tours">Tour</a>
              </li>
              <li className="breadcrumb-item active">Add tour</li>
            </>
          )}
        </ol>
      </nav>
    </>
  );
}

export default NavList;
