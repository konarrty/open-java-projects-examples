package com.example.constructionmanagementspring.service.impl.qr;

import com.example.constructionmanagementspring.service.qr.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class QRCodeServiceImpl implements QRCodeService {

    public String createQRCode(String qrText) {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        String qrName = null;
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            bitMatrix = barcodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 200, 200, hints);
        } catch (WriterException e) {
            log.error("ошибка записи");
        }
        File qrImage = new File("src/main/resources/qr/" + UUID.randomUUID() + ".jpg");
        try {
            ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "jpg", qrImage);
            qrName = qrImage.getName();
        } catch (IOException e) {
            log.error("Ошибка отрисовки изображения qr-кода");
        }
        return qrName;
    }
}
