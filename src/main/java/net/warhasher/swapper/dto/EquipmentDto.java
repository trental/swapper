package net.warhasher.swapper.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.warhasher.swapper.data.EquipmentType;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto {

    private UUID id;

    @NotEmpty(message = "Equipment type should not be null or empty")
    private EquipmentType type;

    @NotEmpty(message = "Equipment name should not be null or empty")
    private String name;
}
