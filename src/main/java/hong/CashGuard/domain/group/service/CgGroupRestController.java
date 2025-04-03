package hong.CashGuard.domain.group.service;

import hong.CashGuard.domain.group.dto.request.CgGroupChange;
import hong.CashGuard.domain.group.dto.request.CgGroupInvite;
import hong.CashGuard.domain.group.dto.request.CgGroupParam;
import hong.CashGuard.domain.group.dto.request.CgGroupSave;
import hong.CashGuard.domain.group.dto.request.member.CgGroupMemberApprove;
import hong.CashGuard.domain.group.dto.request.member.CgGroupMemberSave;
import hong.CashGuard.domain.group.dto.response.CgGroupAndMemberAndCategoryList;
import hong.CashGuard.domain.group.dto.response.CgGroupList;
import hong.CashGuard.domain.group.dto.response.member.CgGroupMemberList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.bean.error.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.group.service
 * fileName       : CgGroupRestController
 * author         : work
 * date           : 2025-04-02
 * description    : 그룹 및 그룹 하위 사용자 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-03        work       응답 dto 파일명 변경 (CgGroupAndMemberList -> CgGroupAndMemberAndCategoryList)
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/group")
public class CgGroupRestController {

    private final CgGroupService service;

    /**
     *
     * 새로운 그룹 생성
     *
     * @api         [POST] /cguard/api/group
     * @author      work
     * @date        2025-04-02
    **/
    @PostMapping
    public ResponseEntity saveGroup(@RequestBody @Valid CgGroupSave request) {
        service.saveGroup(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 그룹 수정
     *
     * @api         [PUT] /cguard/api/group/{uid}
     * @author      work
     * @date        2025-04-02
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeGroup(@PathVariable("uid") Long uid, @RequestBody @Valid CgGroupChange request) {
        service.changeGroup(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 그룹 목록 조회 (페이징)
     *
     * @api         [GET] /cguard/api/group/page
     * @author      work
     * @date        2025-04-02
    **/
    @GetMapping("/page")
    public ResponseEntity findAllPage(@Valid CgGroupParam param, Pageable pageable) {
        Page<CgGroupList> allPage = service.findAllPage(param, pageable);
        return ResponseEntity.ok(allPage);
    }

    /**
     *
     * 그룹 목록 조회 (리스트)
     *
     * @api         [GET] /cguard/api/group/list
     * @author      work
     * @date        2025-04-02
    **/
    @GetMapping("/list")
    public ResponseEntity findAllList(@Valid CgGroupParam param) {
        List<CgGroupList> allList = service.findAllList(param);
        return ResponseEntity.ok(allList);
    }


    /**
     *
     * 그룹 하위 사용자 목록 조회
     *
     * @api         [GET] /cguard/api/group/member/{uid}
     * @author      work
     * @date        2025-04-02
    **/
    @GetMapping("/member/{uid}")
    public ResponseEntity findGroupMember(@PathVariable("uid") Long uid) {
        List<CgGroupMemberList> groupMember = service.findGroupMember(uid);
        return ResponseEntity.ok(groupMember);
    }

    /**
     *
     * 그룹 하위 멤버 추가 (신청)
     *
     * @api         [POST] /cguard/api/group/member
     * @author      work
     * @date        2025-04-02
    **/
    @PostMapping("/member")
    public ResponseEntity saveGroupMember(@RequestBody @Valid CgGroupMemberSave request) {

        if( !service.canJoin(request.getGroupUid(), request.getUserUid()) ) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, "이미 그룹에 등록된 사용자입니다.", "conflict");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(errorResponse);
        }

        service.saveGroupMember(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 그룹 하위 멤버들 승인
     * > 한번에 여러명 승인 가능
     *
     * @api         [PUT] /cguard/api/group/approve-member
     * @author      work
     * @date        2025-04-02
    **/
    @PutMapping("/approve-member")
    public ResponseEntity approveGroupMember(@RequestBody @Valid CgGroupMemberApprove request) {
        service.approveGroupMember(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 현재 로그인한 사용자의 그룹 정보 조회
     * > 그룹 하위 멤버 및 카테고리 목록 함께 조회
     *
     * @api         [GET] /cguard/api/group/exponent
     * @author      work
     * @date        2025-04-02
    **/
    @GetMapping("/exponent")
    public ResponseEntity findLoginUsersGroup() {
        List<CgGroupAndMemberAndCategoryList> loginUsersGroup = service.findLoginUsersGroup();
        return ResponseEntity.ok(loginUsersGroup);
    }

    /**
     *
     * 그룹 하위 사용자 초대하기
     *
     * @api         [POST] /cguard/api/group/member/invite
     * @author      work
     * @date        2025-04-02
    **/
    @PostMapping("/member/invite")
    public ResponseEntity inviteMember(@RequestBody @Valid CgGroupInvite request) {
        service.inviteMember(request);
        return ResponseEntity.ok().build();
    }
}
