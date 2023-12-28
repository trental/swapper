package net.warhasher.swapper.repository;

import net.warhasher.swapper.entity.SwapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SwapRepository extends JpaRepository<SwapEntity, UUID> {
}
