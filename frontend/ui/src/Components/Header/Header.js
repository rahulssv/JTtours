import React from "react";
import "../../App.css";

export default function Header() {
  return (
    <div className="container-fluid mb-2 mt-4 ">
      <img
        src="https://cdn.pixabay.com/photo/2022/10/13/09/56/mountains-7518727_1280.jpg"
        className="img-fluid rounded w-100"
        alt=""
      />
      <div className="container-fluid mt-2 p-2">
        <form style={{ padding: "0px" }}>
          <div className="container-fluid text-center ">
            <input
              className="form-control mb-2 px-4 fs-3 "
              type="search"
              placeholder="Search Tours..."
              aria-label="Search"
              style={{ borderRadius: "30px", margin: "0px", width: "100%" }}
            />
            <button
              className="btn btn-primary search-button"
              type="submit"
              id="searchTour"
            >
              Search
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
