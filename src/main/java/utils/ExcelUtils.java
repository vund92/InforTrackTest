package utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelUtils {

    public static List<LinkedHashMap<String,String>> getExcelDataAsListOfMapWithRandomNumberData(String excelFileName, String sheetName) throws IOException {
        List<LinkedHashMap<String,String>> dataFromExcel = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new File("src/test/resources/testdata/" + excelFileName + ".xlsx"));
        Sheet sheet = workbook.getSheet(sheetName);

        int totalRows = sheet.getPhysicalNumberOfRows();
        LinkedHashMap<String,String> mapData;
        List<String> allKeys = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        for(int i = 0; i< totalRows ; i++) {
            mapData = new LinkedHashMap<>();
            if( i == 0) {
                int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
                for (int j = 0; j < totalCols; j++) {
                    allKeys.add(sheet.getRow(0).getCell(j).getStringCellValue());
                }
            }
            else {
                int totalCols = sheet.getRow(i).getPhysicalNumberOfCells();
                for (int j = 0; j < totalCols; j++) {
                    String cellValue = dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
                    int size = 6;
                    if(cellValue.contains("RandomNumber")) {
                        // With size
                        if(cellValue.contains("_")) {
                            size = Integer.parseInt((cellValue.split("_"))[1]);
                        }
                        cellValue = RandomDataGenerator_Reqres.getRandomNumber(size);
                    }
                    mapData.put(allKeys.get(j), cellValue);
                }
                dataFromExcel.add(mapData);
            }
        }
        dataFromExcel = dataFromExcel.stream().filter(keyValuePair -> keyValuePair.get("Enabled").equalsIgnoreCase("Y"))
                .collect(Collectors.toList());
        return dataFromExcel;
    }

    public static List<LinkedHashMap<String,String>> getExcelDataAsListOfMapWithRandomStringData(String excelFileName, String sheetName) throws IOException {
        List<LinkedHashMap<String,String>> dataFromExcel = new ArrayList<>();
        Workbook workbook = null;
        try{
            workbook = WorkbookFactory.create(new File("src/test/resources/testdata/" + excelFileName + ".xlsx"));
            Sheet sheet = workbook.getSheet(sheetName);

            int totalRows = sheet.getPhysicalNumberOfRows();
            LinkedHashMap<String,String> mapData;
            List<String> allKeys = new ArrayList<>();
            DataFormatter dataFormatter = new DataFormatter();
            for(int i = 0; i< totalRows ; i++) {
                mapData = new LinkedHashMap<>();
                if( i == 0) {
                    int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
                    for (int j = 0; j < totalCols; j++) {
                        allKeys.add(sheet.getRow(0).getCell(j).getStringCellValue());
                    }
                } else {
                    int totalCols = sheet.getRow(i).getPhysicalNumberOfCells();
                    for (int j = 0; j < totalCols; j++) {
                        String cellValue = null;
                        try{
                            cellValue = dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
                            int size = 12;
                            if(cellValue.contains("RandomString")) {
                                // With size
                                if(cellValue.contains("_")) {
                                    size = Integer.parseInt((cellValue.split("_"))[1]);
                                }
                                cellValue = RandomDataGenerator_Reqres.getRandomAlphabets(size);
                            }
                        } catch (Exception e) {
                            // Handle the exception, e.g., log it or provide a default value
                            cellValue = "Error: " + e.getMessage();
                        }
                        mapData.put(allKeys.get(j), cellValue);
                    }
                    dataFromExcel.add(mapData);
                }
            }
        } catch (IOException e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
        } finally {
            // Close the workbook to release resources
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataFromExcel;
    }

    public static List<LinkedHashMap<String,String>> getExcelDataAsListOfMapWithoutRandomData(String excelFileName, String sheetName) throws IOException {
        List<LinkedHashMap<String,String>> dataFromExcel = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new File("src/test/resources/testdata/" + excelFileName + ".xlsx"));
        Sheet sheet = workbook.getSheet(sheetName);

        int totalRows = sheet.getPhysicalNumberOfRows();
        LinkedHashMap<String,String> mapData;
        List<String> allKeys = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        for(int i = 0; i< totalRows ; i++) {
            mapData = new LinkedHashMap<>();
            if( i == 0) {
                int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
                for (int j = 0; j < totalCols; j++) {
                    allKeys.add(sheet.getRow(0).getCell(j).getStringCellValue());
                }
            }
            else {
                int totalCols = sheet.getRow(i).getPhysicalNumberOfCells();
                for (int j = 0; j < totalCols; j++) {
                    String cellValue = dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
                    mapData.put(allKeys.get(j), cellValue);
                }
                dataFromExcel.add(mapData);
            }
        }
        return dataFromExcel;
    }
}
