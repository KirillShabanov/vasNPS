/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  
 * 
 *  Date of remmark: 27/07/2023
 */
package com.home.MyWorkTime.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="vas_check_list_engineer_kia")
@Getter
@Setter
@ToString

public class VasCheckListEngineerKiaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "num_order_check_kia")
    private Long numOrderCheckKia;
    @Column(name = "surname_engineer_kia")
    private String surnameEngineerKia;
    @Column(name = "date_save_inspection")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "dd.MM.yyyy, HH:mm:ss")
    private LocalDateTime dateSaveInspection;
    @Column(name = "in_work")
    private String inWork;
    @Column(name = "reception_question_1")
    private String receptionQuestion1;
    @Column(name = "reception_question_2")
    private String receptionQuestion2;
    @Column(name = "reception_question_3")
    private String receptionQuestion3;
    @Column(name = "reception_question_4")
    private String receptionQuestion4;
    @Column(name = "reception_question_5")
    private String receptionQuestion5;
    @Column(name = "reception_question_6")
    private String receptionQuestion6;
    @Column(name = "reception_question_7")
    private String receptionQuestion7;
    @Column(name = "reception_question_8")
    private String receptionQuestion8;
    @Column(name = "reception_question_9")
    private String receptionQuestion9;
    @Column(name = "reception_question_10")
    private String receptionQuestion10;
    @Column(name = "reception_question_11")
    private String receptionQuestion11;
    @Column(name = "reception_question_12")
    private String receptionQuestion12;
    @Column(name = "reception_question_13")
    private String receptionQuestion13;
    @Column(name = "reception_question_14")
    private String receptionQuestion14;
    @Column(name = "reception_question_15")
    private String receptionQuestion15;
    @Column(name = "reception_question_16")
    private String receptionQuestion16;

    @Column(name = "inspection_question_1")
    private String inspectionQuestion1;
    @Column(name = "inspection_question_2")
    private String inspectionQuestion2;
    @Column(name = "inspection_question_3")
    private String inspectionQuestion3;
    @Column(name = "inspection_question_4")
    private String inspectionQuestion4;
    @Column(name = "inspection_question_5")
    private String inspectionQuestion5;
    @Column(name = "inspection_question_6")
    private String inspectionQuestion6;

    @Column(name = "waiting_question_1")
    private String waitingQuestion1;
    @Column(name = "waiting_question_2")
    private String waitingQuestion2;
    @Column(name = "waiting_question_3")
    private String waitingQuestion3;

    @Column(name = "issuing_question_1")
    private String issuingQuestion1;
    @Column(name = "issuing_question_2")
    private String issuingQuestion2;
    @Column(name = "issuing_question_3")
    private String issuingQuestion3;
    @Column(name = "issuing_question_4")
    private String issuingQuestion4;
    @Column(name = "issuing_question_5")
    private String issuingQuestion5;
    @Column(name = "issuing_question_6")
    private String issuingQuestion6;
    @Column(name = "issuing_question_7")
    private String issuingQuestion7;
    @Column(name = "issuing_question_8")
    private String issuingQuestion8;
    @Column(name = "result")
    private int result;

}
