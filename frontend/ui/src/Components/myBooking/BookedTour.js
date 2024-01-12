import React, { useEffect, useState } from 'react'
import { getTourName } from '../../api';

function BookedTour({id,booking}) {
    const [tourName, setTourName] = useState("");
    useEffect(()=>{
        getTourName(id, setTourName);
    })
  return (
     <>
           <h4>{tourName}</h4>
            <p>Participants: {booking?.travellers?.length}</p>
            <p>Booking Date : {booking?.date}</p>
     </>
  )
}

export default BookedTour