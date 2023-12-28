package net.warhasher.swapper.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.warhasher.swapper.entity.Developer;
import net.warhasher.swapper.entity.Equipment;
import net.warhasher.swapper.entity.Inventory;
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

        Inventory currentEquipmentInventory = entityManager.find(Inventory.class, inventoryKey);

        if (currentEquipmentInventory == null && quantity > 0) {
            Inventory newInventory = new Inventory();

            Developer developer = entityManager.find(Developer.class, developerId);
            Equipment equipment = entityManager.find(Equipment.class, equipmentId);

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
}
