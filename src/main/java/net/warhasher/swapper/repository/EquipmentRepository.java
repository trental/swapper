package net.warhasher.swapper.repository;

import net.warhasher.swapper.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EquipmentRepository extends JpaRepository<EquipmentEntity, UUID> {
}
