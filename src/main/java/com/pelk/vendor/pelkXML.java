package com.pelk.vendor;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class pelkXML {

    public String file;
    public FileInputStream fi;
    public HSSFWorkbook xss;
    public HSSFSheet shhets;

    public pelkXML(String file){
        this.file = file;
    }


    public pelkXML openFile() throws IOException {
        File file = new File(this.file);
        this.fi = new FileInputStream(file);
        return this;
    }

    public Iterator<Row> getDocumentIterator(){

        try {
            this.xss = new HSSFWorkbook(this.fi);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.shhets = xss.getSheetAt(0);
            Iterator<Row> iterator = shhets.iterator();
            return iterator;
        }

    }

    public String getCellValue(Cell cell){
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                return  cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
        }
        return "undefined type cell";
    }

}
