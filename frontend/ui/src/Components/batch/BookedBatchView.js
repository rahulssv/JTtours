import React, { useEffect, useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import { Link, useLocation, useParams } from "react-router-dom";
import { getBatchDetailsByBatchId } from "../../api";

function BookedBatchView() {
  const location = useLocation();
  const params = useParams();
  const { id } = params;
  const [batchData, setBatchData] = useState();
  useEffect(() => {
    getBatchDetailsByBatchId(id, setBatchData);
  }, []);
  
  return (
    <div
      className="container-fluid p-4 mt-4 "
      style={{ width: "80%", background: "white" }}
      id="batch"
    >
      <h2 className="text-center">{batchData?.tour?.name}</h2>
      
      <Row className="mt-4">
        <Col>
          <img
            src="https://tse1.mm.bing.net/th/id/OIP.hvxvp2GJ_TKnDxnk3y_2iAHaE6?rs=1&pid=ImgDetMain"
            alt="img"
          />
        </Col>
        <Col
          className=""
          style={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            flexDirection:'column'
          }}
        >
          <h2>{batchData?.tour?.startAt}</h2>
          <p><i><q>Bhopal, India's capital of Madhya Pradesh, is a city of extremes. The Muslim-dominated Old City, a beautiful enclave of gorgeous mosques and a busy bazaar, lies on one side.</q></i> </p>
        </Col>
      </Row>
      <div className="mt-5">
        <h3>Tour Details :</h3>
        <div className="mt-4" style={{ width: "60%", margin: "auto" }}>
          <Row className="m-2">
            <Col>StartAt : {batchData?.tour?.startAt}</Col>
            <Col>EndAt : {batchData?.tour?.endAt}</Col>
          </Row>
          <Row className="m-2">
            <Col>Start Date : {batchData?.startDate}</Col>
            <Col>End Date : {batchData?.endDate}</Col>
          </Row>
          <Row className="m-2">
            <Col>Days : {batchData?.tour?.days}</Col>
            <Col>Nights : {batchData?.tour?.nights}</Col>
          </Row>
          <Row className="m-2">
            <Col>Capacity : {batchData?.capacity}</Col>
            <Col>Available Seats: {batchData?.availableSeats}</Col>
          </Row>
          <Row className="m-2">
            <Col>PerParticipant Cost : {batchData?.perParticipantCost} INR</Col>
            <Col>
              Status:{" "}
              <span style={{ background: "" }}>{batchData?.status}</span>
            </Col>
          </Row>
          <Row className="m-2">
            <Col>Tour Mode : {batchData?.tour?.mode}</Col>
            <Col>
              Difficulty:{" "}
              <span style={{ background: "" }}>
                {batchData?.tour?.difficultyLevel}
              </span>
            </Col>
          </Row>
        </div>
      </div>

      <div className="mt-5">
        <h3>Daily Plans :</h3>
        {batchData?.tour?.itinerary?.dayPlans?.map((plan) => {
          return (
            <div className="mt-4" style={{ width: "90%", margin: "auto" }}>
              <fieldset className="border p-2">
                <legend className="float-none w-auto">Day : {plan?.dayCount}</legend>
                <Row className="m-2">
                  <Col> Place : {plan?.place} </Col>
                  <Col>Distance : {plan?.distance} km</Col>
                  <Col>Activity: {plan?.activity}</Col>
                </Row>
                <Row className="m-2">
                  <Col>Hotel Name : {plan?.accomodation?.hotelName}</Col>
                  <Col>Address : {plan?.accomodation?.address}</Col>
                  <Col>Room Type : {plan?.accomodation?.roomType}</Col>
                </Row>
              </fieldset>
            </div>
          );
        })}
      </div>

      <div className="mt-5">
        <h3>Guide Details :</h3>
        <div
          className="mt-4 shadow-sm p-2"
          style={{
            width: "90%",
            margin: "auto",
            background: "white",
            borderRadius: "10px",
          }}
        >
          <Row className="m-2">
            <Col>Name : {batchData?.guide?.name?.first + " " + batchData?.guide?.name?.last} </Col>
            <Col>Mobile : {batchData?.guide?.mobile?.number}</Col>
          </Row>
        </div>
      </div>

      <Row className="mt-4">
        <Col
          className=""
          style={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            flexDirection:'column'
          }}
        >
          <h2>{batchData?.tour?.endAt}</h2>
          <p><i><q>The city is surrounded by lush green forests, sparkling lakes, and picturesque landscapes that attract tourists from all over the world.</q></i> </p>
        </Col>
        <Col>
          <img
            src="https://tse4.mm.bing.net/th/id/OIP.VfSrw1d5ZhvB39AoEZBd5gHaFj?rs=1&pid=ImgDetMain"
            alt="img"
          />
        </Col>
      </Row>
      <div style={{display:'flex',justifyContent:'right'}}>

      <a href="#navbar" className=" mt-4"><i style={{fontSize:'60px'}} class="bi bi-arrow-up-short"></i></a>
      </div>
       {/* <div className="m-4">

      <a href="#navbar"  style={{textDecoration:'none',float:'right'}}><i style={{fontSize:'40px',color:'red'}} className="bi bi-arrow-up-short"></i> </a>
       </div> */}
    </div>
  );
}

export default BookedBatchView;
