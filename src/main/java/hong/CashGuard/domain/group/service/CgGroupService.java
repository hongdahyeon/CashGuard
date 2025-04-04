package hong.CashGuard.domain.group.service;

import hong.CashGuard.domain.code.EmailSendReason;
import hong.CashGuard.domain.emaillog.dto.request.EmailLogSave;
import hong.CashGuard.domain.emaillog.service.CgEmailLogService;
import hong.CashGuard.domain.group.domain.CgGroup;
import hong.CashGuard.domain.group.domain.CgGroupMapper;
import hong.CashGuard.domain.group.domain.category.CgGroupCategory;
import hong.CashGuard.domain.group.domain.category.CgGroupCategoryMapper;
import hong.CashGuard.domain.group.domain.member.CgGroupMember;
import hong.CashGuard.domain.group.domain.member.CgGroupMemberMapper;
import hong.CashGuard.domain.group.dto.request.CgGroupChange;
import hong.CashGuard.domain.group.dto.request.CgGroupInvite;
import hong.CashGuard.domain.group.dto.request.CgGroupParam;
import hong.CashGuard.domain.group.dto.request.CgGroupSave;
import hong.CashGuard.domain.group.dto.request.member.CgGroupMemberApprove;
import hong.CashGuard.domain.group.dto.request.member.CgGroupMemberSave;
import hong.CashGuard.domain.group.dto.response.CgGroupAndMemberAndCategoryList;
import hong.CashGuard.domain.group.dto.response.CgGroupInfo;
import hong.CashGuard.domain.group.dto.response.CgGroupList;
import hong.CashGuard.domain.group.dto.response.category.CgGroupCategoryList;
import hong.CashGuard.domain.group.dto.response.member.CgGroupMemberList;
import hong.CashGuard.domain.user.service.CgUserService;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.exception.CGException;
import hong.CashGuard.global.util.AESUtil;
import hong.CashGuard.global.util.StringUtil;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.group.service
 * fileName       : CgGroupService
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 및 그룹 하위 사용자 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-03        work       * 그룹 하위 그룹멤버, 카테고리 로직 분리
 *                              * 그룹 생성/수정 시점에 카테고리 정보도 함께 추가/삭제
 * 2025-04-04        work       그룹 생성자(대표자) 정보도 그룹 멤버 테이블에 저장하도록 로직 변경
 *                              => 대표자는 자동 승인 처리, is_exponent 값은 Y
 */

@Service
@RequiredArgsConstructor
public class CgGroupService {

    private final CgGroupMapper mapper;
    private final CgGroupMemberMapper memberMapper;
    private final CgGroupCategoryMapper groupCategoryMapper;
    private final CgEmailLogService emailLogService;
    private final CgUserService userService;

    @Value("${hong.base-url}")
    private String baseUrl;

    /**
     * @method      saveGroup
     * @author      work
     * @date        2025-04-02
     * @deacription 새로운 그룹 생성
    **/
    @Transactional
    public void saveGroup(CgGroupSave request) {
        Long loginUserUid = UserUtil.getLoginUserUid();
        // 1. 그룹 생성
        CgGroup insertGroup = new CgGroup(request);
        mapper.insert(insertGroup);
        Long groupUid = insertGroup.getUid();

        // 2. 그룹 생성 대표자 -> 그룹 멤버에 추가 -> 자동 승인
        memberMapper.insert(new CgGroupMember(loginUserUid, groupUid, "Y", "Y"));
        memberMapper.approveMember(new CgGroupMember(loginUserUid, groupUid, "Y"));

        // 3. 그룹 하위 카테고리 추가
        for(Long categoryUid : request.getUids()) {
            groupCategoryMapper.insert(new CgGroupCategory(groupUid, categoryUid));
        }
    }

    /**
     * @method      changeGroup
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 수정
    **/
    @Transactional
    public void changeGroup(Long uid, CgGroupChange request) {
        CgGroup myView = mapper.view(uid);
        mapper.update(myView.changeGroup(uid, request));
        groupCategoryMapper.delete(uid);
        for(Long categoryUid : request.getUids()) {
            groupCategoryMapper.insert(new CgGroupCategory(uid, categoryUid));
        }
    }

