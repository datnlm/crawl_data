package com.example.crawl_data.controller;

import com.example.crawl_data.models.ObjectTemplate;
import com.example.crawl_data.models.ObjectVina;
import com.example.crawl_data.utils.Utils;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "crawl")
public class CrawlDataController {
    private final Utils utils;

    public CrawlDataController(Utils utils) {
        this.utils = utils;
    }

    @PostMapping("/vinaBiz")
    public String crawlDataVinaBiz(@RequestParam(name = "url") String urlPath, @RequestParam(name = "startIndex") int startIndex, @RequestParam(name = "endIndex") int endIndex) throws IOException {
        String[] urlPaths = urlPath.split("/");
        List<ObjectVina> objects = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            System.out.println("***********Page: " + i);
            String url = urlPath + "/" + i;
            Document doc = Jsoup.connect(url).data("query", "Java").userAgent("Chrome").cookie("auth", "token").timeout(200000).post();
            Elements elements = doc.getElementsByTag("h4");
            for (Element e : elements) {
                Elements list_a = e.getElementsByTag("a");
                for (Element a : list_a) {
                    String href_a = a.attr("href");
                    String url_detail = "https://vinabiz.us" + href_a;
                    Document doc2 = Jsoup.connect(url_detail).data("query", "Java").userAgent("Chrome").cookie("auth", "token").timeout(200000).post();
                    Elements elementsDetail = doc2.getElementsByTag("td");
                    if (!elementsDetail.get(14).text().contains("đang hoạt động")) {
                        break;
                    }
                    String companyName = elementsDetail.get(2).text();
                    String companyCode = elementsDetail.get(6).text();
                    String date = elementsDetail.get(8).text();
                    String address = elementsDetail.get(18).text();
                    String phone = elementsDetail.get(20).text();
                    String personal = elementsDetail.get(28).text();
                    String personal_phone = elementsDetail.get(30).text();
                    String director = elementsDetail.get(34).text();
                    String director_phone = elementsDetail.get(36).text();
                    String accountant = elementsDetail.get(40).text();
                    String accountant_phone = elementsDetail.get(42).text();
                    String job = elementsDetail.get(48).text();
                    String[] b = elementsDetail.get(24).toString().split("\"");
                    List<String> test = Arrays.asList(b);
                    String email = "";
                    if (test.size() != 1 && test.get(5) != null) {
                        email = utils.convertToEmail(test.get(5));
                    }
                    objects.add(new ObjectVina(companyName, companyCode, date, address, phone, email, personal, personal_phone, director, director_phone, accountant, accountant_phone, job));
                    System.out.println(new ObjectVina(companyName, companyCode, date, address, phone, email, personal, personal_phone, director, director_phone, accountant, accountant_phone, job));
                }
            }
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        String strDate = simpleDateFormat.format(date);
        String excelFilePath = urlPaths[urlPaths.length - 1] + "_" + strDate + ".xlsx";
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        int rowIndex = 0;
        for (ObjectVina book : objects) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            utils.writeBook(book, row);
            rowIndex++;
        }
        utils.createOutputFile(workbook, excelFilePath);
        System.out.println(excelFilePath + "Done!!!");
        return "OK";
    }

    @PostMapping("/hoSoCongTy")
    public String crawlHoSoCongTy(@RequestParam(name = "url") String urlPath, @RequestParam(name = "startIndex") int startIndex, @RequestParam(name = "endIndex") int endIndex) throws IOException {
        String[] urlPaths = urlPath.split("/");
        new Thread(() -> {
            List<ObjectTemplate> objects = new ArrayList<>();
            for (int i = startIndex; i <= endIndex; i++) {
                System.out.println("=======> " + urlPaths[urlPaths.length - 1] + "_" + "Page: " + i);
                String url = urlPath + "/page-" + i;
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).data("query", "Java").userAgent("PostmanRuntime/7.32.2")
                            .timeout(200000).post();
                } catch (IOException e) {
                    System.out.println("Error =======> " + urlPaths[urlPaths.length - 1] + "_" + "Page: " + i);
                }

                Elements elements = doc.getElementsByTag("h3");
                for (Element e : elements) {
                    Elements list_a = e.getElementsByTag("a");
                    for (Element a : list_a) {
                        String href_a = a.attr("href");
                        String url_detail = "https://hosocongty.vn/" + href_a;
                        Document doc2 = null;
                        try {
                            doc2 = Jsoup.connect(url_detail).data("query", "Java").userAgent("PostmanRuntime/6.32.2")
                                    .timeout(200000).post();
                        } catch (IOException ex) {
                            System.out.println("Error =======> " + urlPaths[urlPaths.length - 1] + "_" + "Page: " + i);
                        }
                        Elements elementsDetail = doc2.getElementsByTag("li");
                        String regex = "Mã số thuế:(?:\\s?)(.*)[\\s*\\S*]*Địa chỉ thuế:(.*)\\sĐại diện pháp luật:(\\D*)\\sĐiện thoại:(.*)[\\s*\\S*]*Ngày cấp:\\s(.*)\\sNgành nghề chính:\\s(.*)\\sTrạng thái:";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(elementsDetail.text());
                        if (matcher.find()) {
                            ObjectTemplate objectTemplate = new ObjectTemplate(matcher.group(1),
                                    elementsDetail.get(4).text(),
                                    matcher.group(3),
                                    matcher.group(4).replaceAll("\\D*", ""),
                                    "",
                                    matcher.group(6),
                                    matcher.group(2),
                                    matcher.group(5));
                            objects.add(objectTemplate);
                            System.out.println(objectTemplate.toString());

                        } else {
                            ObjectTemplate objectTemplate = new ObjectTemplate("",
                                    elementsDetail.get(4).text(),
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "");
                            objects.add(objectTemplate);
                        }
                    }
                }
            }
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd");
            String strDate = simpleDateFormat.format(date);
            String excelFilePath = urlPaths[urlPaths.length - 1] + "_" + strDate + ".xlsx";
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            int rowIndex = 0;
            Row row = sheet.createRow(rowIndex);
            utils.writeTitle(row);
            rowIndex++;
            for (ObjectTemplate book : objects) {
                // Create row
                row = sheet.createRow(rowIndex);
                // Write data on row
                utils.writeBook(book, row);
                rowIndex++;
            }
            try {
                utils.createOutputFile(workbook, excelFilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(excelFilePath + "Done!!!");
        }).start();
        return "OK";
    }

    @PostMapping("/maSoThue")
    public String crawlMaSoThue(@RequestParam(name = "url") String urlPath, @RequestParam(name = "startIndex") int startIndex, @RequestParam(name = "endIndex") int endIndex) throws IOException {
        String[] urlPaths = urlPath.split("/");
        new Thread(() -> {
            List<ObjectTemplate> objects = new ArrayList<>();
            for (int i = startIndex; i <= endIndex; i++) {
                System.out.println("=======> " + urlPaths[urlPaths.length - 1] + "_" + "Page: " + i);
                String url = urlPath + "?page=" + i;
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).userAgent("Safari").timeout(200000).get();
                } catch (IOException e) {
                    System.out.println("Error =======> " + urlPaths[urlPaths.length - 1] + "_" + "Page: " + i);
                }
                Elements elements = doc.getElementsByTag("h3");

                for (int j = 0; j < elements.size() - 2; j++) {
                    String href_a = elements.get(j).getElementsByTag("a").attr("href");
                    String url_detail = "https://masothue.com" + href_a;
                    Document doc2 = null;
                    try {
                        doc2 = Jsoup.connect(url_detail).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11").timeout(200000).get();
                    } catch (IOException ex) {
                        System.out.println("Error =======> " + urlPaths[urlPaths.length - 1] + "_" + "Page: " + i);
                    }

                    String tenDoanhNghiep = doc2.getElementsByAttributeValueStarting("itemprop", "name").text();
                    String diaChi = doc2.getElementsByAttributeValueStarting("itemprop", "address").text();

                    String soDienThoai = doc2.getElementsByAttributeValueStarting("itemprop", "telephone").text();


                    String maSoThue = doc2.getElementsByAttributeValueStarting("itemprop", "taxID").text();

                    String nguoiDaiDien = "";

                    Elements elements2 = doc2.getElementsByAttributeValueStarting("itemprop", "alumni");

                    for (Element element :
                            elements2) {
                        nguoiDaiDien = element.getElementsByTag("a").get(0).text();
                        break;
                    }

                    Elements elements1= doc2.getElementsByTag("span");
                    String ngayHoatDong = elements1.get(elements1.size() - 2).text();

                    ObjectTemplate objectTemplate = new ObjectTemplate(maSoThue,
                            tenDoanhNghiep,
                            nguoiDaiDien,
                            soDienThoai,
                            "",
                            "",
                            diaChi,
                            ngayHoatDong);
                    objects.add(objectTemplate);
                    System.out.println(objectTemplate.toString());
                }
            }
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd");
            String strDate = simpleDateFormat.format(date);
            String excelFilePath = urlPaths[urlPaths.length - 1] + "_" + strDate + ".xlsx";
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            int rowIndex = 0;
            Row row = sheet.createRow(rowIndex);
            utils.writeTitle(row);
            rowIndex++;
            for (ObjectTemplate book : objects) {
                // Create row
                row = sheet.createRow(rowIndex);
                // Write data on row
                utils.writeBook(book, row);
                rowIndex++;
            }
            try {
                utils.createOutputFile(workbook, excelFilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(excelFilePath + "Done!!!");
        }).start();
        return "OK";
    }
}
