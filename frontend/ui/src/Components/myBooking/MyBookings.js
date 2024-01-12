import React, { useEffect, useState } from "react";
import { getBookings } from "../../api";
import { useNavigate } from "react-router-dom";
import BookedTour from "./BookedTour";

function MyBookings() {
  const navigate = useNavigate();
  const [bookings, setBookings] = useState([]);
  useEffect(()=>{
    getBookings(setBookings,navigate);
  },[]);

  return (
    <div className="container-fluid p-4 mt-4" style={{ width: "80%", background:'white' }}>
      <h2 style={{textAlign:'center'}}>My Bookings</h2>
      <div className="tour-card mt-5">
        {
          bookings?.map((booking)=>{
            return ( <div onClick={()=>navigate(`/bookedbatch/${booking?.batchId}`)} className="shadow p-4" style={{maxWidth:'400px',borderRadius:'10px',cursor:'pointer'}}> 
            {/* <h4>Bhopal-Sagar</h4>
            <p>Participants: {booking?.travellers?.length}</p>
            <p>Booking Date : {booking?.date}</p> */}
            <BookedTour id={booking?.batchId} booking={booking} />
            </div> )
          })
        }
         
      </div>
    </div>
  );
}

export default MyBookings;
