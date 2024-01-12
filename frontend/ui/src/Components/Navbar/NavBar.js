import React, { useEffect, useState } from "react";
import "../../App.css";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { Button } from "react-bootstrap";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import $ from "jquery";

export default function NavBar({ user, setUser, setAdmin }) {
  const [profileMenu, setProfileMenu] = useState(false);
  const navigate = useNavigate();
  useEffect(() => {
    setProfileMenu(false);
  }, []);

  const handleProfile = (e) => {
    setProfileMenu(!profileMenu);
  };
  const handleLogout = () => {
    localStorage.removeItem("user");
    setUser(localStorage.getItem("user"));
    setAdmin(false);
    setProfileMenu(false);
    toast.success("User Log Out Successfully!");
    navigate(-1);
  };
  // $(document).on("click", function () {
  //   $(".profile-menu").hide();
  // });
  // $("#profile-icon").on("click", function (event) {
  //   $(".profile-menu").show();
  //   event.stopPropagation();
  // });
  return (
    <div id="navbar" className="container-fluid " style={{ padding: "0px" }}>
      <nav className="navbar navbar-expand-lg px-4">
        <span
          className="navbar-brand text-light fs-1 "
          style={{ cursor: "pointer" }}
          onClick={()=> navigate("/")}
        >
          Traveller
        </span>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div
          className="collapse navbar-collapse text-light text-center"
          id="navbarSupportedContent"
        >
          <div className="container p-3 ">
            <ul className="navbar-nav ">
              <li className="nav-item">
                <Link
                  style={{ color: "white" }}
                  className="nav-link"
                  aria-current="page"
                  to="/"
                >
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <NavLink
                  style={{ color: "white" }}
                  className="nav-link "
                  aria-current="page"
                  to="/tours"
                >
                  Tours
                </NavLink>
              </li>

              <li className="nav-item">
                {/* <NavLink
                  style={{ color: "white" }}
                  className="nav-link "
                  aria-current="page"
                  to="/"
                >
                  About Us
                </NavLink> */}
                <a className="nav-link"  style={{ color: "white" }} href="#aboutus">About us</a>
              </li>

              <li className="nav-item">
                <NavLink
                  style={{ color: "white" }}
                  className="nav-link "
                  aria-current="page"
                  to="/bookings"
                >
                  Bookings
                </NavLink>
              </li>
              <li className="nav-item">
                {/* <NavLink
                  style={{ color: "white" }}
                  className="nav-link"
                  aria-current="page"
                  to="/"
                >
                  Contact Us
                </NavLink> */}
                <a className="nav-link"  style={{ color: "white" }} href="#footer">Contact us</a>
              </li>

              {!user ? (
                <li className="nav-item">
                  <NavLink
                    style={{ color: "white" }}
                    className="nav-link"
                    aria-current="page"
                    to="/login"
                  >
                    Log In
                  </NavLink>
                </li>
              ) : (
                <span
                  style={{
                    width: "45px",
                    fontWeight: "bold",
                    fontSize: "35px",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    color: "#003D59",
                    cursor: "pointer",
                    height: "45px",
                    borderRadius: "50%",
                    background: "white",
                  }}
                  onClick={handleProfile}
                  data-testid="profile-icon"
                  id="profile-icon"
                >
                  {JSON.parse(user).username?.charAt(0).toUpperCase()}
                </span>
              )}
            </ul>
          </div>
        </div>
      </nav>
      {user && (
        <div
          className="profile-menu"
          data-testid="profile-menu"
          style={profileMenu ? { height: "350px" } : { height: "0px" }}
        >
          <div
            style={{
              background: "#003D59",
              color: "white",
              fontSize: "40px",
              borderRadius: "50%",
              width: "50px",
              height: "50px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            {JSON.parse(user).username?.charAt(0).toUpperCase()}
          </div>
          {/* <h4 style={{ marginTop: "20px" }}>{JSON.parse(user).username} </h4> */}
          <p>{JSON.parse(user).username}</p>
          <p>Role : {JSON.parse(user)?.role}</p>
          <Button
            id="#logout-button"
            style={{ background: "#003D59" }}
            onClick={handleLogout}
          >
            Log Out
          </Button>
        </div>
      )}

      <ToastContainer />
    </div>
  );
}
