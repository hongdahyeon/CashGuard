package hong.CashGuard.domain.emaillog.service;

import hong.CashGuard.domain.emaillog.domain.CgEmailLog;
import hong.CashGuard.domain.emaillog.domain.CgEmailLogMapper;
import hong.CashGuard.domain.emaillog.dto.request.EmailLogSave;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : hong.CashGuard.domain.email.service
 * fileName       : CgEmailLogService
 * author         : work
 * date           : 2025-04-02
 * description    : 이메일 발송 로그 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgEmailLogService {

    private final CgEmailLogMapper mapper;
    private final GoogleEmailService googleEmailService;

    /**
     * @method      EmailLogSaveAndSend
     * @author      work
     * @date        2025-04-02
     * @deacription 이메일 발송 정보 저장 및 전송
    **/
    @Transactional
    public void EmailLogSaveAndSend(EmailLogSave request) {
        // 1. 이메일 전송 정보 저장
        mapper.insert(new CgEmailLog(UserUtil.getLoginUser(), request));

        // 2. 이메일 전송
        // TODO : 이메일 발송 기능 구현
        /*googleEmailService.sendMail(request.getRecipientEmail(),
                                    UserUtil.getLoginUser().getUserEmail(),
                                    UserUtil.getLoginUser().getUserNm(),
                                    request.getSubject(),
                                    request.getContent());*/
    }

}
