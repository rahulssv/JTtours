import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { Lock } from "react-bootstrap-icons";
import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { logIn } from "../../api";

function Login({ myUser, setAdmin }) {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    userName: "",
    password: "",
  });

  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    logIn(user, navigate, myUser, setAdmin);
  };
  return (
    <div
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        minHeight: "90vh",
        background: "white",
      }}
    >
      <Form
        method="POST"
        onSubmit={handleSubmit}
        className="d-flex flex-column"
        style={{
          width: "400px",
          maxWidth: "100%",

          boxShadow: "0 0 5px rgba(0,0,0,0.5)",
          borderRadius: "10px",
          padding: "30px",
        }}
      >
        <div
          style={{
            cursor: "pointer",
            borderRadius: "50%",
            display: "flex",
            width: "60px",
            height: "60px",
            margin: "auto",
            padding: "10px",
            border: "2px solid goldenrod",
          }}
        >
          <Lock
            className=" "
            style={{ fontSize: "35px", color: "goldenrod" }}
          />
        </div>

        <h2 style={{ textAlign: "center", marginBottom: "30px" }}>Log In</h2>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            type="email"
            onChange={handleChange}
            placeholder="Enter email"
            name="userName"
            value={user.userName}
            required={true}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            name="password"
            value={user.password}
            onChange={handleChange}
            placeholder="Password"
            required={true}
          />
        </Form.Group>

        <Button
          variant="primary"
          style={{ background: "#003D59" }}
          className="mb-3 w-50 mx-auto"
          type="submit"
        >
          Login
        </Button>
        <Form.Group className="mb-3 text-center">
          <span>Do not have an account ? </span>
          <NavLink to="/signup" style={{ color: "#003D59" }}>
            SignUp
          </NavLink>
        </Form.Group>
      </Form>
    </div>
  );
}

export default Login;
