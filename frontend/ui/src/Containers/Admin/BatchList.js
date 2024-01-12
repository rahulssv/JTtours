import React from "react";
import { useState, useEffect } from "react";
import "./BatchList.css";
import { Link } from "react-router-dom";
import { getBatchesByTourId } from "../../api";
import { cancelBatch } from "../../api";

function BatchList({ tourId, isAdmin, user, batchData, setBatchData }) {
  
  useEffect(() => {
    getBatchesByTourId(tourId,setBatchData);
  }, [tourId]);
  const handleBatchCancellation = (batchId) => {
    cancelBatch(
      `/tours/${tourId}/batches?tourID=${String(tourId)}`,
      batchId,
      setBatchData
    );
  };
  return (
    <>
      <table className="table table-hover table-striped table-primary caption-top">
        <caption style={{ fontSize: "22px", padding: "5px", color: "#003D59" }}>
          List of Batches
        </caption>
        <thead className="align-middle">
          <tr>
            <th scope="col">#</th>
            <th scope="col">Start Date</th>
            <th scope="col" className="d-none d-lg-table-cell">End Date</th>
            <th scope="col">Guide Name</th>
            <th scope="col" className="d-none d-lg-table-cell">
              Available Seats
            </th>
            <th scope="col" className="d-none d-lg-table-cell">
              Cost per Participant
            </th>
            <th scope="col" className="d-none d-sm-table-cell">
              Status
            </th>
            <th scope="col">Options</th>
          </tr>
        </thead>
        <tbody className="align-middle table-group-divider">
          {batchData &&
            batchData.map((batch, index) => {
              // console.log(batch)
              return (
                <>
                  <tr key={new Date().getTime().toString()}>
                    <td>{index + 1}</td>
                    <td>{batch.startDate}</td>
                    <td className="d-none d-lg-table-cell">{batch.endDate}</td>
                    <td>
                      {batch?.guide?.name?.first + " " + batch?.guide?.name?.last}
                    </td>
                    <td className="d-none d-lg-table-cell">{batch.availableSeats}</td>
                    <td className="d-none d-lg-table-cell">
                      {batch.perParticipantCost}
                    </td>
                    <td className="d-none d-sm-table-cell">{batch.status}</td>
                    <td>
                      {isAdmin ? (
                        <>
                          <Link
                            className="btn btn-primary btn-sm ms-1"
                            state={batch}
                            to={`/tours/${tourId}/batches/updatebatch`}
                            style={{
                              backgroundColor: "#003D59",
                              color: "#FFFFFF",
                            }}
                          >
                            Update
                          </Link>
                          <button
                            className="btn btn-danger btn-sm ms-1"
                            onClick={() => handleBatchCancellation(batch.id)}
                          >
                            Cancel
                          </button>
                        </>
                      ) : (
                        // <Link
                        //   className="btn btn-primary btn-sm ms-1"
                        //   style={{
                        //     backgroundColor: "#003D59",
                        //     color: "#FFFFFF",
                        //   }}
                        //   state={{ batchDetails: batch, user: user }}
                        //   to={{
                        //     pathname: `/tours/${tourId}/bookTour`,
                        //   }}
                        // >
                        //   Book Now
                        // </Link>
                        <Link
                          className="btn btn-primary btn-sm ms-1"
                          style={{
                            backgroundColor: "#003D59",
                            color: "#FFFFFF",
                          }}
                          state={{ batchDetails: batch, user: user }}
                          to={{
                            pathname: `/batch/${batch?.id}`,
                          }}
                        >
                          View
                        </Link>
                      )}
                    </td>
                  </tr>
                </>
              );
            })}
        </tbody>
      </table>
    </>
  );
}

export default BatchList;
