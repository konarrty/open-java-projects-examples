package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.enums.EquipmentStatus;
import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import com.example.constructionmanagementspring.model.Assets;
import com.example.constructionmanagementspring.model.Equipment;
import com.example.constructionmanagementspring.model.Provider;
import com.example.constructionmanagementspring.repository.EquipmentRepository;
import com.example.constructionmanagementspring.service.AssetsService;
import com.example.constructionmanagementspring.service.EquipmentService;
import com.example.constructionmanagementspring.service.qr.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    private final AssetsService assetsService;
    private final QRCodeService qrCodeService;


    @Override
    public List<Equipment> getAllEquipments(int page) {

        return equipmentRepository.findAll(
                        PageRequest.of(page - 1, 5))
                .getContent();
    }

    @Override
    public Equipment createEquipment(Equipment equipment) {
        System.err.println(equipment);
        equipment = equipment.toBuilder()
                .qrImage(qrCodeService.createQRCode("В данный момент оборудование находится на складе"))
                .build();
        equipment.getAssets().setEquipmentStatus(EquipmentStatus.PENDING);
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment sendToWarehouse(Long equipmentId) {

        Equipment equipment = equipmentRepository.getReferenceById(equipmentId);
        equipment.getAssets().setEquipmentStatus(EquipmentStatus.IN_STOCK);
        return equipmentRepository.save(equipment);

    }

    @Override
    public double getAssetsSum() {

        return assetsService.getAllAssets().stream()
                .mapToDouble(Assets::getCoast)
                .sum();
    }

    @Override
    public double getAssetsSumPercentForWeek() {
        double sum = assetsService.getAllAssets().stream()
                .filter(assets ->
                        assets.getCreateDate().isAfter(
                                LocalDateTime.now().minusWeeks(1L)))
                .mapToDouble(Assets::getCoast)
                .sum();

        return sum / (getAssetsSum() - sum);
    }

    @Override
    public Equipment writeOffEquipment(Long equipmentId) {

        Equipment equipment = getEquipmentById(equipmentId);

        if (equipment.getAssets().getEquipmentStatus().equals(EquipmentStatus.WRITE_OFF))
            return null;

        equipment.getAssets().setEquipmentStatus(EquipmentStatus.WRITE_OFF);

        return equipmentRepository.save(equipment);
    }

    @Override
    public double getWriteOffAssets() {

        return (double) assetsService.getAllAssets().stream()
                .filter(assets ->
                        assets.getEquipmentStatus()
                                .equals(EquipmentStatus.WRITE_OFF))
                .filter(assets ->
                        assets.getCreateDate().isAfter(LocalDateTime.now().minusMonths(1L)))
                .count() / assetsService.getAllAssets().stream().count();
    }

    @Override
    public double getPercentWriteOffAssetsForMonth() {

        double count = (double) assetsService.getAllAssets().stream()
                .filter(assets ->
                        assets.getEquipmentStatus()
                                .equals(EquipmentStatus.WRITE_OFF))
                .filter(assets ->
                        assets.getCreateDate().isAfter(LocalDateTime.now().minusMonths(2L))
                && assets.getCreateDate().isBefore(LocalDateTime. now().minusMonths(1L)))
                .count() / assetsService.getAllAssets().stream().count();

        return count / (getAssetsSum() - count);
    }

    @Override
    public Equipment updateQr(String qr, Equipment equipment) {

        equipment.setQrImage(qr);

        return equipmentRepository.save(equipment);

    }

    @Override
    public Equipment putEquipment(Equipment newEquipment, Long equipmentId) {
        Equipment equipment = equipmentRepository
                .findById(equipmentId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Оборудование не найдено!"));

        newEquipment.setId(equipment.getId());

        return equipmentRepository.save(newEquipment);
    }

    @Override
    public void deleteEquipment(Long id) {
        if (!equipmentRepository.existsById(id))
            throw new NoSuchEntityException("Оборудование не найдено!");

        equipmentRepository.deleteById(id);
    }

    @Override
    public Equipment getEquipmentById(Long id) {

        return equipmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchEntityException("Оборудование не найдено!"));
    }

    @Override
    public Equipment addProviderToEquipment(Long equipmentId, Provider provider) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() ->
                        new NoSuchEntityException("Оборудование не найдено!"));

        equipment.getAssets().setProvider(provider);

        return equipmentRepository.save(equipment);
    }
}
