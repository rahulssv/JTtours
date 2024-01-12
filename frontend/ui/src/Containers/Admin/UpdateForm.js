import React from "react";
import { useState,useEffect } from "react";
import { Form, Row, Col, Button } from "react-bootstrap";
import { useNavigate, useLocation } from "react-router-dom";
import { ToastContainer ,toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { ArrowLeft } from "react-bootstrap-icons";
import { getMetaData, updateTour } from "../../api";
import { validateTourName } from "../../utils/Validation";

function UpdateForm() {
  const location = useLocation();
  const navigate = useNavigate();
  const [modes, setModes] = useState([]);
  const [roomTypes, setRoomTypes] = useState([]);
  const [difficultyLevels, setDifficultyLevels] = useState([]);

  useEffect(() => {
    getMetaData("modes", setModes);
    getMetaData("roomtypes", setRoomTypes);
    getMetaData("difficulties", setDifficultyLevels);
  }, []);
  const tour = location.state;
  const [formData, setFormData] = useState(location.state);
  const [formErrors, setFormErrors] = useState({});

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    if (name === "name" || name === "startAt" || name === "endAt") {
      validateTourName(name, value, setFormErrors, formData);
    }
    if (name === "dayPlans") {
      const dayPlans = [...formData.itinerary.dayPlans];
      const index = parseInt(e.target.getAttribute("data-index"));
      const dataName = e.target.getAttribute("data-name");
      if (
        dataName === "hotelName" ||
        dataName === "address" ||
        dataName === "roomType"
      ) {
        dayPlans[index].accomodation = {
          ...dayPlans[index].accomodation,
          [dataName]: value,
        };
      } else {
        dayPlans[index] = { ...dayPlans[index], [dataName]: value };
      }
      setFormData({
        ...formData,
        itinerary: {
          ...formData.itinerary,
          dayPlans,
        },
      });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const adddayPlans = () => {
    const dayPlans = [...formData.itinerary.dayPlans];
    const day = dayPlans.length;

    dayPlans.push({
      dayCount: day + 1,
      place: "",
      distance: "",
      activity: "",
      accomodation: {
        hotelName: "",
        address: "",
        roomType: "",
      },
    });
    setFormData({
      ...formData,
      itinerary: { ...formData.itinerary, dayPlans },
    });
  };
  const validateForm = () => {
    const { difficultyLevel, mode, startAt, endAt, itinerary } = formData;
    const { dayPlans } = itinerary;
    for (var value in dayPlans) {
      const { place,activity,accomodation ,distance} = dayPlans[value];
      const { hotelName,address,roomType } = accomodation;

      if (roomType === "" ||distance===""|| place === "" || activity==="" || hotelName===""||address==="") {
        toast.error("Please enter all the details of itinerary");
        return false;
      }
    }
    return difficultyLevel && mode && startAt && endAt;
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      updateTour(formData.id, formData, navigate);
    } else {
      toast.error("Something Wrong");
    }
    
  };
  return (
    <>
      <div
        className="container py-4 "
        style={{ maxWidth: "800px", margin: "auto" }}
      >
        <Form className="p-5 border shadow  mb-5 bg-white rounded">
          <div className="container-fluid d-flex p-0 m-0 mb-5">
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
              <h2>Update Tour Details</h2>
            </div>
          </div>
          <Form.Group className="mb-3">
            <Form.Label>Tour Name</Form.Label>
            <Form.Control
              type="text"
              name="name"
              onChange={handleFormChange}
              value={formData.name}
              placeholder="Tour Name"
              required={true}
            />
            <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
              {formErrors.name}
            </p>
          </Form.Group>
          <Row>
            <Col>
              <Form.Group className="mb-3">
                <Form.Label>Start At:</Form.Label>
                <Form.Control
                  type="text"
                  name="startAt"
                  onChange={handleFormChange}
                  value={formData.startAt}
                  placeholder="Start At"
                  required={true}
                />
                <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
                  {formErrors.startAt}
                </p>
              </Form.Group>
            </Col>
            <Col>
              <Form.Group className="mb-3">
                <Form.Label>End At:</Form.Label>
                <Form.Control
                  type="text"
                  name="endAt"
                  onChange={handleFormChange}
                  value={formData.endAt}
                  placeholder="End At"
                required={true}

                />
                <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
                {formErrors.endAt}
              </p>
              </Form.Group>
            </Col>
          </Row>
          <Form.Group>
            <Form.Label>Mode</Form.Label>
            <Form.Select
              className="mb-3"
              name="mode"
              onChange={handleFormChange}
              defaultValue={tour.mode}
              required={true}

            >
               {modes.map((mode) => {
              return (
                <option value={mode.optionValue}>
                  {mode.optionDisplayString}
                </option>
              );
            })}
            </Form.Select>
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Difficulty</Form.Label>
            <Form.Select
              name="difficultyLevel"
              className="mb-3"
              onChange={handleFormChange}
              defaultValue={tour.difficultyLevel}
            required={true}

            >
              {difficultyLevels.map((difficultyLevel) => {
              return (
                <option value={difficultyLevel.optionValue}>
                  {difficultyLevel.optionDisplayString}
                </option>
              );
            })}
            </Form.Select>
          </Form.Group>

          <Form.Label>Itinerary:</Form.Label>
          {formData.itinerary.dayPlans.map((dailyPlan, indx) => {
            return (
              <>
                <fieldset className="border p-2" key={indx}>
                  <legend className="float-none w-auto">
                    DayCount: {indx + 1}
                  </legend>
                  <Row>
                    <Col>
                      <Form.Control
                        type="text"
                        // onKeyPress={(event) => {
                        //   if (!/[a-zA-Z]/.test(event.key)) {
                        //     event.preventDefault();
                        //   }
                        // }}
                        name="dayPlans"
                        data-index={indx}
                        data-name="place"
                        value={dailyPlan.place}
                        onChange={handleFormChange}
                        placeholder="Place"
                        required={true}
                      />
                    </Col>
                    <Col>
                      <Form.Control
                        type="text"
                        // onKeyPress={(event) => {
                        //   if (!/^[a-zA-Z0-9\s,'.-]+$/.test(event.key)) {
                        //     event.preventDefault();
                        //   }
                        // }}
                        name="dayPlans"
                        data-index={indx}
                        data-name="activity"
                        value={dailyPlan.activity}
                        onChange={handleFormChange}
                        placeholder="Activity"
                        required={true}
                      />
                    </Col>
                    <Col>
                      <Form.Control
                        type="number"
                        name="dayPlans"
                        // onKeyPress={(event) => {
                        //   if (!/[0-9]/.test(event.key)) {
                        //     event.preventDefault();
                        //   }
                        // }}
                        data-index={indx}
                        data-name="distance"
                        value={dailyPlan.distance}
                        onChange={handleFormChange}
                        placeholder="Distance to cover"
                        required={true}
                      />
                    </Col>
                  </Row>
                  <Form.Label>Accomodation :</Form.Label>
                  <Row>
                    <Col>
                      <Form.Control
                        type="text"
                        name="dayPlans"
                        // onKeyPress={(event) => {
                        //   if (!/^[a-zA-Z0-9\s'-]+$/.test(event.key)) {
                        //     event.preventDefault();
                        //   }
                        // }}
                        data-index={indx}
                        data-name="hotelName"
                        value={dailyPlan.accomodation.hotelName}
                        onChange={handleFormChange}
                        placeholder="Hotel name"
                        required={true}
                      />
                    </Col>
                    <Col>
                      <Form.Control
                        type="text"
                        name="dayPlans"
                        // onKeyPress={(event) => {
                        //   if (!/^[a-zA-Z0-9\s,'.-]+$/.test(event.key)) {
                        //     event.preventDefault();
                        //   }
                        // }}
                        data-index={indx}
                        data-name="address"
                        value={dailyPlan.accomodation.address}
                        onChange={handleFormChange}
                        placeholder="Address"
                        required={true}
                      />
                    </Col>
                    <Col>
                      <Form.Select
                        name="dayPlans"
                        data-index={indx}
                        data-name="roomType"
                        area-aria-label="Select Room Type"
                        value={dailyPlan.accomodation.roomType}
                        onChange={handleFormChange}
                        required={true}
                      >
                         {roomTypes.map((room) => {
                        return (
                          <option value={room.optionValue}>
                            {room.optionDisplayString}
                          </option>
                        );
                      })}
                      </Form.Select>
                    </Col>
                  </Row>
                </fieldset>
                <br />
              </>
            );
          })}
          <Button
            style={{ backgroundColor: "#003D59", color: "#FFFFFF" }}
            onClick={adddayPlans}
          >
            Add Daily Plans
          </Button>
          <br />
          <br />
          <Button
            type="submit"
            style={{ backgroundColor: "#003D59", color: "#FFFFFF" }}
            onClick={handleSubmit}
          >
            Submit
          </Button>
          <ToastContainer></ToastContainer>
        </Form>
      </div>
      <pre>{JSON.stringify(formData,null,2)}</pre>
    </>
  );
}

export default UpdateForm;
