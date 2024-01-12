import axios from "axios";
import { set } from "react-hook-form";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const API = axios.create({ baseURL: `http://${process.env.REACT_APP_API_URL}` });

API.interceptors.request.use((req) => {
  if (localStorage.getItem("user")) {
    // console.log(localStorage.getItem("user")?.token);
    req.headers.Authorization = `Bearer ${JSON.parse(localStorage.getItem("user")).token
      }`;
  }
  return req;
});

export const logIn = async (newUser, navigate, myUser, setAdmin) => {
  localStorage.clear();
  await API.post("/auth/login", newUser)
    .then(({ data }) => {
      // console.log(data);
      const user = {};
      user.token = data.token;
      user.role = data.role;
      user.username = data.username;
      // console.log(user);

      // setAdmin(data.role === "ADMIN");
      localStorage.setItem("user", JSON.stringify(user));
      setAdmin(JSON.parse(localStorage.getItem("user"))?.role === "ADMIN");
      myUser(localStorage.getItem("user"));
      toast.success("User Logged in Successfully!");
      navigate("/");
      // alert(data);
    })
    .catch((err) => {
      toast.error("Invalid username or password");
    });
};

export const signUp = async (formData, navigate) => {
  try {
    formData.role = "CUSTOMER";
    await API.post("/auth/register", formData);
    toast.success("User Registration successful.");
    navigate("/login");
    return;
  } catch (err) {
    console.log(err);
    toast.error(err.message);
  }
};

export const getAllTours = async (setTourData, criteria, filter) => {
  try {
    const { data } = await API.get("/tours");
    data.map((d) => {
      d.days = d?.itinerary?.dayPlans?.length;
    });
    setTourData(data);
    return data;
    // console.log(data);
  } catch (err) {
    console.log(err);
    toast.error(err.message);
  }
};

export const addTour = async (formData, navigate) => {
  try {
    await API.post("/tours", formData);
    toast.success("New Tour added.");
    navigate(-1);
    return;
  } catch (err) {
    console.log(err);
    toast.error(err.message);
  }
};
export const updateTour = async (id, formData, navigate) => {
  try {
    await API.put(`/tours`, formData);
    toast.success("Tour updated Successfully.");
    navigate(-1);
    return;
  } catch (err) {
    toast.error(err.message);
  }
};

export const addBatch = async (formData, id, navigate) => {
  try {
    formData.status = "ACTIVE";
    await API.post(`/batches/tours/${id}`, formData);
    toast.success("Success");
    navigate(-1);
    return;
  } catch (err) {
    toast.error(err.message);
  }
};

export const deleteTourById = async (setTourData, id) => {
  try {
    await API.delete(`/tours/${id}`);
    const { data } = await API.get("/tours");
    setTourData(data);
    return data;
  } catch (err) {
    toast.error(err.message);
  }
};

export const updateBatch = async (url, formData, navigate) => {
  try {
    // console.log(url);
    await API.put(`/tours/${url}`, formData);
    toast.success("Batch updated");
    navigate(-1);
  } catch (err) {
    toast.error(err.message);
  }
};

export const getBatchDetailsByBatchId = async (id, setBatchData) => {
  try{
    await API.get(`/batches/${id}`).then(({data})=>{
      setBatchData(data);
      console.log(data);
    })
  }catch(err) {

    toast.error(err.message);
  }
}

export const getBatchesByTourId = async (tourId, setBatchData) => {
  try {
    // console.log(url);
    const { data } = await API.get(`/tours/${tourId}/batches`);
    setBatchData(data);
  } catch (err) {
    toast.error(err.message);
  }
};

//Filters for Batch

export const filterBatchByDate = async (minDate, maxDate, setBatchData) => {
  try {
    if (minDate === "") {
      const { data } = await API.get(`/batches/startDate?maxDate=${maxDate}`);
      setBatchData(data);
    }
    else if (maxDate === "") {
      const { data } = await API.get(`/batches/startDate?minDate=${minDate}`);
      setBatchData(data);
    }
    else {
      const { data } = await API.get(`/batches/startDate?minDate=${minDate}&maxDate=${maxDate}`);
      setBatchData(data);
    }
  } catch (err) {
    toast.error(err.message);
  }
}

