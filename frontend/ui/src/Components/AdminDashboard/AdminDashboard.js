import React from "react";
import { Link } from "react-router-dom";
import "./AdminDashboard.css";

function AdminDashboard() {
  return (
    <>
      <div className="container py-4">
        <div className="row">
          <div className="col d-grid">
            <Link
              to="/tours/add-tour"
              style={{ backgroundColor: "#003D59", color: "#FFFFFF" }}
              className="btn btn-primary dashboard-btn"
              role="button"
            >
              Add a New Tour
            </Link>
          </div>
        </div>
      </div>
    </>
  );
}

export default AdminDashboard;
