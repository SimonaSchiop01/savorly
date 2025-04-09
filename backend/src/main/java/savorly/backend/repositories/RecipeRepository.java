package savorly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import savorly.backend.models.RecipeModel;

import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<RecipeModel, UUID> {
    Optional<RecipeModel> findByName(String name);
}
