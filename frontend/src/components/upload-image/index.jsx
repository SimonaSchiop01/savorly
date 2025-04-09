import React, { useState } from "react";
import styles from "./styles.module.css";

const ImageUpload = ({ onImageSelected }) => {
  // pastram URL-ul imaginii pt a fi previzualizata
  const [imagePreview, setImagePreview] = useState("");

  const handleFileChange = (event) => {
    const file = event.target.files[0];

    // daca a fost selectat un fisier
    if (file) {
      // Cream URL temporar pt previzualizare
      const previewUrl = URL.createObjectURL(file);
      setImagePreview(previewUrl);

      // trimitem fisierul si URL preview-ul parintelui
      onImageSelected(file, previewUrl);
    }
  };

  const handleRemoveImage = () => {
    setImagePreview("");
    onImageSelected(null, "");
  };

  return (
    <div className={styles.container}>
      {/* arata previzualizarea imaginii daca o imagine a fost selectata */}
      {imagePreview && (
        <div className={styles.previewContainer}>
          <img
            src={imagePreview}
            alt="Recipe preview"
            className={styles.preview}
          />
          <button
            type="button"
            onClick={handleRemoveImage}
            className={styles.removeButton}
          >
            Sterge imaginea
          </button>
        </div>
      )}

      {/* daca nici o imagine nu a fost selectata, afisam butonul de upload */}
      {!imagePreview && (
        <label className={styles.uploadButton}>
          Selecteaza imagine
          <input
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            className={styles.fileInput}
          />
        </label>
      )}
    </div>
  );
};

export default ImageUpload;
