import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import "./Filter.css";
import { filterTour, filterTourByDay, filterTourByDifficulty, filterTourByMode, filterTourByPlace, getMetaData } from "../../api";
// import { filterTour } from "../../api";

function Filter({ setTourData }) {
  const [criteria, setCriteria] = useState("None");
  const [modes, setModes] = useState();
  const [difficulty, setDifficulty] = useState();
  const [filter, setFilter] = useState({
    place: {
      startAt: "",
      endAt: "",
    },
    mode: "",
    difficultyLevel: "",
    day: {
      minDays: 0,
      maxDays: 0,
    },
  });
  useEffect(() => {
    getMetaData("modes", setModes);
    getMetaData("difficulties", setDifficulty);
  }, []);
  const handleCriteriaChange = (e) => {
    setCriteria(e.target.value);
  };
  const handleFormChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    const dataName = e.target.getAttribute("dataName");
    if (dataName === "place") {
      setFilter({ ...filter, place: { ...filter.place, [name]: value } });
    }
    if (dataName === "mode") {
      setFilter({ ...filter, [name]: value });
    }
    if (dataName === "difficultyLevel") {
      setFilter({ ...filter, [name]: value });
    }
    if (dataName === "day") {
      setFilter({ ...filter, day: { ...filter.day, [name]: value } });
    }
  };
  const handleFormSubmit = (e) => {
    e.preventDefault();
    if(criteria === "day") {
      let minDay = filter.day.minDays;
      let maxDay = filter.day.maxDays;
      if(minDay > maxDay) {
        maxDay = minDay;
      }
       
      filterTourByDay(minDay,maxDay, setTourData);
    } else if(criteria ==="mode") {
      filterTourByMode(filter.mode, setTourData);
    } else if(criteria === "place") {
      filterTourByPlace(filter.place.startAt,filter.place.endAt, setTourData);
    } else if(criteria === "difficultyLevel") {
      filterTourByDifficulty(filter.difficultyLevel, setTourData);
    }
 
  };
  return (
    <>
      <div className="accordion" id="filter">
        <div className="accordion-item">
          <h2 className="accordion-header" id="filterHeader">
            <button
              className="accordion-button collapsed"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#collapseFilter"
              aria-expanded="false"
              aria-controls="collapseFilter"
            >
              Filters
            </button>
          </h2>
          <div
            id="collapseFilter"
            className="accordion-collapse collapse"
            aria-labelledby="filterHeader"
            data-bs-parent="#filter"
          >
            <div className="accordion-body">
              <label htmlFor="filterCriteria" className="form-label">
                Filter Criteria:
              </label>
              <select
                id="filterCriteria"
                name="filterCriteria"
                onChange={handleCriteriaChange}
                className="form-select"
                aria-label="Filter Criteria Select"
              >
                <option value="None">None</option>
                <option value="place">Place</option>
                <option value="mode">Mode</option>
                <option value="difficultyLevel">Difficulty</option>
                <option value="day">Day</option>
              </select>
              <div className="container mt-4">
                <form>
                  {criteria === "place" && (
                    <>
                      <div className="row">
                        <div className="mb-2 col-sm-6">
                          <label htmlFor="startAt" className="form-label">
                            Source
                          </label>
                          <input
                            type="text"
                            name="startAt"
                            id="startAt"
                            dataName="place"
                            className="form-control"
                            value={filter.place.startAt}
                            onChange={handleFormChange}
                          />
                        </div>
                        <div className="mb-2 col-sm-6">
                          <label htmlFor="endAt" className="form-label">
                            Destination
                          </label>
                          <input
                            type="text"
                            name="endAt"
                            id="endAt"
                            dataName="place"
                            className="form-control"
                            value={filter.place.endAt}
                            onChange={handleFormChange}
                          />
                        </div>
                      </div>
                    </>
                  )}
                  {criteria === "mode" && (
                    <>
                      <select
                        name="mode"
                        id="mode"
                        dataName="mode"
                        onChange={handleFormChange}
                        className="form-select mb-2"
                      >
                        {modes.map((mode) => {
                          return <>
                            <option value={mode.optionValue}>
                              {mode.optionDisplayString}
                            </option>
                          </>;
                        })}
                      </select>
                    </>
                  )}
                  {criteria === "difficultyLevel" && (
                    <>
                      <select
                        name="difficultyLevel"
                        id="difficultyLevel"
                        className="form-select mb-2"
                        onChange={handleFormChange}
                        dataName="difficultyLevel"
                      >
                        {difficulty.map((difficulty) => {
                          return <>
                            <option value={difficulty.optionValue}>
                              {difficulty.optionDisplayString}
                            </option>
                          </>;
                        })}
                      </select>
                    </>
                  )}
                  {criteria === "day" && (
                    <>
                      <div className="row">
                        <div className="mb-2 col-sm-6">
                          <label htmlFor="minDays" className="form-label">
                            Minimum Days
                          </label>
                          <input
                            type="number"
                            name="minDays"
                            id="minDays"
                            dataName="day"
                            className="form-control"
                            value={filter.day.minDays}
                            onChange={handleFormChange}
                          />
                        </div>
                        <div className="mb-2 col-sm-6">
                          <label htmlFor="maxDays" className="form-label">
                            Maximum Days
                          </label>
                          <input
                            type="number"
                            name="maxDays"
                            id="maxDays"
                            dataName="day"
                            className="form-control"
                            value={filter.day.maxDays}
                            onChange={handleFormChange}
                          />
                        </div>
                      </div>
                    </>
                  )}
                  {criteria !== "None" && (
                    <div className="container d-flex justify-content-center">
                      <button
                        className="btn btn-primary w-25"
                        type="submit"
                        onClick={handleFormSubmit}
                        style={{ backgroundColor: "#003D59", color: "#FFFFFF" }}
                      >
                        Apply
                      </button>
                    </div>
                  )}
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Filter;
