package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.ReadingService;
import com.ssafy.cobook.service.dto.reading.ReadingApplyReqDto;
import com.ssafy.cobook.service.dto.reading.ReadingDetailResDto;
import com.ssafy.cobook.service.dto.reading.ReadingSaveReqDto;
import com.ssafy.cobook.service.dto.reading.ReadingSaveResDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/readings")
@RequiredArgsConstructor
public class ReadingController {

    private static final Logger logger = LoggerFactory.getLogger(ReadingController.class);

    private final ReadingService readingService;

    @ApiOperation(value = "리딩에 신청한다")
    @PostMapping("/clubs/users")
    public ResponseEntity<Void> applyReading(@RequestBody ReadingApplyReqDto reqDto) {
        readingService.applyReading(reqDto);
        return ResponseEntity.ok().build();
    }
}