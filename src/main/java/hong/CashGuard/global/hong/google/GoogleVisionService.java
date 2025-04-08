package hong.CashGuard.global.hong.google;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import hong.CashGuard.global.exception.CGException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : hong.CashGuard.global.hong.google
 * fileName       : GoogleVisionService
 * author         : work
 * date           : 2025-04-08
 * description    : GOOGLE VISION API OCR 서비스 로직
 *
 *                  ## Google Vision OCR 결과 구조 (FullTextAnnotation) ##
 *                     TextAnnotation
 *                          Page (한장) : 1개의 이미지는 1개의 페이지로 인식된다.
 *                          |
 *                          L  Block(덩어리 : 제목, 문단, 표 같은 시각적 구획) : 페이지 내의 시각적으로 구분된 블럭 (문단 그룹이 많을수록 Block 수도 많아짐)
 *                              |
 *                              L Paragraph (글의 단락, 줄 같은 단위) : 문단 or 줄
 *                                    |
 *                                    L Word (단어 단위) : 띄어쓰기 단위 기준 (하나의 단어)
 *                                        |
 *                                        L Symbol (한 글자 단위) : 한글 음절
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */
@Service
@RequiredArgsConstructor
public class GoogleVisionService {

    private final ImageAnnotatorClient imageAnnotatorClient;

    public String extractText(MultipartFile file) throws IOException {
        // 1. MultipartFile -> ByteString
        ByteString imgBytes = ByteString.readFrom(file.getInputStream());
        List<AnnotateImageRequest> requests = this.makeRequestBuilder(imgBytes);

        // 3. [ImageAnnotatorClient]를 통해 Google Vision API 호출
        BatchAnnotateImagesResponse response = imageAnnotatorClient.batchAnnotateImages(requests);
        List<AnnotateImageResponse> responses = response.getResponsesList();

        StringBuilder extractedText = new StringBuilder();
        for (AnnotateImageResponse res : responses) {
            if (res.hasError()) {
                throw new CGException("OCR 실패: " + res.getError().getMessage(), HttpStatus.BAD_REQUEST);
            }
            String stringText = this.extractResponse(res);
            extractedText.append(stringText);
        }
        return extractedText.toString().trim();
    }

    private List<AnnotateImageRequest> makeRequestBuilder(ByteString imgBytes) {
        // 2. 이미지 객체 구성
        List<AnnotateImageRequest> requests = new ArrayList<>();
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(img).build();
        requests.add(request);
        return requests;
    }

    private String extractResponse(AnnotateImageResponse res) {
        StringBuilder stringBuilder = new StringBuilder();
        TextAnnotation annotation = res.getFullTextAnnotation();
        for (Page page : annotation.getPagesList()) {
            for (Block block : page.getBlocksList()) {
                for (Paragraph para : block.getParagraphsList()) {
                    for (Word word : para.getWordsList()) {
                        StringBuilder wordText = new StringBuilder();
                        for (Symbol symbol : word.getSymbolsList()) {
                            wordText.append(symbol.getText());
                        }
                        stringBuilder.append(wordText).append(" ");
                    }
                    stringBuilder.append("\n"); // 문단 줄바꿈
                }
                stringBuilder.append("\n"); // 블럭 줄바꿈 (optional)
            }
        }
        return stringBuilder.toString();
    }
}
