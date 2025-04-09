package savorly.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="ingredients")
public class IngredientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @OneToMany(
        mappedBy = "ingredient",
        cascade = CascadeType.ALL, // CascadeType.REMOVE
            orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<RecipeIngredientModel> recipesIngredients = new HashSet<>();
}
