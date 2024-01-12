import React from "react";
import { Link, useLocation, useParams } from "react-router-dom";
import { ArrowLeft } from "react-bootstrap-icons";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { toast } from "react-toastify";

function BookingForm() {
  const location = useLocation();
  const batchDetails = location.state.batchDetails;
  // console.log(batchDetails);
  const tourId = useParams().id;
  const user = JSON.parse(location.state.user);
  const navigate = useNavigate();
  const [participantList, setParticipantList] = useState([]);
  const [formErrors, setFormErrors] = useState({});
  const [participantDetails, setParticipantDetails] = useState({
    name: {
      first: "",
      last: "",
    },
    mobile: {
      number: "",
    },
  });
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "first" || name === "last" || name === "mobile") {
      validateText(name, value);
    }
    if (name === "first" || name === "last") {
      setParticipantDetails({
        ...participantDetails,
        name: { ...participantDetails.name, [name]: value },
      });
    } else {
      setParticipantDetails({
        ...participantDetails,
        mobile: { number: value },
      });
    }
  };
  const handleParticipant = (e) => {
    e.preventDefault();
    if (validateForm()) {
      participantDetails.id = new Date().getTime().toString();
      setParticipantList([...participantList, participantDetails]);
      setParticipantDetails({
        name: {
          first: "",
          last: "",
        },
        mobile: {
          number: "",
        },
      });
    } else {
      toast.error("Enter all the required details correctly");
    }
  };
  const handleDelete = (participant) => {
    const newParticipantList = participantList.filter(({ id }) => {
      return id !== String(participant.id);
    });
    setParticipantList(newParticipantList);
  };
  const validateText = (fieldName, fieldValue) => {
    const regex = /^[a-zA-Z]+(\s[A-Za-z]*)?$/;
    const regex2 = /^[6-9][0-9]{9}$/;

    if (fieldName === "first") {
      const valid1 = regex.test(fieldValue);
      setFormErrors((prevErrors) => ({
        ...prevErrors,
        [fieldName]: valid1 ? "" : "Enter a valid name",
      }));
    } else if (fieldName === "last") {
      const valid2 = regex.test(fieldValue);
      setFormErrors((prevErrors) => ({
        ...prevErrors,
        [fieldName]: valid2 ? "" : "Enter a valid name",
      }));
    } else {
      const valid3 = regex2.test(fieldValue);
      setFormErrors((prevErrors) => ({
        ...prevErrors,
        [fieldName]: valid3
          ? ""
          : `${fieldName} must be a 10 digit no starting with either 6,7,8 or 9.`,
      }));
    }
  };
  const validateForm = () => {
    const { name, mobile } = participantDetails;
    const { first, last } = name;
    const { number } = mobile;
    if (
      formErrors.first === "" &&
      formErrors.last === "" &&
      formErrors.mobile === "" &&
      first &&
      last &&
      number
    )
      return true;
    else return false;
  };
  return (
    <>
      <div className="container pt-3">
        <button
          className="btn fs-5 d-flex align-items-center justify-content-center border border-0"
          onClick={() => {
            navigate(-1);
          }}
          style={{ backgroundColor: "#003D59", color: "#FFFFFF" }}
        >
          <ArrowLeft></ArrowLeft>
        </button>
        <div className="container d-flex align-items-center flex-column pt-3">
          <div className="card w-100 shadow mb-5">
            <div
              className="card-header"
              style={{ backgroundColor: "#003D59", color: "#FFF" }}
            >
              <h6 className="display-6">Tour Details</h6>
            </div>
            <div
              className="card-body"
              style={{ backgroundColor: "white", color: "black" }}
            >
              <div className="row mb-2">
                <div className="col-sm-6 pb-2">
                  <div className="form-floating">
                    <input
                      className="form-control"
                      type="text"
                      placeholder="Tour Name"
                      name="tourName"
                      value={batchDetails?.tour?.name}
                      readOnly
                    />
                    <label htmlFor="tourName" className="form-label">
                      Tour Name
                    </label>
                  </div>
                </div>
                <div className="col-sm-6">
                  <div className="form-floating">
                    <input
                      className="form-control"
                      type="text"
                      placeholder="Days"
                      name="days"
                      value={batchDetails?.tour?.days}
                      readOnly
                    />
                    <label htmlFor="days" className="form-label">
                      No. of Days
                    </label>
                  </div>
                </div>
              </div>
              <div className="row mb-2">
                <div className="col-sm-6 pb-2">
                  <div className="form-floating">
                    <input
                      className="form-control"
                      type="text"
                      placeholder="Nights"
                      name="nights"
                      value={batchDetails?.tour?.nights}
                      readOnly
                    />
                    <label htmlFor="nights" className="form-label">
                      No. of Nights
                    </label>
                  </div>
                </div>
                <div className="col-sm-6">
                  <div className="form-floating">
                    <input
                      className="form-control"
                      type="text"
                      placeholder="Per Person Cost"
                      name="perPersonCost"
                      value={batchDetails?.perParticipantCost}
                      readOnly
                    />
                    <label htmlFor="perPersonCost" className="form-label">
                      Per Person Cost
                    </label>
                  </div>
                </div>
              </div>
              <div className="row mb-2">
                <div className="col-sm-6 pb-2">
                  <div className="form-floating">
                    <input
                      className="form-control"
                      type="text"
                      placeholder="Mode of Transport"
                      name="mode"
                      value={batchDetails?.tour?.mode}
                      readOnly
                    />
                    <label htmlFor="mode" className="form-label">
                      Mode of Transport
                    </label>
                  </div>
                </div>
                <div className="col-sm-6">
                  <div className="form-floating">
                    <input
                      className="form-control"
                      type="text"
                      placeholder="Difficulty Level"
                      name="difficulty"
                      value={batchDetails?.tour?.difficultyLevel}
                      readOnly
                    />
                    <label htmlFor="difficulty" className="form-label">
                      Difficulty
                    </label>
                  </div>
                </div>
              </div>
              <div className="row mb-2">
                <div className="col-sm-6 pb-2">
                  <div className="form-floating">
                    <input
                      className="form-control"
                      type="text"
                      placeholder="Start Date"
                      name="startDate"
                      value={batchDetails?.startDate}
                      readOnly
                    />
                    <label htmlFor="startDate" className="form-label">
                      Start Date
                    </label>
                  </div>
                </div>
                <div className="col-sm-6">
                  <div className="form-floating">
                    <input
                      className="form-control"
                      type="text"
                      placeholder="Seats Available"
                      name="seatsAvailable"
                      value={batchDetails?.availableSeats}
                      readOnly
                    />
                    <label htmlFor="availableSeats" className="form-label">
                      Seats Available
                    </label>
                  </div>
                </div>
              </div>
              <div className="row mb-2">
                <div className="col-sm-6 pb-2">
                  <div className="form-floating">
                    <input
                      className="form-control"
                      type="text"
                      placeholder="End Date"
                      name="endDate"
                      value={batchDetails?.endDate}
                      readOnly
                    />
                    <label htmlFor="endDate" className="form-label">
                      End Date
                    </label>
                  </div>
                </div>
                <div className="col-sm-6">
                  <div className="form-floating">
                    <input
                      className="form-control"
                      type="text"
                      placeholder="status"
                      name="status"
                      value={batchDetails?.status}
                      readOnly
                    />
                    <label htmlFor="availableSeats" className="form-label">
                      Status
                    </label>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="card w-100 shadow">
            <div
              className="card-header"
              style={{ backgroundColor: "#003D59", color: "#FFF" }}
            >
              <h6 className="display-6">Enter Traveller Details</h6>
            </div>
            <div
              className="card-body"
              style={{ backgroundColor: "white", color: "black" }}
            >
              <form
                id="participantForm"
                className="needs-validation"
                noValidate
              >
                <div className="row">
                  <div className="col">
                    <div className="pb-2">
                      <label htmlFor="first" className="form-label">
                        Firstname
                      </label>
                      <input
                        type="text"
                        name="first"
                        placeholder="Firstname"
                        value={participantDetails.name.first}
                        onChange={handleChange}
                        className="form-control"
                        required
                      />
                      <p style={{ color: "red", margin: "5px 0 0 0" }}>
                        {formErrors.first}
                      </p>
                    </div>
                    <div className="pb-2">
                      <label htmlFor="last" className="form-label">
                        Lastname
                      </label>
                      <input
                        type="text"
                        name="last"
                        placeholder="Lastname"
                        value={participantDetails.name.last}
                        onChange={handleChange}
                        className="form-control"
                        required
                      />
                      <p style={{ color: "red", margin: "5px 0 0 0" }}>
                        {formErrors.last}
                      </p>
                    </div>
                    <div className="pb-2">
                      <label htmlFor="mobile" className="form-label">
                        Mobile Number
                      </label>
                      <input
                        type="tel"
                        name="mobile"
                        placeholder="Mobile Number"
                        value={participantDetails.mobile.number}
                        onChange={handleChange}
                        className="form-control"
                        required
                      />
                      <p style={{ color: "red", margin: "5px 0 0 0" }}>
                        {formErrors.mobile}
                      </p>
                    </div>
                  </div>
                </div>
                <div className="row mt-3 d-flex justify-content-center">
                  <button
                    className="btn btn-primary w-25"
                    type="submit"
                    style={{backgroundColor:"#003D59"}}
                    onClick={handleParticipant}
                  >
                    Add Traveller
                  </button>
                </div>
              </form>
              <div className="container mt-4 d-flex flex-column align-items-center">
                {participantList.length !== 0 && (
                  <table className="table table-striped table-hover table-primary caption-top">
                    <caption>List of Participants</caption>
                    <thead>
                      <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Mobile Number</th>
                        <th>Options</th>
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
                              <td>
                                <button
                                  className="btn btn-danger btn-sm"
                                  onClick={() => handleDelete(participant)}
                                >
                                  Delete
                                </button>
                              </td>
                            </tr>
                          </>
                        );
                      })}
                    </tbody>
                  </table>
                )}

                {participantList.length !== 0 && (
                  <Link
                    className="btn btn-primary w-25"
                    style={{backgroundColor:"#003D59"}}
                    to={`/tours/${tourId}/bookTour/payments`}
                    state={{
                      participantList: participantList,
                      batchDetails: batchDetails,
                      user: user,
                    }}
                  >
                    Proceed to Payments
                  </Link>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default BookingForm;
