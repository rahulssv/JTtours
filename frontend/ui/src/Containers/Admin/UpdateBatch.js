import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { ArrowLeft } from "react-bootstrap-icons";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { validateNo, validateText, validateDate } from "../../utils/Validation";

import { updateBatch } from "../../api";
import { useEffect } from "react";

export default function UpdateBatch() {
  const navigate = useNavigate();
  const location = useLocation();
  const batch = location.state;
  const [formData, setFormData] = useState(batch);
  // for all errors handling using UseState Hooks
  const [formErrors, setFormErrors] = useState({});
  useEffect(() => {
    validateNo("capacity", batch.capacity, setFormErrors);
    validateNo("perParticipantCost", batch.perParticipantCost, setFormErrors);
    validateDate("startDate", batch.startDate, setFormErrors);
    validateText("guideFirstName", batch?.guide?.name?.first, setFormErrors);
    validateText("guideLastName", batch?.guide?.name?.last, setFormErrors);
    validateText("guideMobileNumber", batch?.guide?.mobile?.number, setFormErrors);
  }, []);
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
            name: { ...formData?.guide?.name, first: value },
          },
        });
      }
      if (dataName === "guideLastName") {
        setFormData({
          ...formData,
          [name]: {
            ...formData.guide,
            name: { ...formData?.guide?.name, last: value },
          },
        });
      }
      if (dataName === "guideMobileNumber") {
        setFormData({
          ...formData,
          [name]: {
            ...formData.guide,
            mobile: { ...formData?.guide?.mobile, number: value },
          },
        });
      }
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };
  const validateForm = () => {
    const { startDate, capacity, perParticipantCost, guide, status } = formData;
    const { first, last } = guide?.name;
    const { number } = guide?.mobile;
    return (
      startDate &&
      capacity &&
      perParticipantCost &&
      first &&
      last &&
      number &&
      status &&
      formErrors?.startDate === "" &&
      formErrors?.capacity === "" &&
      formErrors?.perParticipantCost === "" &&
      formErrors?.guideFirstName === "" &&
      formErrors?.guideLastName === "" &&
      formErrors?.guideMobileNumber === ""
    );
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      updateBatch(
        `${batch.tourID}/batch/${batch.id}?tourID=${batch.tourID}`,
        formData,
        navigate
      );
      // updateBatch(batch.id,formData,navigate);
    } else {
      toast.error(
        "Please fill all the details correctly and in correct format"
      );
    }
  };
  return (
    <>
      <div
        className="container-fluid batch mt-3 shadow"
        style={{ background: "white" }}
      >
        <div className="container-fluid d-flex p-0 m-0">
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
            <h2>Update Batch Details</h2>
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
              placeholder="Start Date"
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
            <span id="error" style={{ color: "red", margin: "5px 0 0 0" }}>
              {formErrors.capacity}
            </span>
          </div>
          <div className="form-group">
            <label htmlFor="perParticipantCost" className="label">
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
              value={formData.perParticipantCost}
              onChange={handleChange}
              required
            />
            <span id="error" style={{ color: "red", margin: "5px 0 0 0" }}>
              {formErrors.perParticipantCost}
            </span>
          </div>
          <div className="form-group">
            <label htmlFor="status" className="label">
              Status
            </label>
            <select
              name="status"
              className="form-select"
              // value={formData?.status}
              onChange={handleChange}
              required
            >
              <option>Select Status</option>
              <option value="ACTIVE">Active</option>
              <option value="FULL">Full</option>
              <option value="IN PROGRESS">In Progress</option>
              <option value="CANCELLED">Cancelled</option>
            </select>
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
                  value={formData?.guide?.name?.first}
                  onChange={handleChange}
                  placeholder="Firstname"
                  required
                />
                <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
                  {formErrors?.guideFirstName}
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
                  value={formData?.guide?.name?.last}
                  onChange={handleChange}
                  placeholder="Lastname"
                  required
                />
                <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
                  {formErrors?.guideLastName}
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
                  value={formData?.guide?.mobile?.number}
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
              Update
            </button>
          </div>
        </form>
      </div>
    </>
  );
}
