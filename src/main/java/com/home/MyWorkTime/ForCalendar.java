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
package com.home.MyWorkTime;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ForCalendar {
    public static void main(String[] args) throws IOException{

        FileInputStream listTO = new FileInputStream("C:\\Users\\Garant\\Desktop\\1.xlsx");
         try (XSSFWorkbook dataTO = new XSSFWorkbook(listTO)) {
            XSSFSheet list = dataTO.getSheetAt(0);
             XSSFSheet dataList = dataTO.getSheetAt(1);

             for (int i = 0; i < list.getLastRowNum(); i++){
                XSSFRow rowCall = list.getRow(i + 1);
                XSSFRow dataCall = dataList.getRow(i);

                XSSFCell name = rowCall.getCell(0);
                XSSFCell model = rowCall.getCell(1);
                XSSFCell vin = rowCall.getCell(2);
                XSSFCell relaese = rowCall.getCell(3);
                String newRelaese = String.valueOf(relaese).substring(0, 4);
                
                XSSFCell sale = rowCall.getCell(4);
                String dateSale = String.valueOf(sale);
                String saleDate = null;
                if(!(dateSale.equals(""))){
                String[] f = dateSale.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                saleDate = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell phone = rowCall.getCell(5);
                String addDate = "2023-07-19";
                String activity = "active";


                XSSFCell planned = rowCall.getCell(6);
                String plannedP = String.valueOf(planned);
                String plannedDate = null;
                if(!(plannedP.equals(""))){
                String[] f = plannedP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                plannedDate = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                // ТО-0 -НАЧАЛО
                XSSFCell to0 = rowCall.getCell(7);
                String to0DateP = String.valueOf(to0);
                String to0Date = null;
                if(!(to0DateP.equals(""))){
                String[] f = to0DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to0Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to0mileage = rowCall.getCell(8);
                String to0mileageP = String.valueOf(to0mileage);
                String to0km = null;
                if (!(to0mileageP).equals("")){
                String[] to0mileagePf = String.valueOf(to0mileageP).replace(".", " ").split(" ");
                to0km = "'" + to0mileagePf[0] + "'";
                }
                // ТО-0 - КОНЕЦ

                // ТО-1 -НАЧАЛО
                XSSFCell to1 = rowCall.getCell(9);
                String to1DateP = String.valueOf(to1);
                String to1Date = null;
                if(!(to1DateP.equals(""))){
                String[] f = to1DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to1Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to1mileage = rowCall.getCell(10);
                String to1mileageP = String.valueOf(to1mileage);
                String to1km = null;
                if (!(to1mileageP).equals("")){
                String[] to1mileagePf = String.valueOf(to1mileageP).replace(".", " ").split(" ");
                to1km = "'" + to1mileagePf[0] + "'";
                }
                // ТО-1 - КОНЕЦ

                // ТО-2 -НАЧАЛО
                XSSFCell to2 = rowCall.getCell(11);
                String to2DateP = String.valueOf(to2);
                String to2Date = null;
                if(!(to2DateP.equals(""))){
                String[] f = to2DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to2Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to2mileage = rowCall.getCell(12);
                String to2mileageP = String.valueOf(to2mileage);
                String to2km = null;
                if (!(to2mileageP).equals("")){
                String[] to2mileagePf = String.valueOf(to2mileageP).replace(".", " ").split(" ");
                to2km = "'" + to2mileagePf[0] + "'";
                }
                // ТО-2 - КОНЕЦ

                // ТО-3 -НАЧАЛО
                XSSFCell to3 = rowCall.getCell(13);
                String to3DateP = String.valueOf(to3);
                String to3Date = null;
                if(!(to3DateP.equals(""))){
                String[] f = to3DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to3Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to3mileage = rowCall.getCell(14);
                String to3mileageP = String.valueOf(to3mileage);
                String to3km = null;
                if (!(to3mileageP).equals("")){
                String[] to3mileagePf = String.valueOf(to3mileageP).replace(".", " ").split(" ");
                to3km = "'" + to3mileagePf[0] + "'";
                }
                // ТО-3 - КОНЕЦ
// ТО-4 -НАЧАЛО
                XSSFCell to4 = rowCall.getCell(15);
                String to4DateP = String.valueOf(to4);
                String to4Date = null;
                if(!(to4DateP.equals(""))){
                String[] f = to4DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to4Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to4mileage = rowCall.getCell(16);
                String to4mileageP = String.valueOf(to4mileage);
                String to4km = null;
                if (!(to4mileageP).equals("")){
                String[] to4mileagePf = String.valueOf(to4mileageP).replace(".", " ").split(" ");
                to4km = "'" + to4mileagePf[0] + "'";
                }
                // ТО-4 - КОНЕЦ

                // ТО-5 -НАЧАЛО
                XSSFCell to5 = rowCall.getCell(17);
                String to5DateP = String.valueOf(to5);
                String to5Date = null;
                if(!(to5DateP.equals(""))){
                String[] f = to5DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to5Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to5mileage = rowCall.getCell(18);
                String to5mileageP = String.valueOf(to5mileage);
                String to5km = null;
                if (!(to5mileageP).equals("")){
                String[] to5mileagePf = String.valueOf(to5mileageP).replace(".", " ").split(" ");
                to5km = "'" + to5mileagePf[0] + "'";
                }
                // ТО-5 - КОНЕЦ

                // ТО-6 -НАЧАЛО
                XSSFCell to6 = rowCall.getCell(19);
                String to6DateP = String.valueOf(to6);
                String to6Date = null;
                if(!(to6DateP.equals(""))){
                String[] f = to6DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to6Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to6mileage = rowCall.getCell(20);
                String to6mileageP = String.valueOf(to6mileage);
                String to6km = null;
                if (!(to6mileageP).equals("")){
                String[] to6mileagePf = String.valueOf(to6mileageP).replace(".", " ").split(" ");
                to6km = "'" + to6mileagePf[0] + "'";
                }
                // ТО-6 - КОНЕЦ

                // ТО-7 -НАЧАЛО
                XSSFCell to7 = rowCall.getCell(21);
                String to7DateP = String.valueOf(to7);
                String to7Date = null;
                if(!(to7DateP.equals(""))){
                String[] f = to7DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to7Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to7mileage = rowCall.getCell(22);
                String to7mileageP = String.valueOf(to7mileage);
                String to7km = null;
                if (!(to7mileageP).equals("")){
                String[] to7mileagePf = String.valueOf(to7mileageP).replace(".", " ").split(" ");
                to7km = "'" + to7mileagePf[0] + "'";
                }
                // ТО-7 - КОНЕЦ

                // ТО-8 -НАЧАЛО
                XSSFCell to8 = rowCall.getCell(23);
                String to8DateP = String.valueOf(to8);
                String to8Date = null;
                if(!(to8DateP.equals(""))){
                String[] f = to8DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to8Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to8mileage = rowCall.getCell(24);
                String to8mileageP = String.valueOf(to8mileage);
                String to8km = null;
                if (!(to8mileageP).equals("")){
                String[] to8mileagePf = String.valueOf(to8mileageP).replace(".", " ").split(" ");
                to8km = "'" + to8mileagePf[0] + "'";
                }
                // ТО-8 - КОНЕЦ

                // ТО-9 -НАЧАЛО
                XSSFCell to9 = rowCall.getCell(25);
                String to9DateP = String.valueOf(to9);
                String to9Date = null;
                if(!(to9DateP.equals(""))){
                String[] f = to9DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to9Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to9mileage = rowCall.getCell(26);
                String to9mileageP = String.valueOf(to9mileage);
                String to9km = null;
                if (!(to9mileageP).equals("")){
                String[] to9mileagePf = String.valueOf(to9mileageP).replace(".", " ").split(" ");
                to9km = "'" + to9mileagePf[0] + "'";
                }
                // ТО-9 - КОНЕЦ

                // ТО-10 -НАЧАЛО
                XSSFCell to10 = rowCall.getCell(27);
                String to10DateP = String.valueOf(to10);
                String to10Date = null;
                if(!(to10DateP.equals(""))){
                String[] f = to10DateP.replaceAll("-", " ").replace(".", "").split(" ");
                String month = f[1];
                String forDate = null;
                if(month.equals("янв")){forDate = "01";}
                else if(month.equals("февр")){forDate = "02";}
                else if(month.equals("мар")){forDate = "03";}
                else if(month.equals("апр")){forDate = "04";}
                else if(month.equals("мая")){forDate = "05";}
                else if(month.equals("июн")){forDate = "06";}
                else if(month.equals("июл")){forDate = "07";}
                else if(month.equals("авг")){forDate = "08";}
                else if(month.equals("сент")){forDate = "09";}
                else if(month.equals("окт")){forDate = "10";}
                else if(month.equals("нояб")){forDate = "11";}
                else if(month.equals("дек")){forDate = "12";} 
                to10Date = "'" + f[2] + "-" + forDate + "-" + f[0] + "'";
                }

                XSSFCell to10mileage = rowCall.getCell(28);
                String to10mileageP = String.valueOf(to10mileage);
                String to10km = null;
                if (!(to10mileageP).equals("")){
                String[] to10mileagePf = String.valueOf(to10mileageP).replace(".", " ").split(" ");
                to10km = "'" + to10mileagePf[0] + "'";
                }
                // ТО-10 - КОНЕЦ

                /**
                 * INSERT INTO vas_calendar_client_kia (owner, model, vehicle_identification_number, year_release, date_sale, phone, add_date, to1_date, to1_mileage, to2_date, to2_mileage, to3_date, to3_mileage, to4_date, to4_mileage, to5_date, to5_mileage, to6_date, to6_mileage, to7_date, to7_mileage, to8_date, to8_mileage, to9_date, to9_mileage, to10_date, to10_mileage)
                    VALUES ('+name+', 'SPORTAGE', 'U5YPG81AALL895737', '2020', 31.03.2020, '375292132742', 05.12.2020, 05.12.2020, 14901, 20.11.2021, 29293, 06.09.2022, 43145);
                 */



                dataCall.createCell(0).setCellValue("INSERT INTO vas_calendar_client_kia (owner, model, vehicle_identification_number, year_release, date_sale, phone, planned_date, add_date, activity, to0_date, to0_mileage, to1_date, to1_mileage, to2_date, to2_mileage, to3_date, to3_mileage, to4_date, to4_mileage, to5_date, to5_mileage, to6_date, to6_mileage, to7_date, to7_mileage, to8_date, to8_mileage, to9_date, to9_mileage, to10_date, to10_mileage)"
                + " VALUES (" + "'"+name+"', " + "'"+model+"', " + "'"+vin+"', " + 
                "'"+newRelaese+"', " +
                saleDate+", " +
                "'"+phone+"', " +
                plannedDate+", " +
                "'"+addDate+"', " +
                "'"+activity+"', " +
                to0Date+", " +
                to0km+", " +
                to1Date+", " +
                to1km+", " +
                to2Date+", " +
                to2km+", " +
                to3Date+", " +
                to3km+", " +
                to4Date+", " +
                to4km+", " +
                to5Date+", " +
                to5km+", " +
                to6Date+", " +
                to6km+", " +
                to7Date+", " +
                to7km+", " +
                to8Date+", " +
                to8km+", " +
                to9Date+", " +
                to9km+", " +
                to10Date+", " +
                to10km+");"
                );
             }

            String newFile = "2.xlsx";
            FileOutputStream fileOuts = new FileOutputStream("C:\\Users\\Garant\\Desktop\\" + newFile);
            dataTO.write(fileOuts);
            fileOuts.close();
        }
    } 
}
