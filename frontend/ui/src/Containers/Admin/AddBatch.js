import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { ArrowLeft } from "react-bootstrap-icons";
import "./AddBatch.css";
import { addBatch } from "../../api";
import { validateText, validateNo, validateDate } from "../../utils/Validation";
import { toast } from "react-toastify";

export default function AddBatch() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [formData, setFormData] = useState({
    startDate: "",
    capacity: "",
    perParticipantCost: "",
    guide: {
      name: {
        first: "",
        last: "",
      },
      mobile: {
        number: "",
      },
    },
  });
  const [formErrors, setFormErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "capacity" || name === "perParticipantCost") {
      validateNo(name, value, setFormErrors);
    }
    if (name === "startDate") {
      validateDate(name, value, setFormErrors);
    }
    if (name === "guide") {
      let dataName = e.target.getAttribute("data-name");
      validateText(dataName, value, setFormErrors);
      if (dataName === "guideFirstName") {
        setFormData({
          ...formData,
          [name]: {
            ...formData.guide,
            name: { ...formData.guide.name, first: value },
          },
        });
      }
      if (dataName === "guideLastName") {
        setFormData({
          ...formData,
          [name]: {
            ...formData.guide,
            name: { ...formData.guide.name, last: value },
          },
        });
      }
      if (dataName === "guideMobileNumber") {
        setFormData({
          ...formData,
          [name]: {
            ...formData.guide,
            mobile: { ...formData.guide.mobile, number: value },
          },
        });
      }
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };
  const validateForm = () => {
    const { startDate, capacity, perParticipantCost, guide } = formData;
    const { first, last } = guide.name;
    const { number } = guide.mobile;
    return (
      startDate &&
      capacity &&
      perParticipantCost &&
      first &&
      last &&
      number &&
      formErrors.startDate === "" &&
      formErrors.capacity === "" &&
      formErrors.perParticipantCost === "" &&
      formErrors.guideFirstName === "" &&
      formErrors.guideLastName === "" &&
      formErrors.guideMobileNumber === ""
    );
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      addBatch(formData, id, navigate);
    } else {
      toast.error(
        "Please enter all the details and it must be in correct manner"
      );
    }
  };
  return (
    <>
      <div
        className="container-fluid batch mt-4 p-4 rounded shadow"
        style={{ background: "white" }}
      >
        <div className="container-fluid d-flex p-0 m-0 mb-4">
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
            <h2>Enter Batch Details</h2>
          </div>
        </div>
        <form onSubmit={handleSubmit} method="POST" style={{ padding: "10px" }}>
          <div className="form-group">
            <label htmlFor="startDate" className="label">
              Start Date
            </label>
            <input
              type="date"
              className="form-control"
              id="startDate"
              name="startDate"
              value={formData.startDate}
              onChange={handleChange}
              placeholder="Enter start date"
              required
            />
            <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
              {formErrors.startDate}
            </p>
          </div>
          <div className="form-group">
            <label htmlFor="capacity" className="label">
              Capacity
            </label>
            <input
              type="number"
              className="form-control"
              onKeyPress={(event) => {
                if (!/[0-9]/.test(event.key)) {
                  event.preventDefault();
                }
              }}
              id="capacity"
              name="capacity"
              placeholder="Enter Capacity"
              value={formData.capacity}
              onChange={handleChange}
              required
            />
            <p id="error" style={{ color: "red", margin: "5px 0 0 0" }}>
              {formErrors.capacity}
            </p>
          </div>
          <div className="form-group">
            <label htmlFor="cost" className="label">
              Cost per Person
            </label>
            <input
              type="number"
              className="form-control"
              onKeyPress={(event) => {
                if (!/[0-9]/.test(event.key)) {
                  event.preventDefault();
                }
              }}
              id="cost"
              name="perParticipantCost"
              placeholder="Enter Cost"
              value={formData?.perParticipantCost}
              onChange={handleChange}
              required
            />
            <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
              {formErrors.perParticipantCost}
            </p>
          </div>
          <div className="form-group">
            <label htmlFor="startDate" className="label">
              <h3>Guide Details</h3>
            </label>
            <div className="row py-1">
              <div className="col">
                <label htmlFor="guideFirstName" className="label">
                  Firstname
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="guideFirstName"
                  name="guide"
                  data-name="guideFirstName"
                  value={formData.guide.name.first}
                  onChange={handleChange}
                  placeholder="Firstname"
                  required
                />
                <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
                  {formErrors.guideFirstName}
                </p>
              </div>
            </div>
            <div className="row py-3">
              <div className="col">
                <label htmlFor="guideLastName" className="label">
                  Lastname
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="guideLastName"
                  name="guide"
                  data-name="guideLastName"
                  value={formData.guide.name.last}
                  onChange={handleChange}
                  placeholder="Lastname"
                  required
                />
                <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
                  {formErrors.guideLastName}
                </p>
              </div>
            </div>
            <div className="row py-1">
              <div className="col">
                <label htmlFor="guideMobileNumber" className="label">
                  Mobile Number
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="guideMobileNumber"
                  name="guide"
                  data-name="guideMobileNumber"
                  value={formData.guide.mobile.number}
                  onChange={handleChange}
                  placeholder="Mobile Number"
                  required
                />
                <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
                  {formErrors.guideMobileNumber}
                </p>
              </div>
            </div>
          </div>
          <div className="form-group text-center my-1 py-3">
            <button
              type="submit"
              className="btn btnForm rounded-pill fs-5"
              style={{ color: "#FFFFFF", backgroundColor: "#003D59" }}
            >
              Submit
            </button>
          </div>
        </form>
      </div>
    </>
  );
}
