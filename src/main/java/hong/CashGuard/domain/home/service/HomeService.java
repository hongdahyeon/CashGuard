package hong.CashGuard.domain.home.service;

import hong.CashGuard.domain.code.EmailSendReason;
import hong.CashGuard.domain.emaillog.domain.CgEmailLog;
import hong.CashGuard.domain.emaillog.service.CgEmailLogService;
import hong.CashGuard.domain.group.service.CgGroupService;
import hong.CashGuard.domain.user.dto.response.CgUserView;
import hong.CashGuard.domain.user.service.CgUserService;
import hong.CashGuard.global.auth.AuthenticationService;
import hong.CashGuard.global.exception.CGException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * packageName    : hong.CashGuard.domain.home.service
 * fileName       : HomeService
 * author         : work
 * date           : 2025-04-03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성 (이메일을 통한 초대하기 서비스 로직 추가)
 * 2025-04-04        work       로그인 시점에 자신이 속한 활성화된 그룹을 세션에 담기에 순서 변경
 *                              => 기존 ) 자동 로그인 이후에 그룹 멤버 담기
 *                              => 이후 ) 그룹 멤버에 담고 자동 로그인 처리
 */

@Service
@RequiredArgsConstructor
public class HomeService {

    private final CgEmailLogService emailLogService;
    private final CgGroupService groupService;
    private final CgUserService userService;
    private final AuthenticationService authenticationService;


    /**
     * @method      inviteUser
     * @author      work
     * @date        2025-04-03
     * @deacription 이메일을 통한 초대하기 서비스 로직
    **/
    @Transactional
    public ResponseEntity inviteUser(String token, Long uid, String reasonCode, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("token = " + token);
        if( !EmailSendReason.isValidReason(reasonCode) ) {
            throw new CGException("유효하지 않은 이메일 발송 이유 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        System.out.println(EmailSendReason.INVITE_GROUP.equals(reasonCode));
        if( EmailSendReason.INVITE_GROUP.name().equals(reasonCode) ) {

            boolean isValidToken = emailLogService.isValidToken(token);
            System.out.println("isValidToken = " + isValidToken);
            if( !isValidToken ) {
                throw new CGException("유효하지 않은 이메일 토큰입니다.", HttpStatus.BAD_REQUEST);
            } else {

                CgEmailLog emailLogBean = emailLogService.findEmailLogByUid(token);
                String recipientEmail = emailLogBean.getRecipientEmail();
                CgUserView user = userService.findUserByEmail(recipientEmail);

                // (1) 그룹으로 초대
                groupService.saveInviteMember(user.getUid(), uid);

                // (2) 초대장 이메일 토큰 유호성 없애기
                emailLogService.changeTokenIsRead(token);

                // (3) 자동 로그인 처리
                authenticationService.login(user.getUserId(), req);

                // (4) 초대 완료 > 메인 페이지로 리디렉션
                try {
                    res.sendRedirect("/?invitesuccess");
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("초대 후 리디렉트 오류 발생");
                }
            }

        }
        return ResponseEntity.ok().build();
    }
}
