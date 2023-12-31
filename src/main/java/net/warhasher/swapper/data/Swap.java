package net.warhasher.swapper.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Swap {
    private UUID id;
    private UUID inId;
    private UUID outId;
    private UUID developerId;
}
