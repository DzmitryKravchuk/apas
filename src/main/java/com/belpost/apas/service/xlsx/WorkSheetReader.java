package com.belpost.apas.service.xlsx;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkSheetReader {

    public String readCellValue(XSSFSheet workSheet, int rowIndex, int columnIndex) {
        XSSFRow nextRow = workSheet.getRow(rowIndex);
        Cell nextCell = nextRow.getCell(columnIndex);
        return nextCell.getStringCellValue();
    }

    public String readCellValue(Cell nextCell) {
        return nextCell.getStringCellValue();
    }

}
