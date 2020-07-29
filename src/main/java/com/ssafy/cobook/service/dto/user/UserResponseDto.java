package com.ssafy.cobook.service.dto.user;
import com.ssafy.cobook.domain.user.PlatformType;
import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private String nickName;
    private String description;
    private String profileImg;
    private PlatformType platformType;
    private String platformId;

    public UserResponseDto(User user) {
        id = user.getId();
        email = user.getEmail();
        password = user.getPassword();
        nickName = user.getNickName();
        description = user.getDescription();
        profileImg = "http://i3a111.p.ssafy.io:8080/api/profile/images/" + id;
        platformType = user.getPlatformType();
        platformId = user.getPlatformId();
    }
}
