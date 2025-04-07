package hong.CashGuard.domain.receipt.service;

import lombok.RequiredArgsConstructor;
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
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/cguard/api/receipt")
public class CgReceiptRestController {

    private final CgReceiptService service;

    @PostMapping("/upload")
    public ResponseEntity uploadReceiptImage(@RequestParam("file") MultipartFile file) throws IOException {
        String extractText = service.extractText(file);
        return ResponseEntity.ok(extractText);
    }
}
