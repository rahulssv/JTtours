import React from "react";
import "./hero.css";
import { useNavigate } from "react-router-dom";
 

function Hero() {
    const navigate = useNavigate();
  return (
    <div className="hero"  data-aos="zoom-in">
      <h2>Explore your dream destination</h2>

      <p>Make your travel memorable with us.</p>
       <button onClick={()=> navigate("/tours")} className="hero-btn">Explore</button>
    </div>
  );
}

export default Hero;
