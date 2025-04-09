import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import RecipeCard from "../../components/RecipeCard";
import styles from "./styles.module.css";

function Home() {
  const [recipes, setRecipes] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [activeCategory, setActiveCategory] = useState("all");

  console.log(activeCategory);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [recipesResponse, categoriesResponse] = await Promise.all([
          axios.get("/api/recipes"),
          axios.get("/api/categories"),
        ]);

        setRecipes(recipesResponse.data);
        setCategories(categoriesResponse.data);
        setLoading(false);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleDeleteRecipe = (recipeId) => {
    setRecipes(recipes.filter((recipe) => recipe.id !== recipeId));
  };

  const filteredRecipes = recipes.filter((recipe) => {
    const matchesSearch =
      recipe.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      recipe.description?.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesCategory =
      activeCategory === "all" || recipe.category.id === activeCategory;

    return matchesSearch && matchesCategory;
  });

  if (loading) {
    return (
      <div className={styles.loadingContainer}>
        <div className={styles.loader}></div>
        <p>Se incarca retetele...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className={styles.errorContainer}>
        <div className={styles.errorIcon}>⚠️</div>
        <h2>Eroare la incarcarea retetelor</h2>
        <p>{error}</p>
        <button
          className={styles.retryButton}
          onClick={() => window.location.reload()}
        >
         Incearca din nou
        </button>
      </div>
    );
  }

  return (
    <div className={styles.homeContainer}>
      <div className={styles.hero}>
        <div className={styles.heroContent}>
          <h1>Retete delicioase</h1>
          <p>Descopera si impartaseste retetele tale preferate</p>

          <div className={styles.searchContainer}>
            <input
              type="text"
              placeholder="Cauta retete..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className={styles.searchInput}
            />
            {searchTerm && (
              <button
                className={styles.clearButton}
                onClick={() => setSearchTerm("")}
              >
                ×
              </button>
            )}
          </div>
        </div>
      </div>

      <div className={styles.contentContainer}>
        <div className={styles.categoriesNav}>
          <button
            className={`${styles.categoryButton} ${
              activeCategory === "all" ? styles.activeCategory : ""
            }`}
            onClick={() => setActiveCategory("all")}
          >
            Toate Retetele
          </button>

          {categories.map((category) => (
            <button
              key={category.id}
              className={`${styles.categoryButton} ${
                activeCategory === category.id ? styles.activeCategory : ""
              }`}
              onClick={() => setActiveCategory(category.id)}
            >
              {category.name}
            </button>
          ))}
        </div>

        <div className={styles.recipesHeader}>
          <h2>
            {activeCategory === "all"
              ? "Toate Retetele"
              : categories.find((c) => c.id === activeCategory)?.name ||
                "Retete"}
            {searchTerm && ` care contin cuvantul "${searchTerm}"`}
          </h2>

          <Link to="/add-recipe" className={styles.addRecipeButton}>
            <span className={styles.addIcon}>+</span> Adauga o noua reteta
          </Link>
        </div>

        {filteredRecipes.length === 0 ? (
          <div className={styles.noRecipes}>
            <div className={styles.emptyIcon}>🍽️</div>
            <h3>Nu s-au gasit retete</h3>
            <p>
              {searchTerm
                ? "Schimba elementele din categorie sau search"
                : "Adauga prima reteta"}
            </p>
            {!searchTerm && (
              <Link to="/add-recipe" className={styles.addFirstRecipeButton}>
                Adauga prima reteta
              </Link>
            )}
          </div>
        ) : (
          <div className={styles.recipesGrid}>
            {filteredRecipes.map((recipe) => (
              <div key={recipe.id} className={styles.recipeCardWrapper}>
                <RecipeCard recipe={recipe} onDelete={handleDeleteRecipe} />
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default Home;
