package hong.CashGuard.domain.receipt.service;

import hong.CashGuard.global.hong.google.GoogleVisionService;
import hong.CashGuard.global.exception.CGException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * packageName    : hong.CashGuard.domain.receipt.service
 * fileName       : CgReciptRestController
 * author         : work
 * date           : 2025-04-07
 * description    : Google Vision API OCR
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 * 2025-04-08        work       Google Vision OCR API 서비스 로직 분리
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/cguard/api/receipt")
public class CgReceiptRestController {

    private final GoogleVisionService visionService;

    @PostMapping("/upload")
    public ResponseEntity uploadReceiptImage(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            throw new CGException("이미지가 비어있습니다.", HttpStatus.BAD_REQUEST);
        }
        String extractText = visionService.extractText(file);
        return ResponseEntity.ok(extractText);
    }
}
