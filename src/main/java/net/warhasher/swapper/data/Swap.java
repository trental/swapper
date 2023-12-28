package net.warhasher.swapper.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Swap {
    private final UUID id;
    private final UUID inId;
    private final UUID outId;
    private final UUID developerId;
}
