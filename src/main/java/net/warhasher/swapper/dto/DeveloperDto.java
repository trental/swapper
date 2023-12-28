package net.warhasher.swapper.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperDto {

    private UUID id;

    @NotEmpty(message = "Developer name should not be null or empty")
    private String name;

    private Set<InventoryDto> inventory;
}
