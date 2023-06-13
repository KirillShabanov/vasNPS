package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasCheckListMechanicalKiaModel;
import com.home.MyWorkTime.repository.VasCheckListMechanicalKiaRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@NoArgsConstructor
public class VasCheckListMechanicalKiaService {

    private VasCheckListMechanicalKiaRepository vasCheckListMechanicalKiaRepository;

    private static final Logger LOGGER = Logger.getLogger(VasCheckListMechanicalKiaService.class.getName());

    @Autowired
    public VasCheckListMechanicalKiaService (VasCheckListMechanicalKiaRepository vasCheckListMechanicalKiaRepository){
        this.vasCheckListMechanicalKiaRepository = vasCheckListMechanicalKiaRepository;
    }

    public List<VasCheckListMechanicalKiaModel> findAllCheckListNotCancelMechanical(){
        LOGGER.log(Level.INFO, "Отображён не полный чек-лист механика KIA: ");
        return vasCheckListMechanicalKiaRepository.findAllCheckListNotCancelMechanical();
    }

    public List<VasCheckListMechanicalKiaModel> findAllCheckListNotCancelFromNumMechanical(String numOrderCheckKia) {
        LOGGER.log(Level.INFO, "Запрос не полный чек-лист механика KIA: " + numOrderCheckKia);
        return vasCheckListMechanicalKiaRepository.findAllCheckListNotCancelFromNumMechanical(numOrderCheckKia);
    }

    public VasCheckListMechanicalKiaModel saveCheckListMechanicalKia(@NotNull VasCheckListMechanicalKiaModel vasCheckListMechanicalKiaModel){
        String q1 = vasCheckListMechanicalKiaModel.getRepairQualityControl1();
        String q2 = vasCheckListMechanicalKiaModel.getRepairQualityControl2();
        String q3 = vasCheckListMechanicalKiaModel.getRepairQualityControl3();
        String q4 = vasCheckListMechanicalKiaModel.getRepairQualityControl4();
        String q5 = vasCheckListMechanicalKiaModel.getRepairQualityControl5();
        String q6 = vasCheckListMechanicalKiaModel.getRepairQualityControl6();
        String q7 = vasCheckListMechanicalKiaModel.getRepairQualityControl7();
        String q8 = vasCheckListMechanicalKiaModel.getRepairQualityControl8();
        String q9 = vasCheckListMechanicalKiaModel.getRepairQualityControl9();
        String q10 = vasCheckListMechanicalKiaModel.getRepairQualityControl10();

        String q11 = vasCheckListMechanicalKiaModel.getParkingLotCheck1();
        String q12 = vasCheckListMechanicalKiaModel.getParkingLotCheck2();
        String q13 = vasCheckListMechanicalKiaModel.getParkingLotCheck3();
        String q14 = vasCheckListMechanicalKiaModel.getParkingLotCheck4();
        String q15 = vasCheckListMechanicalKiaModel.getParkingLotCheck5();
        String q16 = vasCheckListMechanicalKiaModel.getParkingLotCheck6();
        String q17 = vasCheckListMechanicalKiaModel.getParkingLotCheck7();
        String q18 = vasCheckListMechanicalKiaModel.getParkingLotCheck8();
        String q19 = vasCheckListMechanicalKiaModel.getParkingLotCheck9();
        String q20 = vasCheckListMechanicalKiaModel.getParkingLotCheck10();
        String q21 = vasCheckListMechanicalKiaModel.getParkingLotCheck11();

        String q22 = vasCheckListMechanicalKiaModel.getCheckInMotion1();
        String q23 = vasCheckListMechanicalKiaModel.getCheckInMotion2();
        String q24 = vasCheckListMechanicalKiaModel.getCheckInMotion3();
        String q25 = vasCheckListMechanicalKiaModel.getCheckInMotion4();

        String q26 = vasCheckListMechanicalKiaModel.getQualityControl1();

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

        if (q1.equals("YES")) rq1 = 3;
        if (q2.equals("YES") || q2.equals("NA")) rq2 = 3;
        if (q3.equals("YES")) rq3 = 3;
        if (q4.equals("YES")) rq4 = 3;
        if (q5.equals("YES")) rq5 = 3;
        if (q6.equals("YES")) rq6 = 5;
        if (q7.equals("YES")) rq7 = 5;
        if (q8.equals("YES")) rq8 = 5;
        if (q9.equals("YES")) rq9 = 3;
        if (q10.equals("YES")) rq10 = 5;
        if (q11.equals("YES")) rq11 = 2;
        if (q12.equals("YES")) rq12 = 2;
        if (q13.equals("YES")) rq13 = 2;
        if (q14.equals("YES")) rq14 = 2;
        if (q15.equals("YES")) rq15 = 2;
        if (q16.equals("YES")) rq16 = 2;
        if (q17.equals("YES")) rq17 = 2;
        if (q18.equals("YES")) rq18 = 2;
        if (q19.equals("YES")) rq19 = 2;
        if (q20.equals("YES")) rq20 = 2;
        if (q21.equals("YES")) rq21 = 2;
        if (q22.equals("YES")) rq22 = 5;
        if (q23.equals("YES")) rq23 = 5;
        if (q24.equals("YES")) rq24 = 5;
        if (q25.equals("YES")) rq25 = 5;
        if (q26.equals("YES")){
            rq26 = 20;
        } else if (q26.equals("NO")) {
            rq26 = -20;
        }

        int result = rq1 + rq2 + rq3 + rq4 + rq5 + rq6 + rq7 + rq8 + rq9 +
                rq10 + rq11 + rq12 + rq13 + rq14 + rq15 + rq16 + rq17 + rq18 + rq19 +
                rq20 + rq21 + rq22 + rq23 + rq24 + rq25 + rq26;

        vasCheckListMechanicalKiaModel.setResult(result);

        VasCheckListMechanicalKiaModel saveCheckListMechanicalKia = vasCheckListMechanicalKiaRepository.save(vasCheckListMechanicalKiaModel);
        LOGGER.log(Level.INFO, "Сохранён чек-лист механика KIA: ");
        return VasCheckListMechanicalKiaRepository.saveCheckListMechanicalKia(saveCheckListMechanicalKia);
    }

