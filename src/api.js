import axios from "axios";

export default axios.create({
  baseURL: "http://ec2-3-17-67-232.us-east-2.compute.amazonaws.com:8080",
  // withCredentials: true,
});
