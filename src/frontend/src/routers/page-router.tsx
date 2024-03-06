import { createBrowserRouter } from "react-router-dom";

import Home from "../pages/home";
import Auth from "../pages/auth";
import CreateAccount from "../pages/create-account";
import Login from "../pages/login";

const router: any = createBrowserRouter([
    {
      path: "/",
      element: <Home />
    },
    {
      path: "/home",
      element: <Home />
    },
    {
      path: "/auth",
      element: <Auth />
    },
    {
      path: "/create_account",
      element: <CreateAccount />
    },
    {
      path: "/login",
      element: <Login />
    }
]);

export default router;