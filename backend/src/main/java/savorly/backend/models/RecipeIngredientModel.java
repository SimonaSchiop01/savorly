package savorly.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
    name="recipes_ingredients",
    uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "ingredient_id"})
)
public class RecipeIngredientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeModel recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private IngredientModel ingredient;

    @Column(nullable = false, length = 50)
    private String quantity;
}
