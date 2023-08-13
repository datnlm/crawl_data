package com.example.crawl_data.utils;

import com.example.crawl_data.models.ObjectTemplate;
import com.example.crawl_data.models.ObjectVina;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class Utils {
    private  CellStyle cellStyleFormatNumber = null;


    public  String convertToEmail(String hex) {

        try {
            byte[] decoded = Hex.decodeHex(hex.toCharArray());
            byte firstByte = decoded[0];

            byte[] newBytes = new byte[decoded.length - 1];

            for (int i = 0; i < decoded.length; i++) {
                byte result = (byte) (decoded[i] ^ firstByte);

                if (i == 0) {
                    continue;
                }

                newBytes[i - 1] = result;
            }

            return new String(newBytes);

        } catch (DecoderException e) {
            e.printStackTrace();
        }

        return null;
    }


    public  void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }


    public  void writeBook(ObjectVina object, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }


        Cell cell = row.createCell(0);
        cell.setCellValue(object.getCompanyName());

        cell = row.createCell(1);
        cell.setCellValue(object.getCompanyCode());

        cell = row.createCell(2);
        cell.setCellValue(object.getDate());

        cell = row.createCell(3);
        cell.setCellValue(object.getAddress());

        cell = row.createCell(4);
        cell.setCellValue(object.getPhone());

        cell = row.createCell(5);
        cell.setCellValue(object.getEmail());

        cell = row.createCell(6);
        cell.setCellValue(object.getPersonal());

        cell = row.createCell(7);
        cell.setCellValue(object.getPersonal_phone());

        cell = row.createCell(8);
        cell.setCellValue(object.getDirector());

        cell = row.createCell(9);
        cell.setCellValue(object.getDirector_phone());

        cell = row.createCell(10);
        cell.setCellValue(object.getAccountant());

        cell = row.createCell(11);
        cell.setCellValue(object.getAccountant_phone());

        cell = row.createCell(12);
        cell.setCellValue(object.getJob());
    }


    public  void writeTitle(Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue("Mã số thuế (*)");

        cell = row.createCell(1);
        cell.setCellValue("Tên khách hàng (*)");

        cell = row.createCell(2);
        cell.setCellValue("Tên viết tắt");

        cell = row.createCell(3);
        cell.setCellValue("Điện thoại");

        cell = row.createCell(4);
        cell.setCellValue("Email");

        cell = row.createCell(5);
        cell.setCellValue("Lĩnh vực");

        cell = row.createCell(6);
        cell.setCellValue("Địa chỉ (Hóa đơn)");

        cell = row.createCell(7);
        cell.setCellValue("Ngày cấp");
    }

    public  void writeBook(ObjectTemplate object, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }


        Cell cell = row.createCell(0);
        cell.setCellValue(object.getTax());

        cell = row.createCell(1);
        cell.setCellValue(object.getCompany());

        cell = row.createCell(2);
        cell.setCellValue(object.getCustomer());

        cell = row.createCell(3);
        cell.setCellValue(object.getPhone());

        cell = row.createCell(4);
        cell.setCellValue(object.getEmail());

        cell = row.createCell(5);
        cell.setCellValue(object.getIndustry());

        cell = row.createCell(6);
        cell.setCellValue(object.getAddress());

        cell = row.createCell(7);
        cell.setCellValue(object.getCreatedDate());

    }

}
