package savorly.backend.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class CategoryResponseDto {
     private UUID id;
     private String name;
}
