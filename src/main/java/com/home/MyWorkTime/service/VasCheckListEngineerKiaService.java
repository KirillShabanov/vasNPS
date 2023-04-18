package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasCheckListEngineerKiaModel;
import com.home.MyWorkTime.repository.VasCheckListEngineerKiaRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static java.lang.String.valueOf;

@Slf4j
@Service
@NoArgsConstructor
public class VasCheckListEngineerKiaService {

    private VasCheckListEngineerKiaRepository vasCheckListEngineerKiaRepository;
    private final HashMap<Object, Object> analyticsKiaEngineerGeneralIndicator = new HashMap<>();


    private static final Logger LOGGER = Logger.getLogger(VasCheckListEngineerKiaService.class.getName());

    @Autowired
    public VasCheckListEngineerKiaService (VasCheckListEngineerKiaRepository vasCheckListEngineerKiaRepository){
        this.vasCheckListEngineerKiaRepository = vasCheckListEngineerKiaRepository;
    }

    public List<VasCheckListEngineerKiaModel> findAllCheckListNotCancel(){
        LOGGER.log(Level.INFO, "Отображён не полный чек-лист ИТ KIA: ");
        return vasCheckListEngineerKiaRepository.findAllCheckListNotCancel();
    }

    public List<VasCheckListEngineerKiaModel> findAllCheckListNotCancelFromNum(String numOrderCheckKia) {
        LOGGER.log(Level.INFO, "Запрос не полный чек-лист ИТ KIA: " + numOrderCheckKia);
        return vasCheckListEngineerKiaRepository.findAllCheckListNotCancelFromNum(numOrderCheckKia);
    }

    public VasCheckListEngineerKiaModel saveCheckListEngineerKia(@NotNull VasCheckListEngineerKiaModel vasCheckListEngineerKiaModel){
        String q1 = vasCheckListEngineerKiaModel.getReceptionQuestion1();
        String q2 = vasCheckListEngineerKiaModel.getReceptionQuestion2();
        String q3 = vasCheckListEngineerKiaModel.getReceptionQuestion3();
        String q4 = vasCheckListEngineerKiaModel.getReceptionQuestion4();
        String q5 = vasCheckListEngineerKiaModel.getReceptionQuestion5();
        String q6 = vasCheckListEngineerKiaModel.getReceptionQuestion6();
        String q7 = vasCheckListEngineerKiaModel.getReceptionQuestion7();
        String q8 = vasCheckListEngineerKiaModel.getReceptionQuestion8();
        String q9 = vasCheckListEngineerKiaModel.getReceptionQuestion9();
        String q10 = vasCheckListEngineerKiaModel.getReceptionQuestion10();
        String q11 = vasCheckListEngineerKiaModel.getReceptionQuestion11();
        String q12 = vasCheckListEngineerKiaModel.getReceptionQuestion12();
        String q13 = vasCheckListEngineerKiaModel.getReceptionQuestion13();
        String q14 = vasCheckListEngineerKiaModel.getReceptionQuestion14();
        String q15 = vasCheckListEngineerKiaModel.getReceptionQuestion15();
        String q16 = vasCheckListEngineerKiaModel.getReceptionQuestion16();

        String q17 = vasCheckListEngineerKiaModel.getInspectionQuestion1();
        String q18 = vasCheckListEngineerKiaModel.getInspectionQuestion2();
        String q19 = vasCheckListEngineerKiaModel.getInspectionQuestion3();
        String q20 = vasCheckListEngineerKiaModel.getInspectionQuestion4();
        String q21 = vasCheckListEngineerKiaModel.getInspectionQuestion5();
        String q22 = vasCheckListEngineerKiaModel.getInspectionQuestion6();

        String q23 = vasCheckListEngineerKiaModel.getWaitingQuestion1();
        String q24 = vasCheckListEngineerKiaModel.getWaitingQuestion2();
        String q25 = vasCheckListEngineerKiaModel.getWaitingQuestion3();

        String q26 = vasCheckListEngineerKiaModel.getIssuingQuestion1();
        String q27 = vasCheckListEngineerKiaModel.getIssuingQuestion2();
        String q28 = vasCheckListEngineerKiaModel.getIssuingQuestion3();
        String q29 = vasCheckListEngineerKiaModel.getIssuingQuestion4();
        String q30 = vasCheckListEngineerKiaModel.getIssuingQuestion5();
        String q31 = vasCheckListEngineerKiaModel.getIssuingQuestion6();
        String q32 = vasCheckListEngineerKiaModel.getIssuingQuestion7();
        String q33 = vasCheckListEngineerKiaModel.getIssuingQuestion8();

        int rq1 = 0;
        int rq2 = 0;
        int rq3 = 0;
        int rq4 = 0;
        int rq5 = 0;
        int rq6 = 0;
        int rq7 = 0;
        int rq8 = 0;
        int rq9 = 0;
        int rq10 = 0;
        int rq11 = 0;
        int rq12 = 0;
        int rq13 = 0;
        int rq14 = 0;
        int rq15 = 0;
        int rq16 = 0;
        int rq17 = 0;
        int rq18 = 0;
        int rq19 = 0;
        int rq20 = 0;
        int rq21 = 0;
        int rq22 = 0;
        int rq23 = 0;
        int rq24 = 0;
        int rq25 = 0;
        int rq26 = 0;
        int rq27 = 0;
        int rq28 = 0;
        int rq29 = 0;
        int rq30 = 0;
        int rq31 = 0;
        int rq32 = 0;
        int rq33 = 0;

        if (q1.equals("YES") || q1.equals("NA")) rq1 = 3;
        if (q2.equals("YES")) rq2 = 3;
        if (q3.equals("YES")) rq3 = 3;
        if (q4.equals("YES")) rq4 = 3;
        if (q5.equals("YES")) rq5 = 3;
        if (q6.equals("YES")) rq6 = 3;
        if (q7.equals("YES")) rq7 = 3;
        if (q8.equals("YES")) rq8 = 3;
        if (q9.equals("YES")) rq9 = 3;
        if (q10.equals("YES")) rq10 = 3;
        if (q11.equals("YES")) rq11 = 3;
        if (q12.equals("YES")) rq12 = 3;
        if (q13.equals("YES")) rq13 = 3;
        if (q14.equals("YES")) rq14 = 3;
        if (q15.equals("YES")) rq15 = 3;
        if (q16.equals("YES")) rq16 = 3;
        if (q17.equals("YES")) rq17 = 3;
        if (q18.equals("YES") || q18.equals("NA")) rq18 = 3;
        if (q19.equals("YES") || q19.equals("NA")) rq19 = 3;
        if (q20.equals("YES")) rq20 = 3;
        if (q21.equals("YES") || q21.equals("NA")) rq21 = 3;
        if (q22.equals("YES")) rq22 = 3;
        if (q23.equals("YES")) rq23 = 3;
        if (q24.equals("YES") || q24.equals("NA")) rq24 = 3;
        if (q25.equals("YES") || q25.equals("NA")) rq25 = 3;
        if (q26.equals("YES")) rq26 = 3;
        if (q27.equals("YES")) rq27 = 4;
        if (q28.equals("YES") || q28.equals("NA")) rq28 = 3;
        if (q29.equals("YES") || q29.equals("NA")) rq29 = 3;
        if (q30.equals("YES")) rq30 = 3;
        if (q31.equals("YES")) rq31 = 3;
        if (q32.equals("YES")) rq32 = 3;
        if (q33.equals("YES")) rq33 = 3;

        int result = rq1 + rq2 + rq3 + rq4 + rq5 + rq6 + rq7 + rq8 + rq9 + rq10 + rq11 + rq12 + rq13 + rq14 + rq15 + rq16 +
                rq17 + rq18 + rq19 + rq20 + rq21 + rq22 + rq23 + rq24 + rq25 + rq26 + rq27 + rq28 + rq29 + rq30 + rq31 + rq32 + rq33;

        vasCheckListEngineerKiaModel.setResult(valueOf(result));
        VasCheckListEngineerKiaModel saveCheckListEngineerKia = vasCheckListEngineerKiaRepository.save(vasCheckListEngineerKiaModel);
        LOGGER.log(Level.INFO, "Сохранён чек-лист ИТ KIA: ");
        return VasCheckListEngineerKiaRepository.saveCheckListEngineerKia(saveCheckListEngineerKia);

    }

