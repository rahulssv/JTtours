import React, { useEffect } from "react";
import BatchList from "./BatchList";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { useState } from "react";
import { toast } from "react-toastify";
import { useLocation } from "react-router-dom";
import { ArrowLeft } from "react-bootstrap-icons";
import BatchFilter from "../../Components/Filter/BatchFilter";
import { getTourDetails } from "../../api";

export default function TourDetails({isAdmin}) {
  const { id } = useParams();
  const location = useLocation();
  const [batchData, setBatchData] = useState([]);
  // const isAdmin = location.state.isAdmin;
  const user = location.state.user;
  const [tourData, setTourData] = useState({});
  const navigate = useNavigate();
  useEffect(() => {
    getTourDetails(id, setTourData);
  }, [id]);
  return (
    <>
      <div className="container-fluid py-3">
        <div className="container-fluid d-flex p-0 m-0 mb-4 px-5">
          <button
            className="btn fs-5"
            onClick={() => {
              navigate(-1);
            }}
            style={{ backgroundColor: "#003D59", color: "#FFFFFF" }}
          >
            <ArrowLeft></ArrowLeft>
          </button>

          <div className="container-fluid text-center">
            <h2>{tourData && tourData.name}</h2>
          </div>
        </div>
        <div className="container card shadow py-2 my-2">
          <div
            className="card-body"
            style={{ backgroundColor: "white", color: "black" }}
          >
            <div className="row">
              <div className="col-sm-3">
                <h6 className="mb-0">Source</h6>
              </div>
              <div className="col-sm-9 text-secondary">
                {tourData && tourData.startAt}
              </div>
            </div>
            <hr />
            <div className="row">
              <div className="col-sm-3">
                <h6 className="mb-0">Destination</h6>
              </div>
              <div className="col-sm-9 text-secondary">
                {tourData && tourData.endAt}
              </div>
            </div>
            <hr />
            <div className="row">
              <div className="col-sm-3">
                <h6 className="mb-0">Mode of Transport</h6>
              </div>
              <div className="col-sm-9 text-secondary">
                {tourData && tourData.mode}
              </div>
            </div>
            <hr />
            <div className="row">
              <div className="col-sm-3">
                <h6 className="mb-0">Difficulty Level</h6>
              </div>
              <div className="col-sm-9 text-secondary">
                {tourData && tourData.difficultyLevel}
              </div>
            </div>
            <hr />
            <div className="row">
              <div className="col-sm-3">
                <h6 className="mb-0">Days</h6>
              </div>
              <div className="col-sm-9 text-secondary">
                {tourData && tourData.days}
              </div>
            </div>
            <hr />
            <div className="row">
              <div className="col-sm-3">
                <h6 className="mb-0">Nights</h6>
              </div>
              <div className="col-sm-9 text-secondary">
                {tourData && tourData.nights}
              </div>
            </div>
          </div>
        </div>
        {isAdmin && (
          <div className="container py-2">
            <Link
              to={`/tours/${id}/addBatch`}
              className="btn fs-5 rounded"
              style={{ backgroundColor: "#003D59", color: "#FFFFFF" }}
            >
              New Batch
            </Link>
          </div>
        )}
        <div className="container mt-5">
          <BatchFilter setBatchData={setBatchData} isAdmin={isAdmin}></BatchFilter>
        </div>
        <div className="container mt-2 py-2 px-1">
          <BatchList tourId={id} isAdmin={isAdmin} user={user} batchData={batchData} setBatchData={setBatchData}/>
        </div>
      </div>
    </>
  );
}
