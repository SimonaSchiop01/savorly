import { Link } from "react-router-dom";
import styles from "./styles.module.css";
import { useState } from "react";
import axios from "axios";

function RecipeCard({ recipe, onDelete }) {
  const [isHovered, setIsHovered] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleDelete = async (e, recipeId) => {
    e.stopPropagation();
    if (window.confirm("Esti sigur ca vrei sa stergi reteta?")) {
      setLoading(true);
      try {
        await axios.delete(`/api/recipes/${recipeId}`);
        if (onDelete) onDelete(recipeId);
      } catch (err) {
        setError(err.message);
        console.error("Error deleting recipe:", err);
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <div
      className={styles.recipeCard}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <div className={styles.imageContainer}>
        <img
          className={styles.recipeImage}
          src={
            recipe.photoUri
              ? `/uploads/${recipe.photoUri}`
              : "default-recipe.jpg"
          }
          alt={recipe.name}
        />
        {isHovered && (
          <div className={styles.overlay}>
            <Link to={`/recipes/${recipe.id}`} className={styles.detailsButton}>
              Vezi reteta
            </Link>
          </div>
        )}
        <div className={styles.preparationTime}>
          <i className={styles.clockIcon}>⏱️</i> {recipe.preparationTime}
        </div>
      </div>

      <div className={styles.cardContent}>
        <div className={styles.cardHeader}>
          <h3 className={styles.recipeTitle}>{recipe.name}</h3>
          <div className={styles.actions}>
            <button
              className={`${styles.actionButton} ${styles.deleteButton}`}
              onClick={(e) => handleDelete(e, recipe.id)}
              disabled={loading}
            >
              {loading ? "..." : "×"}
            </button>
            <Link
              to={`/edit-recipe/${recipe.id}`}
              className={`${styles.actionButton} ${styles.editButton}`}
            >
              ✎
            </Link>
          </div>
        </div>

        {recipe.category && (
          <div className={styles.category}>{recipe.category.name}</div>
        )}

        <p className={styles.recipeDescription}>
          {recipe.description?.substring(0, 100)}
          {recipe.description?.length > 100 ? "..." : ""}
        </p>

        <div className={styles.ingredientCount}>
          {recipe.recipeIngredients?.length || 0} ingredients
        </div>
      </div>

      {error && <div className={styles.errorMessage}>{error}</div>}
    </div>
  );
}

export default RecipeCard;
