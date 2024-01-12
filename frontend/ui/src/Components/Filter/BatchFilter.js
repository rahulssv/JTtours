import React, { useEffect } from "react";
import { useState } from "react";
import { toast, ToastContainer } from "react-toastify";
import { filterBatchByDate, filterBatchByAvailableSeats, filterBatchByParticipantCost, getMetaData, filterBatchByStatus } from "../../api";

function BatchFilter({ setBatchData, isAdmin }) {
  const [criteria, setCriteria] = useState("None");
  const [status, setStatus] = useState([]);
  const [filter, setFilter] = useState({
    startDate: {
      minDate: "",
      maxDate: "",
    },
    availableSeats: {
      minAvailableSeats: 0,
      maxAvailableSeats: 0,
    },
    perParticipantCost: {
      budget: 0
    },
    status: "ACTIVE"
  });
  useEffect(() => {
    getMetaData("batchstatuses", setStatus);
  }, []);
  const handleCriteriaChange = (e) => {
    setCriteria(e.target.value);
  };
  const handleFormChange = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    const dataName = e.target.getAttribute("dataName");
    if (dataName === "startDate") {
      setFilter({ ...filter, startDate: { ...filter.startDate, [name]: value } });
    }
    if (dataName === "availableSeats") {
      setFilter({
        ...filter,
        availableSeats: { ...filter.availableSeats, [name]: value },
      });
    }
    if (dataName === "perParticipantCost") {
      setFilter({
        ...filter,
        perParticipantCost: { ...filter.perParticipantCost, [name]: value },
      });
    }
    if (dataName === "status") {
      setFilter({ ...filter, [name]: value });
    }
  };
  const handleFilterSubmit = (e) => {
    e.preventDefault();
    if (criteria === "startDate") {
      const maxDate = filter.startDate.maxDate;
      const minDate = filter.startDate.minDate;
      if (maxDate !== "" && minDate !== "") {
        if (filter.startDate.maxDate < filter.startDate.minDate) {
          toast.error("Invalid Dates");
          return;
        }
        filterBatchByDate(filter.startDate.minDate, filter.startDate.maxDate, setBatchData);
      }
      filterBatchByDate(filter.startDate.minDate, filter.startDate.maxDate, setBatchData);
    } 
    else if (criteria === "availableSeats") {
      const minAvailableSeats = filter.availableSeats.minAvailableSeats;
      const maxAvailableSeats = filter.availableSeats.maxAvailableSeats;
      if (minAvailableSeats !== 0 && maxAvailableSeats !== 0) {
        if (maxAvailableSeats < minAvailableSeats) {
          toast.error("Maximum Seats cannot be less than Minimum Seats");
        }
        return;
      }
      filterBatchByAvailableSeats(filter.availableSeats.minAvailableSeats, filter.availableSeats.maxAvailableSeats, setBatchData);
    }
    else if (criteria === "perParticipantCost") {
      const budget = filter.perParticipantCost.budget;
      if (budget <= 0) {
        toast.error("Maximum Cost cannot be less than Minimum Cost");
        return;
      }
      filterBatchByParticipantCost(filter.perParticipantCost.budget, setBatchData);

    }
    else if(criteria === "status") {
      filterBatchByStatus(filter.status, setBatchData);
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
                aria-label="Default select example"
              >
                <option value="None">None</option>
                <option value="startDate">Start Date</option>
                <option value="availableSeats">Available Seats</option>
                <option value="perParticipantCost">Cost</option>
                {isAdmin && <option value="status">Status</option>}
              </select>
              <div className="container mt-4">
                <form id="batchFilterForm">
                  {criteria === "startDate" && (
                    <>
                      <div className="mb-2">
                        <label htmlFor="minDate" className="form-label">
                          Starting On or After
                        </label>
                        <input
                          type="date"
                          name="minDate"
                          id="minDate"
                          dataName="startDate"
                          className="form-control"
                          value={filter.startDate.minDate}
                          onChange={handleFormChange}
                        />
                      </div>
                      <div className="mb-2">
                        <label htmlFor="maxDate" className="form-label">
                          Starting On or Before
                        </label>
                        <input
                          type="date"
                          name="maxDate"
                          id="maxDate"
                          dataName="startDate"
                          className="form-control"
                          value={filter.startDate.maxDate}
                          onChange={handleFormChange}
                        />
                      </div>
                    </>
                  )}
                  {criteria === "availableSeats" && (
                    <>
                      <div className="mb-2">
                        <label htmlFor="minAvailableSeats" className="form-label">
                          Minimum Seats
                        </label>
                        <input
                          type="number"
                          name="minAvailableSeats"
                          id="minAvailableSeats"
                          dataName="availableSeats"
                          className="form-control"
                          value={filter.availableSeats.minAvailableSeats}
                          onChange={handleFormChange}
                        />
                      </div>
                      <div className="mb-2">
                        <label htmlFor="maxAvailableSeats" className="form-label">
                          Maximum Seats
                        </label>
                        <input
                          type="number"
                          name="maxAvailableSeats"
                          id="maxAvailableSeats"
                          dataName="availableSeats"
                          className="form-control"
                          value={filter.availableSeats.maxAvailableSeats}
                          onChange={handleFormChange}
                        />
                      </div>
                    </>
                  )}
                  {criteria === "perParticipantCost" && (
                    <>
                      <div className="mb-2">
                        <label htmlFor="budget" className="form-label">
                          Budget
                        </label>
                        <input
                          type="number"
                          name="budget"
                          id="budget"
                          dataName="perParticipantCost"
                          className="form-control"
                          value={filter.perParticipantCost.budget}
                          onChange={handleFormChange}
                        />
                      </div>
                    </>
                  )}
                  {criteria === "status" && (
                    <>
                      <div className="mb-2">
                        <label htmlFor="status" className="form-label">
                          Status
                        </label>
                        <select
                          id="status"
                          name="status"
                          dataName="status"
                          className="form-select"
                          aria-label="Status Select"
                          onChange={handleFormChange}
                        >
                          {status.map((sts) => {
                            return (
                              <option value={sts.optionValue}>
                                {sts.optionDisplayString}
                              </option>
                            );
                          })}
                        </select>
                      </div>
                    </>
                  )}
                  {criteria !== "None" && (
                    <div className="d-flex justify-content-center">
                      <button
                        className="btn btn-primary w-25"
                        type="submit"
                        onClick={handleFilterSubmit}
                      >
                        Apply
                      </button>
                    </div>
                  )}
                  <ToastContainer></ToastContainer>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default BatchFilter;
