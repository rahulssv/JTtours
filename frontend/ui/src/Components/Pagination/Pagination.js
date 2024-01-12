import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./Pagination.css";

function Pagination() {
  const location = useLocation();
  const navigate = useNavigate();
  const queryParams = new URLSearchParams(location.search);
  const currentPage = Number(queryParams.get("_page")) || 1;
  useEffect(() => {
    queryParams.set("_page", 1);
    queryParams.set("_limit", 5);
    navigate({ search: queryParams.toString() });
  }, []);
  const handlePageChange = (newPage) => {
    if (newPage >= 1) {
      queryParams.set("_page", newPage);
      queryParams.set("_limit", 5);
      navigate({ search: queryParams.toString() });
    }
  };
  return (
    <>
      <nav aria-label="tour pagination">
        <ul className="pagination pagination-lg">
          <li className="page-item">
            <button
              className="page-link"
              onClick={() => handlePageChange(currentPage - 1)}
            >
              Previous
            </button>
          </li>
          <li className="page-item page-link active">{currentPage}</li>
          <li className="page-item">
            <button
              className="page-link"
              onClick={() => handlePageChange(currentPage + 1)}
            >
              Next
            </button>
          </li>
        </ul>
      </nav>
    </>
  );
}

export default Pagination;
