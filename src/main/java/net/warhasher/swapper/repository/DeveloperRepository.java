package net.warhasher.swapper.repository;

import net.warhasher.swapper.entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeveloperRepository extends JpaRepository<DeveloperEntity, UUID> {

}
