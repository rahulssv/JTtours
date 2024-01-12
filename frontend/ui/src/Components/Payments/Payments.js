import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { ArrowLeft } from "react-bootstrap-icons";
import { addBooking } from "../../api";
import PaymentModel from "./PaymentModel";

function Payments() {
  const [donePayment, setDonePayment] = useState(false);
  const [desc, setDesc] = useState("Payment Processing...");

  const booking = {
    date: "",
    amount: "",
    username: "",
    batchId: "",
    travellers: [],
  };
  const location = useLocation();
  const navigate = useNavigate();
  const participantList = location.state.participantList;
  const batchDetails = location.state.batchDetails;
  const user = location.state.user;
  function  handleParticipantSubmit(){
    const date = new Date();
    const formattedDate =
      date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate();
    booking.date = formattedDate.toString();
    booking.amount =
      Number(batchDetails.perParticipantCost) * participantList.length;
    booking.username = user.username;
    booking.batchId = batchDetails.id;
    booking.travellers = participantList;
    addBooking(booking, navigate);
  };

  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => {
    setShow(true);
    setTimeout(() => {
      setDonePayment(true);
      setDesc("Congratulation, Your Tour Booked Successfully!");
    }, 2000);
    setTimeout(() => {
      handleParticipantSubmit();
      navigate("/");
    }, 4500);
  };
  return (
    <>
      <div className="container mt-4">
        <button
          className="btn fs-5 mb-2 d-flex align-items-center justify-content-center border border-0"
          onClick={() => {
            navigate(-1);
          }}
          style={{ backgroundColor: "#003D59", color: "#FFFFFF" }}
        >
          <ArrowLeft></ArrowLeft>
        </button>
        <div className="card">
          <div
            className="card-header"
            style={{ backgroundColor: "#003D59", color: "#FFF" }}
          >
            <h6 className="display-6">Payments</h6>
          </div>
          <div
            className="card-body"
            style={{ backgroundColor: "white", color: "black" }}
          >
            <div className="row mb-4">
              <div className="col">
                <h4>Summary</h4>
              </div>
            </div>
            <div className="row">
              <div className="col-sm-3">Tour Name</div>
              <div className="col-sm-9 text-secondary">
                {batchDetails.tour.name}
              </div>
            </div>
            <hr />
            <div className="row">
              <div className="col-sm-3">Per Person Cost</div>
              <div className="col-sm-9 text-secondary">
                {batchDetails.perParticipantCost}
              </div>
            </div>
            <hr />
            <div className="row">
              <div className="col-sm-3">Start Date</div>
              <div className="col-sm-9 text-secondary">
                {batchDetails.startDate}
              </div>
            </div>
            <hr />
            <div className="row">
              <div className="col-sm-3">Total Cost(INR)</div>
              <div className="col-sm-9 text-secondary">
                {batchDetails.perParticipantCost * participantList.length}
              </div>
            </div>
            <hr />
            <div className="row">
              <div className="col">
                {participantList.length !== 0 && (
                  <table className="table table-primary table-striped table-hover caption-top">
                    <caption>List of Participants</caption>
                    <thead>
                      <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Mobile Number</th>
                      </tr>
                    </thead>
                    <tbody>
                      {participantList.map((participant, index) => {
                        return (
                          <>
                            <tr>
                              <td>{index + 1}</td>
                              <td>
                                {participant.name.first +
                                  " " +
                                  participant.name.last}
                              </td>
                              <td>{participant.mobile.number}</td>
                            </tr>
                          </>
                        );
                      })}
                    </tbody>
                  </table>
                )}
              </div>
            </div>
            <div className="row d-flex justify-content-center mt-2">
              <button
                className="btn btn-primary w-25"
                style={{ backgroundColor: "#003D59" }}
                // onClick={handleParticipantSubmit}
                onClick={handleShow}
              >
                Make Payment
              </button>
            </div>
          </div>
        </div>
        <PaymentModel
          show={show}
          setShow={setShow}
          handleClose={handleClose}
          donePayment={donePayment}
          desc={desc}
        />
      </div>
    </>
  );
}

export default Payments;
