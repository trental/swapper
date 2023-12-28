package net.warhasher.swapper.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.warhasher.swapper.entity.Developer;
import net.warhasher.swapper.entity.Inventory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeveloperCustomRepository {

    @Autowired
    EntityManager entityManager;

    public Optional<Developer> getDeveloperWithInventory(UUID developerId){
        Optional<Developer> developer = Optional.ofNullable(entityManager.find(Developer.class, developerId));

        if (developer != null) {
            Hibernate.initialize(developer.get().getInventory());
        }

        return developer;
    }
}
