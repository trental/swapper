package net.warhasher.swapper.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.warhasher.swapper.entity.DeveloperEntity;
import net.warhasher.swapper.entity.EquipmentEntity;
import net.warhasher.swapper.entity.InventoryEntity;
import net.warhasher.swapper.entity.InventoryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryRepository {

    @Autowired
    EntityManager entityManager;

    public void setInventory(UUID developerId, UUID equipmentId, Integer quantity){
        InventoryKey inventoryKey = new InventoryKey();
        inventoryKey.setDeveloperId(developerId);
        inventoryKey.setEquipmentId(equipmentId);

        InventoryEntity currentEquipmentInventory = entityManager.find(InventoryEntity.class, inventoryKey);

        if (currentEquipmentInventory == null && quantity > 0) {
            InventoryEntity newInventory = new InventoryEntity();

            DeveloperEntity developer = entityManager.find(DeveloperEntity.class, developerId);
            EquipmentEntity equipment = entityManager.find(EquipmentEntity.class, equipmentId);

            newInventory.setId(inventoryKey);
            newInventory.setQuantity(quantity);
            newInventory.setDeveloper(developer);
            newInventory.setEquipment(equipment);

            entityManager.persist(newInventory);
        } else {
            if (quantity > 0) {

                currentEquipmentInventory.setQuantity(quantity);
                entityManager.merge(currentEquipmentInventory);
            } else {
                if (currentEquipmentInventory != null) {
                    entityManager.remove(currentEquipmentInventory);
                }
            }
        }
    }

    public InventoryEntity getInventory(UUID developerId, UUID equipmentId){
        InventoryKey inventoryKey = new InventoryKey();
        inventoryKey.setDeveloperId(developerId);
        inventoryKey.setEquipmentId(equipmentId);

        return entityManager.find(InventoryEntity.class, inventoryKey);
    }
}
