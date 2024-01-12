import React from "react";
import "./TourList.css";
// import { Trash } from "react-bootstrap-icons";
import { Link } from "react-router-dom";

function TourList(props) {
  return (
    <>{
      props.data ?
        (<table
          id="tourList"
          className="table table-hover table-striped table-primary caption-top"
        >
          <caption>List of Tours</caption>
          <thead className="align-middle">
            <tr>
              <th scope="col">#</th>
              <th scope="col">Name</th>
              <th scope="col" className="d-none d-lg-table-cell">
                Source
              </th>
              <th scope="col" className="d-none d-lg-table-cell">
                Destination
              </th>
              <th scope="col" className="d-none d-md-table-cell">
                Mode of Transport
              </th>
              <th scope="col">Duration</th>
              <th scope="col">Difficulty Level</th>
              {props.isAdmin && <th scope="col">Options</th>}
            </tr>
          </thead>
          <tbody className="align-middle table-group-divider">
            {props.data &&
              props.data.map((dataObject, index) => {
                return (
                  <tr key={dataObject.id}>
                    <th scope="row">{index + 1}</th>
                    <td>
                      <Link
                        state={{ isAdmin: props.isAdmin, user: props.user }}
                        to={{ pathname: `/tours/${dataObject.id}/batches` }}
                      >
                        {dataObject.name}
                      </Link>
                    </td>
                    <td className="d-none d-lg-table-cell">
                      {dataObject.startAt}
                    </td>
                    <td className="d-none d-lg-table-cell">{dataObject.endAt}</td>
                    <td className="d-none d-md-table-cell">{dataObject.mode}</td>
                    <td>
                      {dataObject.days} {dataObject.days > 1 ? "Days" : "Day"}
                    </td>
                    <td>{dataObject.difficultyLevel}</td>
                    {props.isAdmin && (
                      <td>
                        {/* <button
                          className="btn btn-danger btn-sm ms-2"
                          onClick={() => {
                            props.handleDelete(dataObject.id);
                          }}
                        >
                          <Trash></Trash>
                        </button> */}
                        <Link
                          className="btn btn-secondary btn-sm ms-1"
                          to={`/tours/${dataObject.id}/updateTour`}
                          state={dataObject}
                        >
                          Update
                        </Link>
                      </td>
                    )}
                  </tr>
                );
              })}
          </tbody>
        </table>) : (<h2>No Tours Available</h2>)
    }
    </>
  );
}

export default TourList;
