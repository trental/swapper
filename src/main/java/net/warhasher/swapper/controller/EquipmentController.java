package net.warhasher.swapper.controller;

import lombok.AllArgsConstructor;
import net.warhasher.swapper.dto.EquipmentDto;
import net.warhasher.swapper.service.EquipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/equipment")
public class EquipmentController {

    private EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<EquipmentDto> createEquipment(@RequestBody EquipmentDto equipment){
        EquipmentDto savedEquipment = equipmentService.createEquipment(equipment);

        return new ResponseEntity<EquipmentDto>(savedEquipment, HttpStatus.CREATED);
    }
}
