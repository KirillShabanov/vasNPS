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
package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasCheckListEngineerKiaModel;
import com.home.MyWorkTime.repository.VasCheckListEngineerKiaRepository;
import lombok.NoArgsConstructor;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;


@Service
@NoArgsConstructor
public class VasCheckListEngineerKiaService {

    private VasCheckListEngineerKiaRepository vasCheckListEngineerKiaRepository;

    private static final Logger LOGGER = Logger.getLogger(VasCheckListEngineerKiaService.class.getName());

    @Autowired
    public VasCheckListEngineerKiaService (VasCheckListEngineerKiaRepository vasCheckListEngineerKiaRepository){
        this.vasCheckListEngineerKiaRepository = vasCheckListEngineerKiaRepository;
    }

    public List<VasCheckListEngineerKiaModel> findAllCheckListNotCancel(){
        //LOGGER.log(Level.INFO, "Отображён не полный чек-лист ИТ KIA: ");
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

        vasCheckListEngineerKiaModel.setResult(result);
        VasCheckListEngineerKiaModel saveCheckListEngineerKia = vasCheckListEngineerKiaRepository.save(vasCheckListEngineerKiaModel);
        LOGGER.log(Level.INFO, "Сохранён чек-лист ИТ KIA: " + vasCheckListEngineerKiaModel.getNumOrderCheckKia());
        return VasCheckListEngineerKiaRepository.saveCheckListEngineerKia(saveCheckListEngineerKia);

    }

    public long analyticsKiaEngineerCountReportCancel(){return vasCheckListEngineerKiaRepository.analyticsKiaEngineerCountReportCancel();}

    public long analyticsKiaEngineerCountReportNotCancel(){return vasCheckListEngineerKiaRepository.analyticsKiaEngineerCountReportNotCancel();}

