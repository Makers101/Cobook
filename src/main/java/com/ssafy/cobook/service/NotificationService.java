package com.ssafy.cobook.service;

import com.google.firebase.database.*;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.club.ClubRepository;
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
        User fromUser = getUser(fromUserId);
        User toUser = getUser(notificationReqDto.getToUserId());

        if (type.equals("club")) {
            club = clubRepository.findById(notificationReqDto.getDataId())
                    .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_CLUB));
            toId = clubMemberRepository.findByClub(club).stream() // 해당 클럽의 멤버들을 뽑아서
                    .filter(c -> c.getRole().equals(MemberRole.LEADER)) // 리더인 애를 찾고
                    .map(ClubMemberResponseDto::new)
                    .map(cm -> cm.getId())
                    .collect(Collectors.toList()).get(0);

            System.out.println("클럽의 리더 아이디" + toId);
        } else {
            toId = notificationReqDto.getToUserId();
        }


        DatabaseReference notiRef = ref.child(toId.toString()); // 알림 받는 사람의 아이디
        DatabaseReference nextNotiRef = notiRef.push(); // 다음 키값으로 푸시
        String postId = nextNotiRef.getKey(); // 현재 알람의 키값을 가져옴
        DatabaseReference saveNoti = notiRef.child(postId); // to의 아이디 값의 child node


        if (type.equals("club")) { // 클럽 가입 신청
        

        } else if (type.equals("follow")) { // 팔로우
            if (followRepository.findByToUser(fromUser, toUser).isPresent()) { // 이미 팔로우 되있는 경우면 noti에서 삭제
                notiRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        exFindData:
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String postKey = data.getKey();
                            System.out.println("포스트의 키 값" + postKey);
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
