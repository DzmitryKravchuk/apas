package com.belpost.apas.service.xlsx;

import com.belpost.apas.service.util.CustomObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

@Slf4j
//@Component
@RequiredArgsConstructor
public class XlsxFileReader implements AutoCloseable {
    private final InputStream inputStream;
    private final CustomObjectMapper objectMapper;

    public <T> List<T> read(File fileToRead, Class<T> clazz) throws IOException {
        log.info("Start reading workbook from file [{}]", fileToRead);
        List<T> list = new ArrayList<>();
        XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
        XSSFSheet workSheet = workBook.getSheetAt(0);
        Iterator<Row> rowIter = workSheet.rowIterator();
        final Map<Integer, String> header = new HashMap<>();

        while (rowIter.hasNext()) {
            XSSFRow nextRow = (XSSFRow) rowIter.next();
            Iterator<Cell> cellIter = nextRow.cellIterator();

            //process header
            if (nextRow.getRowNum() == 0) {
                while (cellIter.hasNext()) {
                    XSSFCell nextCell = (XSSFCell) cellIter.next();
                    header.put(nextCell.getColumnIndex(), nextCell.getStringCellValue());
                }
            }

            Map<String, String> rowObjectMap = new HashMap<>();
            while (cellIter.hasNext()) {
                XSSFCell nextCell = (XSSFCell) cellIter.next();
                rowObjectMap.put(header.get(nextCell.getColumnIndex()),
                    nextCell.getStringCellValue());
            }
            String json = new JSONObject(rowObjectMap).toString();
            final T pojo = objectMapper.readFromFile(json, clazz);
            list.add(pojo);
        }

        log.info("Finish writing workbook to file [{}]", fileToRead);

        return list;
    }

    @Override
    public void close() throws Exception {
        inputStream.close();
    }
}
