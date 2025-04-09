package hong.CashGuard.domain.receipt.service;

import hong.CashGuard.domain.file.service.CgFileService;
import hong.CashGuard.domain.receipt.domain.CgReceipt;
import hong.CashGuard.domain.receipt.domain.CgReceiptMapper;
import hong.CashGuard.global.hong.google.GoogleVisionService;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * packageName    : hong.CashGuard.domain.receipt.service
 * fileName       : CgReceiptService
 * author         : work
 * date           : 2025-04-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 * 2025-04-08        work       Google Vision OCR API 서비스 로직 분리
 * 2025-04-09        work       영수증 이미지 OCR + 이미지 저장 -> 영수증 정보 저장
 */

@Service
@RequiredArgsConstructor
public class CgReceiptService {

    private final GoogleVisionService visionService;
    private final CgFileService fileService;
    private final CgReceiptMapper mapper;

    /**
     * @method      uploadReceiptImage
     * @author      work
     * @date        2025-04-09
     * @deacription 영수증 이미지 OCR 판독 + 결과 정보 및 파일 저장
    **/
    @Transactional
    public void uploadReceiptImage(MultipartFile file) {
        String extractText = visionService.extractText(file);       // 영수증 이미지 OCR
        Long imageUid = fileService.saveReceiptImage(file);         // 이미지 저장
        Long loginUserUid = UserUtil.getLoginUserUid();
        mapper.insert(new CgReceipt(extractText, loginUserUid, imageUid));
    }

}
