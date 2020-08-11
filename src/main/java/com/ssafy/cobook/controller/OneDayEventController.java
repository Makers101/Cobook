package com.ssafy.cobook.controller;

import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.service.OneDayEventService;
import com.ssafy.cobook.service.dto.onedayevent.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/onedayevents")
@RequiredArgsConstructor
public class OneDayEventController {

    private static final Logger logger = LoggerFactory.getLogger(OneDayEventController.class);

    private final OneDayEventService oneDayEventService;

    @ApiOperation(value = "One Day event를 생성한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping
    public ResponseEntity<OneDayEventSaveResDto> saveOneDayEvent(@ApiIgnore final Authentication authentication,
                                                                 @RequestBody @Valid final OneDayEventSaveReqDto requestDto) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        OneDayEventSaveResDto saveResDto = oneDayEventService.saveEvent(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveResDto);
    }

    @ApiOperation(value = "전체 조회", response = OneDayEventResponseDto.class)
    @GetMapping
    public ResponseEntity<List<OneDayEventResponseDto>> getAllEvents() {
        List<OneDayEventResponseDto> responseDtos = oneDayEventService.getAllEvents();
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @ApiOperation(value = "상세 조회", response = OneDayEventDetailsDto.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{eventId}")
    public ResponseEntity<OneDayEventDetailsDto> getDetails(@ApiIgnore final Authentication authentication,
                                                            @PathVariable("eventId") final Long eventId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(oneDayEventService.getDetails(userId, eventId));
    }

    @ApiOperation(value = "이벤트에 가입한다. toggle")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/{eventId}/apply")
    public ResponseEntity<Void> applyEvents(@ApiIgnore final Authentication authentication,
                                            @PathVariable("eventId") final Long eventId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        oneDayEventService.applyEvent(userId, eventId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "이벤트 정보를 수정한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{eventId}")
    public ResponseEntity<Void> updateEvents(@ApiIgnore final Authentication authentication,
                                             @PathVariable("eventId") final Long eventId,
                                             @RequestBody @Valid final OneDayEventUpdateReqDto requestDto) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        oneDayEventService.updatePost(userId, eventId, requestDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "이벤트를 삭제한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvents(@ApiIgnore final Authentication authentication,
                                             @PathVariable("eventId") final Long eventId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        oneDayEventService.deleteEvents(userId, eventId);
        return ResponseEntity.ok().build();
    }
}
