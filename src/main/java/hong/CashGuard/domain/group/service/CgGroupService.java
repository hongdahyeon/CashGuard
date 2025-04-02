package hong.CashGuard.domain.group.service;

import hong.CashGuard.domain.code.EmailSendReason;
import hong.CashGuard.domain.emaillog.dto.request.EmailLogSave;
import hong.CashGuard.domain.emaillog.service.CgEmailLogService;
import hong.CashGuard.domain.group.domain.CgGroup;
import hong.CashGuard.domain.group.domain.CgGroupMapper;
import hong.CashGuard.domain.group.domain.member.CgGroupMember;
import hong.CashGuard.domain.group.dto.request.CgGroupChange;
import hong.CashGuard.domain.group.dto.request.CgGroupInvite;
import hong.CashGuard.domain.group.dto.request.CgGroupParam;
import hong.CashGuard.domain.group.dto.request.CgGroupSave;
import hong.CashGuard.domain.group.dto.request.member.CgGroupMemberApprove;
import hong.CashGuard.domain.group.dto.request.member.CgGroupMemberSave;
import hong.CashGuard.domain.group.dto.response.CgGroupAndMemberList;
import hong.CashGuard.domain.group.dto.response.CgGroupList;
import hong.CashGuard.domain.group.dto.response.CgGroupMemberList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.exception.CGException;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
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
 */

@Service
@RequiredArgsConstructor
public class CgGroupService {

    private final CgGroupMapper mapper;
    private final CgEmailLogService emailLogService;

    /**
     * @method      saveGroup
     * @author      work
     * @date        2025-04-02
     * @deacription 새로운 그룹 생성
    **/
    @Transactional
    public void saveGroup(CgGroupSave request) {
        Long loginUserUid = UserUtil.getLoginUserUid();
        mapper.insert(new CgGroup(loginUserUid, request));
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
        return mapper.getAllGroupMember(groupUid);
    }

    /**
     * @method      saveGroupMember
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 하위 멤버 추가
    **/
    @Transactional
    public void saveGroupMember(CgGroupMemberSave request) {
        mapper.insertMember(new CgGroupMember(request));
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
            mapper.approveMember(new CgGroupMember(userUid, request.getGroupUid(), "Y"));
        }
    }

    /**
     * @method      findLoginUsersGroup
     * @author      work
     * @date        2025-04-02
     * @deacription 로그인 사용자의 그룹 목록 정보 조회 (with. 하위 사용자 목록 정보)
    **/
    public List<CgGroupAndMemberList> findLoginUsersGroup() {
        Long loginUserUid = UserUtil.getLoginUserUid();
        List<CgGroupAndMemberList> loginUsersGroup = mapper.getLoginUsersGroup(loginUserUid);
        if( loginUsersGroup != null && !loginUsersGroup.isEmpty() ) {
            for( CgGroupAndMemberList dto : loginUsersGroup ) {
                List<CgGroupMemberList> memberList = mapper.getAllGroupMember(dto.getUid());
                dto.setMembers(memberList);
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

        // 2. 전송할 이메일 정보 세팅하기
        // => 제목, 내용, 이메일 전송 이유, 이메일 토큰
        String groupNm = groupView.getGroupNm();
        String title = this.createMailTitle(groupNm, request.getRecipientNm());
        String content = this.createMailContent(groupNm, request.getRecipientNm());
        String reasonCode = EmailSendReason.INVITE_GROUP.name();
        // TODO MAKE EMAIL TOKEN > groupUid, reasonCode
        String emailToken = "";

        // 3. 이메일 전송 및 이메일 전송 로그 저장
        EmailLogSave emailBuilder = EmailLogSave.saveEmailLog()
                .emailToken(emailToken)
                .recipientEmail(request.getRecipientEmail())
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
        // TODO : 제목 템플릿 만들기
        String title = "";
        return title;
    }

    /**
     * @method      createMailContent
     * @author      work
     * @date        2025-04-02
     * @deacription 그룹 초대 > 이메일 내용 생성
    **/
    private String createMailContent(String groupNm, String recipientNm) {
        // TODO : 내용 템플릿 만들기
        String content = "";
        return content;
    }
}