    public long analyticsKiaMechanicalCountReportCancel(){return vasCheckListMechanicalKiaRepository.analyticsKiaMechanicalCountReportCancel();}

    public long analyticsKiaMechanicalCountReportNotCancel(){return vasCheckListMechanicalKiaRepository.analyticsKiaMechanicalCountReportNotCancel();}

    public HashMap<Object, Object> analyticsKiaMechanicalGeneralIndicator() {

        HashMap<Object, Object> analyticsKiaMechanicalGeneralIndicator = new HashMap<>();

        String[] analyticsKiaMechanicalGeneralIndicatorSurname = vasCheckListMechanicalKiaRepository.analyticsKiaMechanicalGeneralIndicatorSurname();

        for (int i = 0; i < analyticsKiaMechanicalGeneralIndicatorSurname.length; i++) {

            HashMap<String,String> analyticsKiaMechanicalGeneralIndicatorValue = new HashMap<>();

            int countReportGeneralIndicator = vasCheckListMechanicalKiaRepository.analyticsKiaMechanicalGeneralIndicatorCountReport(analyticsKiaMechanicalGeneralIndicatorSurname[i]);
            int numberOfPoints = vasCheckListMechanicalKiaRepository.numberOfPoints(analyticsKiaMechanicalGeneralIndicatorSurname[i]);
            int gpa = numberOfPoints / countReportGeneralIndicator;

            analyticsKiaMechanicalGeneralIndicatorValue.put("surnameReportKia", analyticsKiaMechanicalGeneralIndicatorSurname[i]);
            analyticsKiaMechanicalGeneralIndicatorValue.put("countReportGeneralIndicator", String.valueOf(countReportGeneralIndicator));
            analyticsKiaMechanicalGeneralIndicatorValue.put("gpa", String.valueOf(gpa));

            analyticsKiaMechanicalGeneralIndicator.put(i, analyticsKiaMechanicalGeneralIndicatorValue);
        }

        return analyticsKiaMechanicalGeneralIndicator;
    }

    public List<VasCheckListMechanicalKiaModel> findAllCheckListCancel() {
        return vasCheckListMechanicalKiaRepository.findAllCheckListCancel();
    }

    public List<VasCheckListMechanicalKiaModel> findAllCheckListCancelFromNum(String numOrderCheckKia) {
        LOGGER.log(Level.INFO, "Запрос не полный чек-лист ИТ KIA: " + numOrderCheckKia);
        return vasCheckListMechanicalKiaRepository.findAllCheckListCancelFromNum(numOrderCheckKia);
    }


    public List<VasCheckListMechanicalKiaModel> countReportsKiaMechanicalPeriod(String periodCheckListMechanicalSurname, String periodCheckListMechanicalDateFrom, String periodCheckListMechanicalDateBy) throws ParseException{

        if ((periodCheckListMechanicalDateFrom).equals("")){
            periodCheckListMechanicalDateFrom = "2023-01-01";
        }

        if ((periodCheckListMechanicalDateBy).equals("")){
            LocalDate date = LocalDate.now();
            periodCheckListMechanicalDateBy = date.toString();
        }
        
        LOGGER.log(Level.INFO, "Проведен запрос чек-листов по автомеханику: " + periodCheckListMechanicalSurname + ", период с " + periodCheckListMechanicalDateFrom + " по " + periodCheckListMechanicalDateBy);
        return vasCheckListMechanicalKiaRepository.countReportsKiaMechanicalPeriod(periodCheckListMechanicalSurname, periodCheckListMechanicalDateFrom, periodCheckListMechanicalDateBy);
    }

