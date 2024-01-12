import React, { useEffect, useState } from "react";
import { Button, Row, Col, Form } from "react-bootstrap";
import NavList from "../../Components/NavList/NavList";
import { BreadcrumbsItem } from "react-breadcrumbs-dynamic";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { ArrowLeft } from "react-bootstrap-icons";
import { addTour, getMetaData } from "../../api";
import { validateTourName } from "../../utils/Validation";

function AddTour() {
  const navigate = useNavigate();
  const [modes, setModes] = useState([]);
  const [roomTypes, setRoomTypes] = useState([]);
  const [difficultyLevels, setDifficultyLevels] = useState([]);

  useEffect(() => {
    getMetaData("modes", setModes);
    getMetaData("roomtypes", setRoomTypes);
    getMetaData("difficulties", setDifficultyLevels);
  }, []);

  const [formData, setFormData] = useState({
    name: "",
    mode: "WALK",
    itinerary: {
      dayPlans: [],
    },

    difficultyLevel: "HIGH",
    startAt: "",
    endAt: "",
  });
  const [formErrors, setFormErrors] = useState({});

  const handleChange = (e) => {
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

  const addDailyPlan = () => {
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
        roomType: "TWIN_BED",
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
      const { accomodation } = dayPlans[value];
      const { roomType } = accomodation;
      if (roomType === "") {
        toast.error("Please select the room type");
        return;
      }
    }
    return difficultyLevel && mode && startAt && endAt && dayPlans.length !== 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (validateForm()) {
      // formData.id = new Date().getTime().toString();
      addTour(formData, navigate);
    } else {
      toast.error("Please fill all the required details including itinerary");
    }
  };

  return (
    <div style={{ maxWidth: "800px", margin: "auto" }}>
      <BreadcrumbsItem to="/tours"></BreadcrumbsItem>
      <div style={{ padding: "1.5rem 3.5rem 0 3rem" }}>
        <NavList></NavList>
      </div>
      <Form
        onSubmit={handleSubmit}
        method="POST"
        className="p-5 border shadow  mb-5 bg-white rounded"
      >
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
            <h2>Enter Tour Details</h2>
          </div>
        </div>
        <Form.Group className="mb-4">
          <Form.Label>Tour Name : </Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter tour name"
            name="name"
            value={formData?.name}
            onChange={handleChange}
            required={true}
          ></Form.Control>
          <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
            {formErrors.name}
          </p>
        </Form.Group>
        <Row>
          <Col>
            <Form.Group className="mb-4">
              <Form.Label>Start At : </Form.Label>
              <Form.Control
                type="text"
                placeholder="Start At"
                name="startAt"
                value={formData?.startAt}
                onChange={handleChange}
                required={true}
              ></Form.Control>
              <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
                {formErrors.startAt}
              </p>
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-4">
              <Form.Label>End At : </Form.Label>
              <Form.Control
                type="text"
                placeholder="End At"
                name="endAt"
                value={formData?.endAt}
                onChange={handleChange}
                required={true}
              ></Form.Control>
              <p id="numError" style={{ color: "red", margin: "5px 0 0 0" }}>
                {formErrors.endAt}
              </p>
            </Form.Group>
          </Col>
        </Row>
        <Form.Group className="mb-4">
          <Form.Label>Mode:</Form.Label>
          <Form.Select
            name="mode"
            onChange={handleChange}
            area-aria-label="Select Tour mode"
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
        <Form.Group className="mb-4">
          <Form.Label>Difficulty:</Form.Label>
          <Form.Select
            name="difficultyLevel"
            onChange={handleChange}
            area-aria-label="Select Difficulty mode"
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
        <br />
        {formData.itinerary.dayPlans.map((plan, indx) => {
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
                      name="dayPlans"
                      data-index={indx}
                      data-name="place"
                      value={plan.place}
                      onChange={handleChange}
                      placeholder="Place"
                      required={true}
                    />
                  </Col>
                  <Col>
                    <Form.Control
                      type="text"
                      name="dayPlans"
                      data-index={indx}
                      data-name="activity"
                      value={plan.activity}
                      onChange={handleChange}
                      placeholder="Activity"
                      required={true}
                    />
                  </Col>
                  <Col>
                    <Form.Control
                      type="number"
                      name="dayPlans"
                      data-index={indx}
                      data-name="distance"
                      value={plan.distance}
                      onChange={handleChange}
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
                      data-index={indx}
                      data-name="hotelName"
                      value={plan.accomodation.hotelName}
                      onChange={handleChange}
                      placeholder="Hotel name"
                      required={true}
                    />
                  </Col>
                  <Col>
                    <Form.Control
                      type="text"
                      name="dayPlans"
                      data-index={indx}
                      data-name="address"
                      value={plan.accomodation.address}
                      onChange={handleChange}
                      placeholder="Address"
                      required={true}
                    />
                  </Col>
                  <Col>
                    <Form.Select
                      name="dayPlans"
                      data-index={indx}
                      data-name="roomType"
                      onChange={handleChange}
                      area-aria-label="Select Room Type"
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
          style={{
            backgroundColor: "#003D59",
            color: "#FFFFFF",
            outline: "none",
          }}
          onClick={addDailyPlan}
        >
          Add Daily Plans
        </Button>{" "}
        <br />
        <br />
        <Button
          style={{ backgroundColor: "#003D59", color: "#FFFFFF" }}
          type="submit"
          className=""
        >
          Submit
        </Button>
        <ToastContainer />
      </Form>
      {/* <pre>{JSON.stringify(formData,null,2)}</pre> */}
    </div>
  );
}

export default AddTour;