export const filterBatchByAvailableSeats = async (minSeat, maxSeat, setBatchData) => {
  try {
    if (minSeat === 0) {
      const { data } = await API.get(`/batches/availableSeats?maxAvailableSeats=${maxSeat}`);
      setBatchData(data);
    }
    else if (maxSeat === 0) {
      const { data } = await API.get(`/batches/availableSeats?minAvailableSeats=${minSeat}`);
      setBatchData(data);
    }
    else {
      const { data } = await API.get(`/batches/availableSeats?maxAvailableSeats=${maxSeat}&minAvailableSeats=${minSeat}`);
      setBatchData(data);
    }
  } catch (err) {
    toast.error(err.message);
  }
}

export const filterBatchByParticipantCost = async (budget, setBatchData) => {
  try {
    const { data } = await API.get(`/batches/perParticipantCost?budget=${budget}`);
    setBatchData(data);
  } catch (err) {
    toast.error(err.message);
  }
}

export const filterBatchByStatus = async(status, setBatchData) => {
  try {
    const {data} = await API.get(`/batches/status?status=${status}`);
    setBatchData(data);
  } catch(err) {
    toast.error(err.message);
  }
}

// Filters for Tours

export const filterTourByDay = async (minDay, maxDay, setTourData) => {
  try {
    const { data } = await API.get(`/tours/day?minDays=${minDay}&maxDays=${maxDay}`);
    setTourData(data);
  } catch (err) {
    setTourData([]);
    toast.error("No Tour Found");
  }
}
export const filterTourByPlace = async (source, destination, setTourData) => {
  try {
    const { data } = await API.get(`/tours/place?startAt=${source}&endAt=${destination}`);
    setTourData(data);
  } catch (err) {
    setTourData([]);
    toast.error("No Tour Found");
  }
}
export const filterTourByMode = async (mode, setTourData) => {
  try {
    const { data } = await API.get(`/tours/mode?mode=${mode}`);
    setTourData(data);
  } catch (err) {
    setTourData([]);
    toast.error("No Tour Found");
  }
}
export const filterTourByDifficulty = async (difficultyLevel, setTourData) => {
  try {
    const { data } = await API.get(`/tours/difficultyLevel?difficultyLevel=${difficultyLevel}`);
    setTourData(data);
  } catch (err) {
    setTourData([]);
    toast.error("No Tour Found");
  }
}

export const cancelBatch = async (url, batchId, setBatchData) => {
  try {
    // console.log(url);
    await API.delete(`/batches/${batchId}`);
    const { data } = await API.get(`${url}`);
    setBatchData(data);
  } catch (err) {
    console.log(err);
    toast.error(err.message);
  }
};

export const addBooking = async (booking, navigate) => {
  try {
    await API.post(`/batches/book`, booking);
    toast.success("Batch Booked Successfully");
    navigate("/bookings");
  } catch (err) {
    console.log(err);
    toast.error(err);
  }
};

export const getMetaData = async (enumType, setEnumType) => {
  try {
    await API.get(`/metadata/${enumType}`)
      .then(({ data }) => {
        // console.log(data);
        setEnumType(data);
      })
      .catch((err) => {
        toast.error("Error in metadata");
      });
  } catch (err) {
    toast.err("Error in metadata");
  }
};

export const getTourDetails = async (id, setTourData) => {
  try {
    await API.get(`/tours/${id}`).then(({ data }) => {
      setTourData(data);
    });
  } catch (err) {
    toast.error("Error in getTourDetails");
  }
};

export const getBookings = async (setBookings,navigate) =>{
  try{
    await API.get("/bookings").then(({data})=>{
      setBookings(data);
    })
  }catch(err){
    navigate('/Login');
    // toast.error(err.message);
  }
}

export const getTourName = async (batchId,setTourName) =>{
  try{
    await API.get(`/batches/${batchId}`).then(({data})=>{
      setTourName(data?.tour?.name)
    })
  }catch(err){
    toast.error(err.message);
  }
}
