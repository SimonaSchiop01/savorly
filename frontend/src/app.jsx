import Home from "./pages/Home";
import Layout from "./pages/Layout";
import AddRecipe from "./pages/add-recipe";
import EditRecipe from "./pages/edit-recipe";
import RecipePage from "./pages/RecipePage";
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/add-recipe" element={<AddRecipe />} />
          <Route path="/recipes/:id" element={<RecipePage />} />
          <Route path="/edit-recipe/:id" element={<EditRecipe />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
