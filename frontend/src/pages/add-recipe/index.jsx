import styles from "./styles.module.css";
import ImageUpload from "../../components/upload-image";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

const AddRecipe = () => {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [ingredients, setIngredients] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // State pt salvare image file
  const [imageFile, setImageFile] = useState(null);

  const [recipe, setRecipe] = useState({
    name: "",
    description: "",
    photoUri: "",
    preparationTime: "",
    categoryId: "",
    ingredients: [{ ingredientId: "", quantity: "" }],
  });

  // Fetch categories si ingredients cand component mounts
  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await fetch("/api/categories");
        const data = await response.json();
        setCategories(data);
      } catch (err) {
        console.error("Error fetching categories:", err);
        setError("Eroare la incarcarea categoriilor. Incearca mai tarziu.");
      }
    };

    const fetchIngredients = async () => {
      try {
        const response = await fetch("/api/ingredients");
        const data = await response.json();
        setIngredients(data);
      } catch (err) {
        console.error("Error fetching ingredients:", err);
        setError("Eroare la incarcarea ingredientelor. Incearca mai tarziu.");
      }
    };

    fetchCategories();
    fetchIngredients();
  }, []);

  useEffect(()=>{
    console.log(recipe)
  },[recipe])

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRecipe({ ...recipe, [name]: value });
  };

  const handleIngredientChange = (index, e) => {
    const { name, value } = e.target;
    
    const updatedIngredients = [...recipe.ingredients];
    updatedIngredients[index] = { ...updatedIngredients[index], [name]: value };
    setRecipe({ ...recipe, ingredients: updatedIngredients });
    
    if (error) {
      setError(null);
    }
  };

  const addIngredient = () => {
    setRecipe({
      ...recipe,
      ingredients: [...recipe.ingredients, { ingredientId: "", quantity: "" }],
    });
  };

  const removeIngredient = (index) => {
    const updatedIngredients = recipe.ingredients.filter((_, i) => i !== index);
    setRecipe({ ...recipe, ingredients: updatedIngredients });
    
    // Clear error cand userul face o schimbare
    if (error) {
      setError(null);
    }
  };

  const handleImageSelected = (file, previewUrl) => {
    setImageFile(file);
    setRecipe({ ...recipe, photoUri: previewUrl });
  };


  const getAvailableIngredients = () => {
    // sterge ultimul ingred pt ca acum il fac
    const recipeIngredients = recipe?.ingredients?.slice(0, -1) || [];
    // ingredientsList va avea ingred care nu se afla in recipeIngredients, adica in recipe
    const ingredientsList=
         ingredients.filter(ingredient =>
      !recipeIngredients?.find(x =>x.ingredientId === ingredient.id)
    );
  
    return ingredientsList
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!recipe.name || !recipe.categoryId || recipe.ingredients.length === 0) {
      setError("Completati toate campurile");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      // Cream un obiect FormData 
      const formData = new FormData();

      // Daca avem un image file, il adaugam la formData
      if (imageFile) {
        formData.append("image", imageFile);
      } else {
        setError("Incarcati o imagine");
        setLoading(false);
        return;
      }

      // Cream obiectul recipeData
      const recipeData = {
        name: recipe.name,
        description: recipe.description,
        preparationTime: recipe.preparationTime,
        categoryId: recipe.categoryId,
        recipeIngredients: recipe.ingredients.map((i) => ({
          ingredientId: i.ingredientId,
          quantity: i.quantity,
        })),
      };

      // Adaugam reteta in formData
      formData.append("recipe", JSON.stringify(recipeData));

      const response = await fetch("/api/recipes", {
        method: "POST",
        // Nu e nevoie sa setam Content-Type header, browser-ul il va seta
        body: formData,
      });

      if (!response.ok) {
        throw new Error("Esuare in crearea retetei");
      }

      navigate("/");
    } catch (err) {
      console.error("Eroare la crearea retetei:", err);
      setError("Eroare la crearea retetei. Incearca din nou.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Adauga reteta</h1>

      {error && <div className={styles.error}>{error}</div>}

      <form onSubmit={handleSubmit} className={styles.form}>
        <div className={styles.formGroup}>
          <label htmlFor="name" className={styles.label}>
            Denumire reteta*
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
          <label htmlFor="description" className={styles.label}>
            Descriere
          </label>
          <textarea
            id="description"
            name="description"
            value={recipe.description}
            onChange={handleChange}
            className={styles.textarea}
            rows="4"
          />
        </div>

        <div className={styles.formGroup}>
          <label className={styles.label}>Imagine reteta*</label>
          <ImageUpload onImageSelected={handleImageSelected} />
        </div>

        <div className={styles.formGroup}>
          <label htmlFor="preparationTime" className={styles.label}>
            Timp de preparare
          </label>
          <input
            type="text"
            id="preparationTime"
            name="preparationTime"
            value={recipe.preparationTime}
            onChange={handleChange}
            className={styles.input}
            placeholder="ex. 30 minute"
          />
        </div>

        <div className={styles.formGroup}>
          <label htmlFor="categoryId" className={styles.label}>
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
            <option value="">Selecteaza o categorie</option>
            {categories.map((category) => (
              <option key={category.id} value={category.id}>
                {category.name}
              </option>
            ))}
          </select>
        </div>

        <div className={styles.ingredientsSection}>
          <h2 className={styles.subtitle}>Ingrediente*</h2>

          {recipe.ingredients.map((ingredient, index) => (
            <div key={index} className={styles.ingredientRow}>
              <div className={styles.ingredientInputs}>
                <select
                  name="ingredientId"
                  value={ingredient.ingredientId}
                  onChange={(e) => handleIngredientChange(index, e)}
                  className={styles.ingredientInput}
                  required
                >
                  {/* optiunile pe care le-am selectat pana acum */}
                  <option value="">Selecteaza ingredient</option>
                  {index<recipe.ingredients.length-1? ingredients?.map((ing) => (
                    <option key={ing.id} value={ing.id}>
                      {ing.name}
                    </option>
                  )):getAvailableIngredients().map((ing)=>(
                    <option key={ing.id} value={ing.id}>
                    {ing.name}
                  </option>
                  ))

                  }

                </select>
                <input
                  type="text"
                  name="quantity"
                  value={ingredient.quantity}
                  onChange={(e) => handleIngredientChange(index, e)}
                  placeholder="Cantitate (ex., 2 g)"
                  className={styles.ingredientInput}
                  required
                />
              </div>

              {recipe.ingredients.length > 1 && (
                <button
                  type="button"
                  onClick={() => removeIngredient(index)}
                  className={styles.removeBtn}
                >
                  Sterge
                </button>
              )}
            </div>
          ))}

          <button
            type="button"
            onClick={addIngredient}
            className={styles.addBtn}
            disabled={recipe.ingredients.length >= ingredients.length}
          >
            Adauga ingredient
          </button>
        </div>

        <div className={styles.formActions}>
          <button type="submit" className={styles.submitBtn} disabled={loading}>
            {loading ? "Se salveaza..." : "Salveaza reteta"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddRecipe;