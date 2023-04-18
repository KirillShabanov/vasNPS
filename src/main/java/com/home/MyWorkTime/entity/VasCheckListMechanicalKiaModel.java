package com.home.MyWorkTime.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="vas_check_list_mechanical_kia")
@Getter
@Setter
@ToString
public class VasCheckListMechanicalKiaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "num_order_check_repair_kia")
    private Long numOrderCheckKiaRepair;
    @Column(name = "surname_check_repair_kia")
    private String surnameMechanicalKia;
    @Column(name = "vin_check_repair_kia")
    private String mechanicalKiaVin;
    @Column(name = "in_work_check_repair_kia")
    private String inWorkRepair;
    @Column(name = "date_save_repair_inspection")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "dd.MM.yyyy, HH:mm:ss")
    private LocalDateTime dateSaveRepairInspection;

    @Column(name = "repair_quality_control_1")
    private String repairQualityControl1;
    @Column(name = "repair_quality_control_2")
    private String repairQualityControl2;
    @Column(name = "repair_quality_control_3")
    private String repairQualityControl3;
    @Column(name = "repair_quality_control_4")
    private String repairQualityControl4;
    @Column(name = "repair_quality_control_5")
    private String repairQualityControl5;
    @Column(name = "repair_quality_control_6")
    private String repairQualityControl6;
    @Column(name = "repair_quality_control_7")
    private String repairQualityControl7;
    @Column(name = "repair_quality_control_8")
    private String repairQualityControl8;
    @Column(name = "repair_quality_control_9")
    private String repairQualityControl9;
    @Column(name = "repair_quality_control_10")
    private String repairQualityControl10;

    @Column(name = "parking_lot_check_1")
    private String parkingLotCheck1;
    @Column(name = "parking_lot_check_2")
    private String parkingLotCheck2;
    @Column(name = "parking_lot_check_3")
    private String parkingLotCheck3;
    @Column(name = "parking_lot_check_4")
    private String parkingLotCheck4;
    @Column(name = "parking_lot_check_5")
    private String parkingLotCheck5;
    @Column(name = "parking_lot_check_6")
    private String parkingLotCheck6;
    @Column(name = "parking_lot_check_7")
    private String parkingLotCheck7;
    @Column(name = "parking_lot_check_8")
    private String parkingLotCheck8;
    @Column(name = "parking_lot_check_9")
    private String parkingLotCheck9;
    @Column(name = "parking_lot_check_10")
    private String parkingLotCheck10;
    @Column(name = "parking_lot_check_11")
    private String parkingLotCheck11;

    @Column(name = "check_in_motion_1")
    private String checkInMotion1;
    @Column(name = "check_in_motion_2")
    private String checkInMotion2;
    @Column(name = "check_in_motion_3")
    private String checkInMotion3;
    @Column(name = "check_in_motion_4")
    private String checkInMotion4;

    @Column(name = "quality_control_1")
    private String qualityControl1;
    @Column(name = "notes_master")
    private String notesMaster;
    @Column(name = "notes_work")
    private String notesWork;
    @Column(name = "explanations_work")
    private String explanationsWork;
    @Column(name = "quality_control_5")
    private String qualityControl5;
}
