import React from "react";
import styles from "./styles.module.css";
import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav className={styles.navbar}>
      <div className={styles.container}>
        <Link to="/" className={styles.logo}>
          Savorly
        </Link>
        <div className={styles.navLinks}>
          <Link to="/" className={styles.navLink}>
            Acasa
          </Link>
          <Link to="/add-recipe" className={styles.navLink}>
            Adauga reteta
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
