import React from "react";
import styles from "./styles.module.css";

const Footer = () => {
  return (
    <footer className={styles.footer}>
      <div className={styles.container}>
        <p className={styles.copyright}>
          © {new Date().getFullYear()} Savorly. All rights reserved.
        </p>
      </div>
    </footer>
  );
};

export default Footer;
