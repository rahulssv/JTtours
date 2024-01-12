import React, { useEffect, useState } from "react";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { BreadcrumbsProvider } from "react-breadcrumbs-dynamic";
import ViewTours from "./Containers/Tours/Tours";
import Homepage from "./Containers/Homepage/Homepage";
import Login from "./Containers/Login/Login";
import AddTour from "./Containers/Admin/AddTour";
import TourDetails from "./Containers/Admin/TourDetails";
import NavBar from "./Components/Navbar/NavBar";
import Footer from "./Components/Footer/Footer";
import SignUp from "./Containers/SignUp/SignUp";
import BookingForm from "./Components/BookingForm/BookingForm";
import AddBatch from "./Containers/Admin/AddBatch";
import UpdateBatch from "./Containers/Admin/UpdateBatch";
import Aos from "aos";
import UpdateForm from "./Containers/Admin/UpdateForm";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Payments from "./Components/Payments/Payments";
import Batch from "./Components/batch/Batch";
import MyBookings from "./Components/myBooking/MyBookings";
import BookedBatchView from "./Components/batch/BookedBatchView";

function App() {
  const [user, setUser] = useState();
  const [admin, setAdmin] = useState(false);

  useEffect(() => {
    setUser(localStorage.getItem("user"));
    setAdmin(JSON.parse(localStorage.getItem("user"))?.role === "ADMIN");
    Aos.init();
  }, []);
  return (
    <BreadcrumbsProvider>
      <BrowserRouter>
        <NavBar user={user} setUser={setUser} setAdmin={setAdmin}></NavBar>
        <Routes>
          <Route exact path="/home" Component={Homepage}></Route>
          <Route
            exact
            path="/"
            element={<Navigate to="/home" replace />}
          ></Route>
          <Route
            exact
            path="/tours"
            element={
              <ViewTours user={user} admin={admin} setAdmin={setAdmin} />
            }
          ></Route>
          <Route
            exact
            path="/signup"
            element={user ? <Navigate to="/home" replace /> : <SignUp />}
          ></Route>
          <Route
            path="/login"
            element={
              user ? (
                <Navigate to="/" replace />
              ) : (
                <Login myUser={setUser} setAdmin={setAdmin} />
              )
            }
          />
          <Route
            exact
            path="/tours/:id/batches"
            element={<TourDetails isAdmin={admin}/>}
          ></Route>
          <Route
            exact
            path="/tours/:id/batches/updatebatch"
            Component={UpdateBatch}
          ></Route>
          <Route
            exact
            path="/tours/:id/bookTour"
            element={user ? <BookingForm /> : <Navigate to="/login" replace />}
          ></Route>
          <Route
            exact
            path="/tours/:id/bookTour/payments"
            element={user ? <Payments /> : <Navigate to="/login" replace />}
          ></Route>
          <Route exact path="/tours/:id/addBatch" Component={AddBatch}></Route>
          <Route
            exact
            path="/tours/:id/updateTour"
            element={user ? <UpdateForm /> : <Navigate to="/home" replace />}
          ></Route>
          <Route
            exact
            path="/tours/add-tour"
            element={user ? <AddTour /> : <Navigate to="/home" replace />}
          ></Route>
          <Route
            path="/batch/:id"
            element={<Batch />}
            user = {user}
          ></Route>
          <Route
            path="/bookedbatch/:id"
            element={<BookedBatchView />}
          ></Route>
          <Route
            path="/bookings"
            element={<MyBookings />}
          ></Route>
        </Routes>
        <Footer></Footer>
        <ToastContainer />
      </BrowserRouter>
    </BreadcrumbsProvider>
  );
}

export default App;
