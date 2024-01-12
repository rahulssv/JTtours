import React from "react";
import Main from "../../Components/Main/Main";
import { Breadcrumbs } from "react-breadcrumbs-dynamic";
import Hero from "../../Components/hero/Hero";
import AboutUs from "../../Components/aboutus/AboutUs";

function Homepage() {
  return (
    <div>
      <Breadcrumbs />
      <div >
        <Hero />
        <Main />
        <AboutUs />
      </div>
    </div>
  );
}

export default Homepage;
