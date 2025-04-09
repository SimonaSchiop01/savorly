package savorly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import savorly.backend.models.IngredientModel;

import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<IngredientModel, UUID> {
    Optional<IngredientModel> findByName(String name);

}
