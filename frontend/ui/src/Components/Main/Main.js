import React from "react";
import "../../App.css";
import Card from "../Card/Card";
import obj from "../../assets/Json/info.json";

// import { Link } from "react-router-dom";
// import obj from "../assets/Json/info.json";

export default function Main() {
  return (
    <div className="container-fluid p-2" style={{width:'80%'}}  >
      <div className="container main-header fs-1 text-center p-3 mt-3 mb-3">
        Explore The World With Us
      </div>
      <div className="container-fluid fs-2 mb-3">
        <label className="popularTours">Popular Tours</label>
      </div>
      {/* <div className="container-fluid mb-2 mt-2 rounded card-group gap-1"> */}
      <div className="tour-card">
        {obj.obj.map((data, index) => {
          return <Card  key={index} data={data} />;
        })}
      </div>
    </div>
  );
}
