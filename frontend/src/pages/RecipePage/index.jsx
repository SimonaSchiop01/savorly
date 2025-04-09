import axios from "axios";
import styles from "./styles.module.css";
import { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";

function RecipePage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [recipe, setRecipe] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchRecipe = async () => {
      try {
        const response = await axios.get(`/api/recipes/${id}`);
        setRecipe(response.data);
        setLoading(false);
      } catch (err) {
        setError(err.message || "Failed to load recipe");
        setLoading(false);
      }
    };

    fetchRecipe();
  }, [id]);

  const handleDelete = async () => {
    if (window.confirm("Esti sigur ca vrei sa stergi aceasta reteta?")) {
      try {
        await axios.delete(`/api/recipes/${id}`);
        navigate("/");
      } catch (err) {
        setError(err.message || "Failed to delete recipe");
      }
    }
  };

  if (loading) {
    return (
      <div className={styles.loadingContainer}>
        <div className={styles.loader}></div>
        <p>Cauta Reteta...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className={styles.errorContainer}>
        <div className={styles.errorIcon}>⚠️</div>
        <h2>ERoare la incarcarea Retetei</h2>
        <p>{error}</p>
        <button
          className={styles.retryButton}
          onClick={() => window.location.reload()}
        >
          Try Again
        </button>
      </div>
    );
  }

  if (!recipe) {
    return (
      <div className={styles.errorContainer}>
        <div className={styles.errorIcon}>🍽️</div>
        <h2>Recipe not found</h2>
        <p>The recipe you're looking for doesn't exist or has been removed.</p>
        <Link to="/" className={styles.returnButton}>
          Return la Retete
        </Link>
      </div>
    );
  }

  return (
    <div className={styles.recipePageContainer}>
      <div className={styles.recipeHeader}>
        <div className={styles.breadcrumbs}>
          <Link to="/">Home</Link> / <span>{recipe.name}</span>
        </div>
        <div className={styles.actions}>
          <Link to={`/edit-recipe/${id}`} className={styles.editButton}>
            Editeaza Reteta
          </Link>
          <button onClick={handleDelete} className={styles.deleteButton}>
            Sterge Reteta
          </button>
        </div>
      </div>

      <div className={styles.recipeContent}>
        <div className={styles.recipeImageContainer}>
          <img
            src={`/uploads/${recipe.photoUri}`}
            alt={recipe.name}
            className={styles.recipeImage}
          />
          <div className={styles.recipeCategory}>{recipe.category.name}</div>
        </div>

        <div className={styles.recipeDetails}>
          <h1 className={styles.recipeName}>{recipe.name}</h1>

          {recipe.preparationTime && (
            <div className={styles.prepTime}>
              <span className={styles.prepIcon}>⏱️</span>
              <span>Timp de preparare: {recipe.preparationTime}</span>
            </div>
          )}

          <div className={styles.recipeDescription}>
            <h2>Descriere</h2>
            <p>{recipe.description}</p>
          </div>

          <div className={styles.ingredientsSection}>
            <h2>Ingrediente</h2>
            <ul className={styles.ingredientsList}>
              {recipe.recipeIngredients &&
                recipe.recipeIngredients.map((item) => (
                  <li key={item.id} className={styles.ingredientItem}>
                    <span className={styles.ingredientName}>
                      {item.ingredient.name}
                    </span>
                    <span className={styles.ingredientQuantity}>
                      {item.quantity}
                    </span>
                  </li>
                ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RecipePage;
