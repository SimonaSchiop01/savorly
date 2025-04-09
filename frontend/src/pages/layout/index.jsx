import React from "react";
import { Outlet } from "react-router-dom";
import Navbar from "../../components/navbar";
import Footer from "../../components/footer";
import styles from "./styles.module.css";

const Layout = () => {
  return (
    <div className={styles.container}>
      <Navbar />
      <div className={styles.content}>
        <Outlet />
      </div>
      <Footer />
    </div>
  );
};

export default Layout;