    /**
     * @method      findAllPage
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<CgGroupList> findAllPage(CgGroupParam param, Pageable pageable) {
        List<CgGroupList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findAllList
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 목록 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<CgGroupList> findAllList(CgGroupParam param) {
        return mapper.list(param);
    }

    /**
     * @method      findGroupMember
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 하위 사용자 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<CgGroupMemberList> findGroupMember(Long groupUid) {
        return memberMapper.getAllGroupMember(groupUid);
    }

    /**
     * @method      saveGroupMember
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 하위 멤버 추가
    **/
    @Transactional
    public void saveGroupMember(CgGroupMemberSave request) {
        memberMapper.insert(new CgGroupMember(request));
    }

    /**
     * @method      canJoin
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹에 사용자 등록 전, 등록 가능한 사용자인지 체크
     *              > 이미 등록된 사용자인지 여부로 체크
    **/
    public boolean canJoin(Long groupUid, Long userUid) {
        Map<String, Object> params = Map.of(
                "groupUid", groupUid,
                "userUid", userUid
        );

        int ifExist = mapper.checkIfExist(params);
        return (ifExist == 0);
    }

    /**
     * @method      approveGroupMember
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 하위 멤버들 승인
    **/
    @Transactional
    public void approveGroupMember(CgGroupMemberApprove request) {
        for( Long userUid : request.getUids() ) {
            memberMapper.approveMember(new CgGroupMember(userUid, request.getGroupUid(), "Y"));
        }
    }

    /**
     * @method      findLoginUsersGroup
     * @author      work
     * @date        2025-04-02
     * @deacription 로그인 사용자의 그룹 목록 정보 조회 (with. 하위 사용자 목록 정보)
     *              -> 로그인 사용자가 [대표자]로 있는 그룹 정보만 조회 (리스트)
    **/
    public List<CgGroupAndMemberAndCategoryList> findLoginUsersGroup() {
        Long loginUserUid = UserUtil.getLoginUserUid();
        List<CgGroupAndMemberAndCategoryList> loginUsersGroup = mapper.getLoginUsersGroup(loginUserUid);
        if( loginUsersGroup != null && !loginUsersGroup.isEmpty() ) {
            for( CgGroupAndMemberAndCategoryList dto : loginUsersGroup ) {
                List<CgGroupMemberList> memberList = memberMapper.getAllGroupMember(dto.getGroupUid());
                List<CgGroupCategoryList> categoryList = groupCategoryMapper.getAllGroupCategory(dto.getGroupUid());
                dto.setMembers(memberList);
                dto.setCategories(categoryList);
            }
        }
        return loginUsersGroup;
    }