    public long analyticsKiaEngineerCountReportCancel(){return vasCheckListEngineerKiaRepository.analyticsKiaEngineerCountReportCancel();}

    public long analyticsKiaEngineerCountReportNotCancel(){return vasCheckListEngineerKiaRepository.analyticsKiaEngineerCountReportNotCancel();}


    public HashMap<Object, Object> analyticsKiaEngineerGeneralIndicator() {

        String[] analyticsKiaEngineerGeneralIndicatorSurname = vasCheckListEngineerKiaRepository.analyticsKiaEngineerGeneralIndicatorSurname();

        for (int i = 0; i < analyticsKiaEngineerGeneralIndicatorSurname.length; i++) {

            HashMap<String,String> analyticsKiaEngineerGeneralIndicatorValue = new HashMap<>();

            int countReportGeneralIndicator = vasCheckListEngineerKiaRepository.analyticsKiaEngineerGeneralIndicatorCountReport(analyticsKiaEngineerGeneralIndicatorSurname[i]);
            int numberOfPoints = vasCheckListEngineerKiaRepository.numberOfPoints(analyticsKiaEngineerGeneralIndicatorSurname[i]);
            int gpa = numberOfPoints / countReportGeneralIndicator;

            analyticsKiaEngineerGeneralIndicatorValue.put("surnameReportKia", analyticsKiaEngineerGeneralIndicatorSurname[i]);
            analyticsKiaEngineerGeneralIndicatorValue.put("countReportGeneralIndicator", String.valueOf(countReportGeneralIndicator));
            analyticsKiaEngineerGeneralIndicatorValue.put("gpa", String.valueOf(gpa));

            analyticsKiaEngineerGeneralIndicator.put(i, analyticsKiaEngineerGeneralIndicatorValue);
        }

        return analyticsKiaEngineerGeneralIndicator;
    }

    public List<VasCheckListEngineerKiaModel> findAllCheckListCancel() {
        return vasCheckListEngineerKiaRepository.findAllCheckListCancel();
    }

    public List<VasCheckListEngineerKiaModel> findAllCheckListCancelFromNum(String numOrderCheckKia) {
        LOGGER.log(Level.INFO, "Запрос не полный чек-лист ИТ KIA: " + numOrderCheckKia);
        return vasCheckListEngineerKiaRepository.findAllCheckListCancelFromNum(numOrderCheckKia);
    }
}
