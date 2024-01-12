import React from "react";
import "./SignUp.css";
import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { NavLink, useNavigate } from "react-router-dom";
import { signUp } from "../../api";

const schema = yup.object({
  userName: yup
    .string()
    .required("Please enter your email")
    .email("Invalid Email"),
  password: yup
    .string()
    .required("Please enter Password")
    .min(8, "Password must be atleast 8 characters")
    .matches(
      /^(?=.*?[0-9])(?=.*?[A-Za-z]).{8,32}$/,
      "Password must be a combination of at least uppercase letter, special character and a number"
    ),
  confirmPassword: yup
    .string()
    .required("Please re-enter Password")
    .oneOf([yup.ref("password")], "Password must match"),
});

export default function SignUp() {
  const navigate = useNavigate();
//  const [successMessage, setsuccessMessage] = useState("");
  const {
    handleSubmit,
    register,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schema),
  });
  // console.log(errors);

  const formSubmit = (data) => {
    signUp(data, navigate);
  };

  return (
    <div>
      <div className="signup-bg">
        <div className="signup-card">
          <h2 id="signup-heading">Create Account</h2>
          <form id="signup-id" onSubmit={handleSubmit(formSubmit)}>
            <label className="signup-lbl">
              <input
                className="signup-inp"
                type="text"
                name=""
                id="userName"
                placeholder=""
                {...register("userName")}
              />
              <span className="signup-label-span">Email</span>
            </label>
            {/* <br /> */}
            <span className="signup-error-message">
              {errors.userName?.message}
            </span>
            {/* <br /> */}
            <label className="signup-lbl">
              <input
                className="signup-inp"
                type="password"
                name=""
                id="password"
                placeholder=" "
                {...register("password")}
              />
              <span className="signup-label-span">Password</span>
              {/* <span className="error-message">{errors.password?.message}</span> */}
            </label>
            {/* <br /> */}
            <div className="signup-err-wrapper">
              <span className="signup-error-message">
                {errors.password?.message}
              </span>
            </div>
            {/* <br /> */}
            <label className="signup-lbl">
              <input
                className="signup-inp"
                type="password"
                name=""
                id="signup-confirmPassword"
                placeholder=" "
                {...register("confirmPassword")}
              />
              <span className="signup-label-span">Confirm Password</span>
              {/* <span className="error-message">
                {errors.confirmPassword?.message}
              </span> */}
            </label>
            {/* <br /> */}
            <span className="signup-error-message">
              {errors.confirmPassword?.message}
            </span>
            {/* <br /> */}
            <div className="signup-btn-div">
              <button type="submit" id="signup-sub-btn">
                Register
              </button>
              <br></br>
              <span>Already have an account? </span>
              <NavLink
                style={{ color: "rgb(0, 61, 89)" }}
                className="signup-nav-link signup-btn-div"
                aria-current="page"
                to="/login"
              >
                Log In
              </NavLink>
            </div>
          </form>
          <br />
          {/* <span className="signup-successsMessage">{successMessage}</span> */}
        </div>
      </div>
    </div>
  );
}
