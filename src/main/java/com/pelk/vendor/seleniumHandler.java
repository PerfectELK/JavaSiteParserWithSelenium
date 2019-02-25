package com.pelk.vendor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class seleniumHandler {

    private String url;
    private WebDriver driver;
    //public ChromeOptions chromeOptions = new ChromeOptions();


    public WebDriver getDriver() {
        return driver;
    }

    public seleniumHandler(){
        //String res = this.getClass().getResource("\\chrome\\chromedriver.exe").getFile();
        String res = System.getProperty("user.dir") + "\\chrome\\chromedriver.exe";
        System.out.println(res);
        System.out.println(new File(res));
        System.setProperty("webdriver.chrome.driver", res);
        //this.chromeOptions.addArguments("--proxy-server=http://");
        this.driver = new ChromeDriver();
    }

    public void submit(String word){
        WebElement field = this.driver.findElement(By.id("pcode"));
        field.sendKeys(word);
        field.submit();
        new WebDriverWait(driver, 10);
    }

    public WebDriver request(){
        driver.get(this.url);
        return this.driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<WebElement> getElementsByClassName(String className){
        return this.driver.findElements(By.className(className));
    }

    public WebElement getElementById(String Id){
        return this.driver.findElement(By.id(Id));
    }

    public ArrayList<String> getValuesFromElements(List<WebElement> elements){

        ArrayList<String> list = new ArrayList();

        for(int i = 0; i < elements.size(); i++){
            list.add(elements.get(i).getText());
        }

        return list;
    }

    public void destruct(){
        driver.close();
        this.url = null;
        this.driver = null;
        System.gc();
    }

    public Integer getMinValueFromClassCollections(List<WebElement> elems){
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < elems.size(); i++){
            list.add(Integer.parseInt(elems.get(i).getText().replaceAll("[\\D]","")));
        }
        return Collections.min(list);
    }

}