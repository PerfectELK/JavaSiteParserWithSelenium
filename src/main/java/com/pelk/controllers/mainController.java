package com.pelk.controllers;

import com.pelk.vendor.pelkFS;
import com.pelk.vendor.pelkXML;
import com.pelk.vendor.seleniumHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class mainController {

    @FXML
    Label pathToFile;

    private FileChooser fileChooser = new FileChooser();
    private String filePath;

    @FXML
    TextField sku;

    @FXML
    TextField price;

    @FXML
    TextField output;

    public void selectFile(Event event){

        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        pathToFile.setText(file.toString());
        filePath = file.toString();

    }

    public void startParse() throws IOException {

        Integer sku = Integer.parseInt(this.sku.getText().trim());
        Integer price = Integer.parseInt(this.price.getText().trim());
        String  output = this.output.getText().trim();

        pelkFS fs = new pelkFS(this.filePath,output);
        fs.createDest();
        fs.copyFileUsingStream();

        String dest = fs.getDest();
        pelkXML xml = new pelkXML(dest);
        System.out.println("document was get");
        System.out.println(dest);
        Iterator<Row> iterator = xml.openFile().getDocumentIterator();
        System.out.println("iterator was get");

        seleniumHandler selenium = new seleniumHandler();
        selenium.setUrl("https://exist.ru/");
        selenium.request();

        System.out.println("selenium started");

        //overloop:
        while(iterator.hasNext()){
            System.out.println("loop start");

            HSSFRow row = (HSSFRow) iterator.next();
            Iterator<Cell> cellIterator = row.iterator();

            while(cellIterator.hasNext()){

                Cell cell = cellIterator.next();
                Integer cellIndex = cell.getColumnIndex();
                if(cell.getRowIndex() != 0) {
                    if (cellIndex == sku) {
                        String skuValueInCell = xml.getCellValue(cell);
                        System.out.println(skuValueInCell);

                        selenium.submit(skuValueInCell);
                        List<WebElement> list = selenium.getElementsByClassName("price");
                        if(list.size() > 0){
                            Integer minPrice = selenium.getMinValueFromClassCollections(list);
                            try{
                                row.getCell(price).setCellValue(minPrice);
                            }catch (Exception e){
                                row.createCell(price).setCellValue(minPrice);
                            }

                        }
                        System.out.println("rewrite success");
                        //break overloop;
                    }

                }

            }

        }
       // selenium.destruct();

        FileOutputStream out = new FileOutputStream(new File(dest));
        xml.xss.write(out);
        out.close();
        xml.xss.close();
        xml.fi.close();





    }




}
