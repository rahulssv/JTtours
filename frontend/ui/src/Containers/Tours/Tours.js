import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import { BreadcrumbsItem } from "react-breadcrumbs-dynamic";
import "./Tours.css";
import TourList from "../../Components/TourList/TourList";
import NavList from "../../Components/NavList/NavList";
import Filter from "../../Components/Filter/Filter";
import AdminDashboard from "../../Components/AdminDashboard/AdminDashboard";
import { deleteTourById, getAllTours } from "../../api";

function ViewTours({ user, admin }) {
  const [isAdmin, setIsAdmin] = useState(admin);
  const [tourData, setTourData] = useState([]);
  useEffect(() => {
    if (user) {
      setIsAdmin(admin);
    }
  }, [admin, user]);

  useEffect(() => {
    getAllTours(setTourData);
  }, []);
  const handleDelete = (id) => {
    deleteTourById(setTourData, id);
  };
  return (
    <div>
      <BreadcrumbsItem to="/">Home</BreadcrumbsItem>
      <div className="nav-list-wrapper">
        <NavList></NavList>
      </div>
      {admin && <AdminDashboard></AdminDashboard>}
      <div className="filter-wrapper">
        <Filter setTourData={setTourData}></Filter>
      </div>
      <div className="tour-list-wrapper">
        <TourList
          data={tourData}
          handleDelete={handleDelete}
          isAdmin={admin}
          user={user}
        ></TourList>
      </div>
    </div>
  );
}

export default ViewTours;
