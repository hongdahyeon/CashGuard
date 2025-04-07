package hong.CashGuard.domain.receipt.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import hong.CashGuard.global.exception.CGException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.receipt.service
 * fileName       : CgReceiptService
 * author         : work
 * date           : 2025-04-07
 * description    : GOOGLE VISION API OCR 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgReceiptService {

    @Value("${ncp.clova.ocr.secret-key}")
    private String secretKey;

    @Value("${ncp.clova.ocr.invoke-url}")
    private String invokeUrl;

    private final ImageAnnotatorClient imageAnnotatorClient;

    public String extractText(MultipartFile file) throws IOException {
        // 1. MultipartFile -> ByteString
        ByteString imgBytes = ByteString.readFrom(file.getInputStream());

        // 2. 이미이 객체 구성
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(img).build();

        // 3. [ImageAnnotatorClient]를 통해 Google Vision API 호출
        BatchAnnotateImagesResponse response = imageAnnotatorClient.batchAnnotateImages(List.of(request));
        List<AnnotateImageResponse> responses = response.getResponsesList();

        StringBuilder extractedText = new StringBuilder();
        for (AnnotateImageResponse res : responses) {
            if (res.hasError()) {
                throw new CGException("OCR 실패: " + res.getError().getMessage(), HttpStatus.BAD_REQUEST);
            }
            extractedText.append(res.getTextAnnotations(0).getDescription());
        }

        return extractedText.toString();
    }
}