    public HashMap<Object, Object> analyticsKiaEngineerGeneralIndicator() {

        HashMap<Object, Object> analyticsKiaEngineerGeneralIndicator = new HashMap<>();

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

    public List<VasCheckListEngineerKiaModel> countReportsKiaEngineerPeriod(String periodCheckListEngineerSurname, String periodCheckListEngineerDateFrom, String periodCheckListEngineerDateBy) throws ParseException{

        if ((periodCheckListEngineerDateFrom).equals("")){
            periodCheckListEngineerDateFrom = "2023-01-01";
        }

        if ((periodCheckListEngineerDateBy).equals("")){
            LocalDate date = LocalDate.now();
            periodCheckListEngineerDateBy = date.toString();
        }
        
        LOGGER.log(Level.INFO, "Проведен запрос чек-листов по ИТ: " + periodCheckListEngineerSurname + ", период с " + periodCheckListEngineerDateFrom + " по " + periodCheckListEngineerDateBy);
        return vasCheckListEngineerKiaRepository.countReportsKiaEngineerPeriod(periodCheckListEngineerSurname, periodCheckListEngineerDateFrom, periodCheckListEngineerDateBy);
    }

    public FileSystemResource createReportsKiaEngineerPeriod(String periodCheckListEngineerSurname,
            String periodCheckListEngineerDateFrom, String periodCheckListEngineerDateBy) throws IOException {
                FileInputStream templatePeriodKiaEngineerOrder = new FileInputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\reportCheckListEngineer.xlsx");
                //"C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\reports\\reportCheckListEngineer.xlsx"
                //"C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\reportCheckListEngineer.xlsx"
                try (XSSFWorkbook report = new XSSFWorkbook(templatePeriodKiaEngineerOrder)) {

                    XSSFSheet checkListEngineer = report.getSheetAt(0);

                    //Запрос полной модели по параметрам
                    List<VasCheckListEngineerKiaModel> countReportFromPeriod = vasCheckListEngineerKiaRepository.countReportsKiaEngineerPeriod(periodCheckListEngineerSurname, periodCheckListEngineerDateFrom, periodCheckListEngineerDateBy);
                    // Имя ИТ
                    checkListEngineer.getRow(1).getCell(3).setCellValue(periodCheckListEngineerSurname);
                    //Кол-во проверок
                    int countReport = countReportFromPeriod.size();
                    checkListEngineer.getRow(2).getCell(3).setCellValue(countReport);
                    // Дата с
                    checkListEngineer.getRow(3).getCell(3).setCellValue(periodCheckListEngineerDateFrom);
                    // Дата по
                    checkListEngineer.getRow(3).getCell(5).setCellValue(periodCheckListEngineerDateBy);
                    // Увеличение количества столбцов (ответы)
                    for(int i = 1; i < countReportFromPeriod.size(); i++){
                       for(int j = 1; j < 41; j++){
                           XSSFCell oldCell = checkListEngineer.getRow(j).getCell(11);
                           XSSFCell newCell = checkListEngineer.getRow(j).createCell(11+i);
                           newCell.copyCellFrom(oldCell, new CellCopyPolicy());
                       } 
                    }

                     // Заполняем отчёт исходя из количества найденных чек-листов
                     for(int i = 0; i < countReportFromPeriod.size(); i++){
                        
                        checkListEngineer.getRow(1).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getDateSaveInspection());
                        checkListEngineer.getRow(2).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getNumOrderCheckKia());
                        checkListEngineer.getRow(3).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getResult());
                        //Результаты по приемке
                        checkListEngineer.getRow(5).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion1());
                        checkListEngineer.getRow(6).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion2());
                        checkListEngineer.getRow(7).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion3());
                        checkListEngineer.getRow(8).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion4());
                        checkListEngineer.getRow(9).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion5());
                        checkListEngineer.getRow(10).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion6());
                        checkListEngineer.getRow(11).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion7());
                        checkListEngineer.getRow(12).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion8());
                        checkListEngineer.getRow(13).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion9());
                        checkListEngineer.getRow(14).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion10()); 
                        checkListEngineer.getRow(15).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion11()); 
                        checkListEngineer.getRow(16).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion12());
                        checkListEngineer.getRow(17).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion13());
                        checkListEngineer.getRow(18).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion14());
                        checkListEngineer.getRow(19).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion15());
                        checkListEngineer.getRow(20).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getReceptionQuestion16());
                        //Результаты по осмотру
                        checkListEngineer.getRow(22).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getInspectionQuestion1());
                        checkListEngineer.getRow(23).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getInspectionQuestion2());
                        checkListEngineer.getRow(24).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getInspectionQuestion3());
                        checkListEngineer.getRow(25).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getInspectionQuestion4());
                        checkListEngineer.getRow(26).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getInspectionQuestion5());
                        checkListEngineer.getRow(27).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getInspectionQuestion6());
                        //Результаты по ожиданию
                        checkListEngineer.getRow(29).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getWaitingQuestion1());
                        checkListEngineer.getRow(30).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getWaitingQuestion2());
                        checkListEngineer.getRow(31).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getWaitingQuestion3());
                        //Результаты по выдаче авто
                        checkListEngineer.getRow(33).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getIssuingQuestion1());
                        checkListEngineer.getRow(34).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getIssuingQuestion2());
                        checkListEngineer.getRow(35).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getIssuingQuestion3());
                        checkListEngineer.getRow(36).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getIssuingQuestion4());
                        checkListEngineer.getRow(37).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getIssuingQuestion5());
                        checkListEngineer.getRow(38).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getIssuingQuestion6());
                        checkListEngineer.getRow(39).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getIssuingQuestion7());
                        checkListEngineer.getRow(40).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getIssuingQuestion8());
                        
                     }

                    String reportName = "Чек-лист ИТ за период.xlsx";
                    FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\CheckList Kia\\Engineer\\" + reportName);
                    //"C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\static\\outputReports\\kiaEngineerReport\\" 
                    //"C:\\Users\\Shabanov\\Desktop\\Shabanov\\NPS vas Server\\VAS-NPS\\src\\main\\resources\\static\\outputReports\\kiaEngineerReport\\reportCheckListEngineer.xlsx"
                    //C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\CheckList Kia\\Engineer\\
                    report.write(fileOut);
                    fileOut.close();
                    
                    FileSystemResource order = new FileSystemResource("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\CheckList Kia\\Engineer\\" + reportName);
                    
                    return order;
                }
        }
}