    /**
     * @method      inviteMember
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 하위 사용자 초대하기
    **/
    @Transactional
    public void inviteMember(CgGroupInvite request) {
        // 1. {groupUid} 값으로 그룹 정보 가져오기
        CgGroup groupView = mapper.view(request.getGroupUid());
        if( groupView == null ) {
            throw new CGException("해당되는 그룹 정보가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. {groupUid, reasonCode} 값을 갖고 이메일 토큰 발급
        String reasonCode = EmailSendReason.INVITE_GROUP.name();
        String emailToken = AESUtil.encrypt(groupView.getUid(), reasonCode);
        String invitationLink = String.format("%s/api/invite-link/%s", baseUrl, emailToken);

        // 3. {recipientEmail} 값으로 이미 등록된 유저인지 체크
        String recipientNm = request.getRecipientNm();
        String recipientEmail = request.getRecipientEmail();
        boolean ifAuthUser = userService.ifAuthUser(recipientEmail);
        String content = "";
        if( !ifAuthUser ) {
            // 3-1. 임시 회원가입
            String randomPassword = StringUtil.random(6);
            userService.insertTempUser(recipientNm, recipientEmail, randomPassword);
            content = this.createMailContentForNewUser(groupView.getGroupNm(), recipientNm, recipientEmail, invitationLink, randomPassword);

        } else {
            content = this.createMailContentForExistingUser(groupView.getGroupNm(), recipientNm, recipientEmail, invitationLink);
        }
        String title = this.createMailTitle(groupView.getGroupNm(), recipientNm);

        // 4. 이메일 전송 및 이메일 전송 로그 저장
        EmailLogSave emailBuilder = EmailLogSave.saveEmailLog()
                .emailToken(emailToken)
                .recipientEmail(recipientEmail)
                .subject(title)
                .content(content)
                .reasonCode(reasonCode)
                .build();
        emailLogService.EmailLogSaveAndSend(emailBuilder);
    }

    /**
     * @method      createMailTitle
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 초대 > 이메일 제목 생성
     **/
    private String createMailTitle(String groupNm, String recipientNm) {
        return String.format("[Cash Guard] %s님, '%s' 그룹 초대장이 도착했습니다!", recipientNm, groupNm);
    }

    /**
     * @method      createMailContentForNewUser
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 초대 > 이메일 내용 생성 (임시 회원가입 유저)
    **/
    private String createMailContentForNewUser(String groupNm, String recipientNm, String recipientEmail, String invitationLink, String rawPassword) {
        return """
        <body style="font-family: Arial, sans-serif; background-color: #f8f9fa; text-align: center; padding: 20px;">
            <div style="background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); max-width: 400px; margin: auto;">
                <h2 style="color: #333;">📩 Cash Guard 초대장</h2>
                <p><strong>%s</strong>님,</p>
                <p>당신을 <strong>%s</strong> 그룹으로 초대합니다!</p>
                <a href="%s" style="display: inline-block; padding: 12px 20px; font-size: 16px; color: white; background-color: #007bff; text-decoration: none; border-radius: 5px;">
                    📝 초대 수락하기 (신규 회원)
                </a>
                <p style="font-size: 14px; color: #666; margin-top: 15px;">
                    🔹 신규 가입 시, 아래 정보로 계정이 자동 생성됩니다.<br>
                    🔹 <strong>아이디:</strong> %s<br>
                    🔹 <strong>초기 비밀번호:</strong> %s<br>
                    🔹 <strong>이메일:</strong> %s<br>
                    🔹 로그인 후 반드시 비밀번호를 변경해 주세요.
                </p>
            </div>
        </body>
        """.formatted(recipientNm, groupNm, invitationLink, recipientEmail, rawPassword, recipientEmail);
    }


    /**
     * @method      createMailContentForExistingUser
     * @author      work
     * @date        2025-04-03
     * @deacription 그룹 초대 > 이메일 내용 생성 (기존 회원가입 유저)
    **/
    private String createMailContentForExistingUser(String groupNm, String recipientNm, String recipientEmail, String invitationLink) {
        return """
        <body style="font-family: Arial, sans-serif; background-color: #f8f9fa; text-align: center; padding: 20px;">
            <div style="background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); max-width: 400px; margin: auto;">
                <h2 style="color: #333;">📩 Cash Guard 초대장</h2>
                <p><strong>%s</strong>님,</p>
                <p>당신을 <strong>%s</strong> 그룹으로 초대합니다!</p>
                <a href="%s" style="display: inline-block; padding: 12px 20px; font-size: 16px; color: white; background-color: #28a745; text-decoration: none; border-radius: 5px;">
                    ✅ 초대 수락하기
                </a>
            </div>
        </body>
        """.formatted(recipientNm, groupNm, invitationLink);
    }

    /**
     * @method      saveInviteMember
     * @author      work
     * @date        2025-04-03
     * @deacription 초대한 사용자 그룹에 추가하기
    **/
    @Transactional
    public void saveInviteMember(Long userUid, Long groupUid) {
        memberMapper.insert(new CgGroupMember(userUid, groupUid));
        memberMapper.approveMember(new CgGroupMember(userUid, groupUid, "Y"));
    }

    /**
     * @method      findGroupListByUserUid
     * @author      work
     * @date        2025-04-04
     * @deacription 유저가 참여되어 있는 그룹 정보 리스트 조회
     *              => 활성화된 그룹만 조회 가능
     *              =>> 그룹 멤버 중에서 3명 이상이 승인 처리된 그룹만 활성화된 그룹이 된다.
    **/
    @Transactional(readOnly = true)
    public List<CgGroupInfo> findGroupListByUserUid(Long userUid) {
        return mapper.getGroupListByUserUid(userUid);
    }
}
