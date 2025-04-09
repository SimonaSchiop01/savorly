import { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import axios from "axios";
import styles from "./styles.module.css";

function EditRecipe() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [recipe, setRecipe] = useState({
    name: "",
    description: "",
    preparationTime: "",
    categoryId: "",
    recipeIngredients: [],
  });

  const [categories, setCategories] = useState([]);
  const [ingredients, setIngredients] = useState([]);
  const [imageFile, setImageFile] = useState(null);
  const [existingImageName, setExistingImageName] = useState("");
  const [imagePreview, setImagePreview] = useState("");
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);


  // Fetch datele retetei si categoriile/ingredientele
  useEffect(() => {
    const fetchData = async () => {
      try {
        const [recipeResponse, categoriesResponse, ingredientsResponse] =
          await Promise.all([
            axios.get(`/api/recipes/${id}`),
            axios.get("/api/categories"),
            axios.get("/api/ingredients"),
          ]);

        const recipeData = recipeResponse.data;

        // Transformam datele recipe sa se potriveasca cu structura din form
        setRecipe({
          name: recipeData.name,
          description: recipeData.description || "",
          preparationTime: recipeData.preparationTime || "",
          categoryId: recipeData.category.id,
          recipeIngredients: recipeData.recipeIngredients.map((item) => ({
            id: item.id,
            ingredientId: item.ingredient.id,
            quantity: item.quantity,
          })),
        });

        // Salvam imaginea existenta si setam preview
        setExistingImageName(recipeData.photoUri);
        if (recipeData.photoUri) {
          setImagePreview(`/uploads/${recipeData.photoUri}`);
        }
        setCategories(categoriesResponse.data);
        setIngredients(ingredientsResponse.data);
        setLoading(false);
      } catch (err) {
        setError(err.message || "Failed to load recipe data");
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRecipe((prev) => ({ ...prev, [name]: value }));
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setImageFile(file);
      setImagePreview(URL.createObjectURL(file));
    }
  };

  const handleIngredientChange = (index, field, value) => {
    const updatedIngredients = [...recipe.recipeIngredients];

    updatedIngredients[index] = {
      ...updatedIngredients[index],
      [field]: value,
    };

    setRecipe((prev) => ({ ...prev, recipeIngredients: updatedIngredients }));

    // Clear error when user makes changes
    if (error) {
      setError(null);
    }
  };

  const addIngredient = () => {
    setRecipe((prev) => ({
      ...prev,
      recipeIngredients: [
        ...prev.recipeIngredients,
        { ingredientId: "", quantity: "" },
      ],
    }));
  };

  const removeIngredient = (index) => {
    const updatedIngredients = [...recipe.recipeIngredients];
    updatedIngredients.splice(index, 1);
    setRecipe((prev) => ({ ...prev, recipeIngredients: updatedIngredients }));

    // Clear error when user makes changes
    if (error) {
      setError(null);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !recipe.name ||
      !recipe.categoryId ||
      recipe.recipeIngredients.length === 0
    ) {
      setError("Completati toate campurile obligatorii");
      return;
    }

    // Verificam daca toate ingredientele au si id si cantitate
    const hasIncompleteIngredients = recipe.recipeIngredients.some(
      (item) => !item.ingredientId || !item.quantity
    );

    if (hasIncompleteIngredients) {
      setError("Completati toate campurile ingredientelor");
      return;
    }

    setSubmitting(true);
    setError(null);

    try {
      const formData = new FormData();

      const recipeData = {
        name: recipe.name,
        description: recipe.description,
        preparationTime: recipe.preparationTime,
        categoryId: recipe.categoryId,
        recipeIngredients: recipe.recipeIngredients.map((item) => ({
          ...(item.id && { id: item.id }),
          ingredientId: item.ingredientId,
          quantity: item.quantity,
        })),
      };


      formData.append("recipe", JSON.stringify(recipeData));

      // Adaugam un image file daca unul nou a fost creat
      if (imageFile) {
        formData.append("image", imageFile);
      } else {
        const emptyBlob = new Blob([""], { type: "application/octet-stream" });
        formData.append("image", emptyBlob, "empty.txt");
      }

      console.log("Sending recipe data:", JSON.stringify(recipeData));

      // Trimitem update-ul
      await axios.put(`/api/recipes/${id}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

     
      navigate(`/recipes/${id}`);
    } catch (err) {
      setError(
        err.response?.data?.message || err.message || "Failed to update recipe"
      );
      setSubmitting(false);
    }
  };

  // Function to get available ingredients (filtering out already selected ones)
  const getAvailableIngredients = (currentIndex) => {
    if (!ingredients) return [];

    const selectedIngredientIds = recipe.recipeIngredients
      .filter((_, index) => index !== currentIndex)
      .map((item) => item.ingredientId);

    return ingredients.filter(
      (ingredient) => !selectedIngredientIds.includes(ingredient.id)
    );
  };

  if (loading) {
    return (
      <div className={styles.loadingContainer}>
        <div className={styles.loader}></div>
        <p>Se incarca datele...</p>
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Edit Recipe</h1>

      {error && <div className={styles.error}>{error}</div>}

      <form className={styles.form} onSubmit={handleSubmit}>
        <div className={styles.formGroup}>
          <label className={styles.label} htmlFor="name">
            Recipe Name*
          </label>
          <input
            type="text"
            id="name"
            name="name"
            value={recipe.name}
            onChange={handleChange}
            className={styles.input}
            required
          />
        </div>

        <div className={styles.formGroup}>
          <label className={styles.label} htmlFor="description">
            Description
          </label>
          <textarea
            id="description"
            name="description"
            value={recipe.description}
            onChange={handleChange}
            className={styles.textarea}
          />
        </div>

        <div className={styles.formGroup}>
          <label className={styles.label} htmlFor="preparationTime">
            Preparation Time
          </label>
          <input
            type="text"
            id="preparationTime"
            name="preparationTime"
            value={recipe.preparationTime}
            onChange={handleChange}
            placeholder="e.g., 30 minutes"
            className={styles.input}
          />
        </div>

        <div className={styles.formGroup}>
          <label className={styles.label} htmlFor="categoryId">
            Category*
          </label>
          <select
            id="categoryId"
            name="categoryId"
            value={recipe.categoryId}
            onChange={handleChange}
            className={styles.select}
            required
          >
            <option value="">Select a category</option>
            {categories.map((category) => (
              <option key={category.id} value={category.id}>
                {category.name}
              </option>
            ))}
          </select>
        </div>

        <div className={styles.formGroup}>
          <label className={styles.label}>Imagine reteta</label>

          <div className={styles.imagePreviewContainer}>
            {imagePreview && (
              <img
                src={imagePreview}
                alt="Recipe preview"
                className={styles.imagePreview}
              />
            )}
          </div>

          <input
            type="file"
            id="image"
            onChange={handleImageChange}
            className={styles.fileInput}
            accept="image/*"
          />
          <p className={styles.helperText}>
            Uploadeaza o noua imagine sau pastreaza imaginea curenta
          </p>
        </div>

        <div className={styles.ingredientsSection}>
          <h2 className={styles.subtitle}>Ingrediente*</h2>
          {/* <p className={styles.helperText}>
            Each ingredient can only be used once in a recipe.
          </p> */}

          {recipe.recipeIngredients.map((item, index) => (
            <div key={index} className={styles.ingredientRow}>
              <div className={styles.ingredientInputs}>
                <select
                  value={item.ingredientId}
                  onChange={(e) =>
                    handleIngredientChange(
                      index,
                      "ingredientId",
                      e.target.value
                    )
                  }
                  className={styles.ingredientInput}
                  required
                >
                  <option value="">Select ingredient</option>
                  {getAvailableIngredients(index).map((ingredient) => (
                    <option key={ingredient.id} value={ingredient.id}>
                      {ingredient.name}
                    </option>
                  ))}
                  {/* Daca acest ingredient este deja selectat, il includem in optiuni */}
                  {item.ingredientId &&
                    !getAvailableIngredients(index).some(
                      (ing) => ing.id === item.ingredientId
                    ) && (
                      <option value={item.ingredientId}>
                        {ingredients.find((ing) => ing.id === item.ingredientId)
                          ?.name || "Selected ingredient"}
                      </option>
                    )}
                </select>

                <input
                  type="text"
                  value={item.quantity}
                  onChange={(e) =>
                    handleIngredientChange(index, "quantity", e.target.value)
                  }
                  placeholder="Quantity (ex., 2 g)"
                  className={styles.ingredientInput}
                  required
                />
              </div>

              <button
                type="button"
                onClick={() => removeIngredient(index)}
                className={styles.removeBtn}
                disabled={recipe.recipeIngredients.length <= 1}
              >
                Remove
              </button>
            </div>
          ))}

          <button
            type="button"
            onClick={addIngredient}
            className={styles.addBtn}
            disabled={recipe.recipeIngredients.length >= ingredients.length}
          >
            + Adauga ingredient
          </button>
        </div>

        <div className={styles.formActions}>
          <Link to={`/recipes/${id}`} className={styles.cancelBtn}>
            Anuleaza
          </Link>
          <button
            type="submit"
            className={styles.submitBtn}
            disabled={submitting}
          >
            {submitting ? "Se salveaza..." : "Salveaza schimbarile"}
          </button>
        </div>
      </form>
    </div>
  );
}

export default EditRecipe;
