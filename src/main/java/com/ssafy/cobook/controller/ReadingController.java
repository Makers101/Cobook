package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.ReadingService;
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

    @ApiOperation(value = "리딩을 생성한다.")
    @PostMapping
    public ResponseEntity<ReadingSaveResDto> makeReading(@RequestBody final ReadingSaveReqDto reqDto) {
        ReadingSaveResDto resDto = readingService.makeReading(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @ApiOperation(value ="리딩의 상세정보를 조회한다")
    @GetMapping("/clubs/{clubId}/readings/{readingId}")
    public ResponseEntity<ReadingDetailResDto> getReadingDetail(@PathVariable("clubId") final Long clubId,
                                                                @PathVariable("readingId") final Long readingId) {
        ReadingDetailResDto resDto = readingService.getDetails(clubId, readingId);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }
    
}