    public FileSystemResource createReportsKiaMechanicalPeriod(String periodCheckListMechanicalSurname,
            String periodCheckListMechanicalDateFrom, String periodCheckListMechanicalDateBy) throws IOException {
                FileInputStream templatePeriodKiaMechanicalOrder = new FileInputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\reportCheckListMechanical.xlsx");
                //"C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\reports\\reportCheckListMechanical.xlsx"
                //"C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\reportCheckListMechanical.xlsx"
                try (XSSFWorkbook report = new XSSFWorkbook(templatePeriodKiaMechanicalOrder)) {

                    XSSFSheet checkListMechanical = report.getSheetAt(0);

                    //Запрос полной модели по параметрам
                    List<VasCheckListMechanicalKiaModel> countReportFromPeriod = vasCheckListMechanicalKiaRepository.countReportsKiaMechanicalPeriod(periodCheckListMechanicalSurname, periodCheckListMechanicalDateFrom, periodCheckListMechanicalDateBy);
                    // Имя ИТ
                    checkListMechanical.getRow(1).getCell(3).setCellValue(periodCheckListMechanicalSurname);
                    //Кол-во проверок
                    int countReport = countReportFromPeriod.size();
                    checkListMechanical.getRow(2).getCell(3).setCellValue(countReport);
                    // Дата с
                    checkListMechanical.getRow(3).getCell(3).setCellValue(periodCheckListMechanicalDateFrom);
                    // Дата по
                    checkListMechanical.getRow(3).getCell(5).setCellValue(periodCheckListMechanicalDateBy);
                    // Увеличение количества столбцов (ответы)
                    for(int i = 1; i < countReportFromPeriod.size(); i++){
                       for(int j = 0; j < 38; j++){
                           XSSFCell oldCell = checkListMechanical.getRow(j).getCell(11);
                           XSSFCell newCell = checkListMechanical.getRow(j).createCell(11+i);
                           newCell.copyCellFrom(oldCell, new CellCopyPolicy());
                       } 
                    }

                     // Заполняем отчёт исходя из количества найденных чек-листов
                     for(int i = 0; i < countReportFromPeriod.size(); i++){
                        
                        checkListMechanical.getRow(0).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getMechanicalKiaVin());
                        checkListMechanical.getRow(1).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getDateSaveRepairInspection());
                        checkListMechanical.getRow(2).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getNumOrderCheckKiaRepair());
                        checkListMechanical.getRow(3).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getResult());
                        
                        //Результаты по контролю ремонта
                        checkListMechanical.getRow(5).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getRepairQualityControl1());
                        checkListMechanical.getRow(6).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getRepairQualityControl2());
                        checkListMechanical.getRow(7).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getRepairQualityControl3());
                        checkListMechanical.getRow(8).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getRepairQualityControl4());
                        checkListMechanical.getRow(9).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getRepairQualityControl5());
                        checkListMechanical.getRow(10).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getRepairQualityControl6());
                        checkListMechanical.getRow(11).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getRepairQualityControl7());
                        checkListMechanical.getRow(12).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getRepairQualityControl8());
                        checkListMechanical.getRow(13).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getRepairQualityControl9());
                        checkListMechanical.getRow(14).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getRepairQualityControl10());
                        
                        //Результаты по проверке на стоянке
                        checkListMechanical.getRow(16).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck1());
                        checkListMechanical.getRow(17).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck2());
                        checkListMechanical.getRow(18).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck3());
                        checkListMechanical.getRow(19).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck4());
                        checkListMechanical.getRow(20).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck5());
                        checkListMechanical.getRow(21).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck6());
                        checkListMechanical.getRow(22).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck7());
                        checkListMechanical.getRow(23).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck8());
                        checkListMechanical.getRow(24).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck9());
                        checkListMechanical.getRow(25).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck10());
                        checkListMechanical.getRow(26).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getParkingLotCheck11());
                        
                        //Результаты по проверке в движении
                        checkListMechanical.getRow(28).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getCheckInMotion1());
                        checkListMechanical.getRow(29).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getCheckInMotion2());
                        checkListMechanical.getRow(30).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getCheckInMotion3());
                        checkListMechanical.getRow(31).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getCheckInMotion4());
                        
                        //Результаты по проверке качества
                        checkListMechanical.getRow(33).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getQualityControl1());
                        checkListMechanical.getRow(34).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getNotesMaster());
                        checkListMechanical.getRow(35).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getNotesWork());
                        checkListMechanical.getRow(36).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getExplanationsWork());
                        checkListMechanical.getRow(37).getCell(11 + i).setCellValue(countReportFromPeriod.get(i).getQualityControl5());
                        
                        
                     }

                    String reportName = "Чек-лист автомеханика за период.xlsx";
                    FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\NPS vas Server\\VAS-NPS\\src\\main\\resources\\static\\outputReports\\kiaMechanicalReport\\" + reportName);
                    //"C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\static\\outputReports\\kiaMechanicalReport\\" 
                    //"C:\\Users\\Shabanov\\Desktop\\Shabanov\\NPS vas Server\\VAS-NPS\\src\\main\\resources\\static\\outputReports\\kiaEngineerReport\\reportCheckListEngineer.xlsx"
                    report.write(fileOut);
                    fileOut.close();

                    return null;
                }
        }
}
