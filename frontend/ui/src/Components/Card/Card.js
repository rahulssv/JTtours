import React from "react";
import '../../App.css';
import { useNavigate } from "react-router-dom";
export default function Card({ data }) {
  const navigate = useNavigate();
  return (
    <>
      <div className="card" onClick={()=>navigate("/tours")} >
        <img src={data.image} className="" style={{width:'100%'}}  alt="Delhi" />

        <div className="card-body">
          <h5 className="card-title p-1 m-0 text-center">{data.name}</h5>

          {/* <p className="card-text">{data.desc}</p */}

          {/* <Link to="/" className="btn btn-primary">
            
            Check Tour
          </Link> */}
        </div>
      </div>
    </>
  );
}
