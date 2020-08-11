package com.ssafy.cobook.service;

import com.google.firebase.database.*;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.clubmember.ClubMemberRepository;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.follow.FollowRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.clubmember.ClubMemberResponseDto;
import com.ssafy.cobook.service.dto.notification.NotificationReqDto;
import com.ssafy.cobook.service.dto.notification.NotificationSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final FollowRepository followRepository;


    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    @Transactional
    public void saveNoti(NotificationReqDto notificationReqDto, Long fromUserId) {
        LocalDateTime curDateTime = LocalDateTime.now();
        String nowDate = curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));


        // 저장할 데이터
        NotificationSaveDto notificationSaveDto = new NotificationSaveDto(nowDate, notificationReqDto.getDataId(), fromUserId, notificationReqDto.getIsRead(), notificationReqDto.getType());

        String type = notificationReqDto.getType();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("noti"); // 최상위 root: noti
        Long toId = 0L;


        Club club = null;

        if (type.equals("club")) {
            club = clubRepository.findById(notificationReqDto.getDataId())
                    .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_CLUB));
            List<ClubMember> clubMemberList = clubMemberRepository.findByClub(club); // 해당 클럽의 멤버들을 뽑아서
            for (ClubMember clubMember : clubMemberList) {
                if (clubMember.getRole().equals(MemberRole.LEADER)) {
                    toId = clubMember.getUser().getId();
                }
            }
        } else {
            toId = notificationReqDto.getToUserId();
        }
        User fromUser = getUser(fromUserId);
        User toUser = getUser(toId);


        DatabaseReference notiRef = ref.child(toId.toString()); // 알림 받는 사람의 아이디
        DatabaseReference nextNotiRef = notiRef.push(); // 다음 키값으로 푸시
        String postId = nextNotiRef.getKey(); // 현재 알람의 키값을 가져옴
        DatabaseReference saveNoti = notiRef.child(postId); // to의 아이디 값의 child node


        if (type.equals("club")) { // 클럽 가입 신청
            if (!clubMemberRepository.findByUserAndClub(fromUser, club).isPresent()) { // 가입신청을 눌러서 데이터가 생김 => 데이터가 없을 때 삭제 알림이 가야함
                notiRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        exFindData:
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String postKey = data.getKey();
                            for (DataSnapshot values : data.getChildren()) {
                                if (values.getKey().equals("from")) {
                                    if (values.getValue() == fromUserId) {
                                        DatabaseReference deleteRef = notiRef.child(postKey);
                                        deleteRef.removeValueAsync();
                                        break exFindData;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            } else { // 테이블에 값이 존재하는 경우에는 가입신청을 한거라서, 데이터를 생성해줌
                saveNoti.setValueAsync(notificationSaveDto);
            }
        } else if (type.equals("follow")) { // 팔로우
            if (!followRepository.findByToUser(fromUser, toUser).isPresent()) { // 팔로우를 신청했다면 테이블에 이미 존재하므로 없을 때 삭제
                notiRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        exFindData:
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String postKey = data.getKey();
                            for (DataSnapshot values : data.getChildren()) {
                                if (values.getKey().equals("from")) {
                                    if (values.getValue() == fromUserId) {
                                        DatabaseReference deleteRef = notiRef.child(postKey);
                                        deleteRef.removeValueAsync();
                                        break exFindData;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            } else { //아닌 경우는 저장
                saveNoti.setValueAsync(notificationSaveDto);
            }
        } else if (type.equals("like")) { // 좋아요

        } else { // 댓글

        }
    }

    @Transactional
    public void readNoti(String notiId, Long userId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("noti"); // 최상위 root: noti
        DatabaseReference notiRef = ref.child(userId.toString()); // noti의 child node: to의 아이디 값
        DatabaseReference updateRef = notiRef.child(notiId);
        updateRef.child("isRead").setValueAsync(true);
    }

    @Transactional
    public void deleteNoti(String notiId, Long userId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("noti"); // 최상위 root: noti
        DatabaseReference notiRef = ref.child(userId.toString()); // noti의 child node: to의 아이디 값
        DatabaseReference deleteRef = notiRef.child(notiId);
        deleteRef.removeValueAsync();
    }

}
