package com.nextdv.api.platform;

import com.nextdv.api.common.CommonResponse;
import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.PlatformService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformController {

  private final PlatformService platformService;

  @GetMapping
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<List<PlatformResponse>>> list() {
    List<Platform> platforms = platformService.findAll();
    return ResponseEntity.ok(CommonResponse.ok(PlatformMapper.toResponseList(platforms)));
  }

  @GetMapping("/{id}")
  @ApiResponse(responseCode = "400", description = "id가 UUID 형식이 아님")
  @ApiResponse(responseCode = "404", description = "해당 ID에 해당하는 플랫폼이 존재하지 않음")
  @ApiResponse(responseCode = "500", description = "서버 내부 오류")
  public ResponseEntity<CommonResponse<PlatformResponse>> findById(@PathVariable UUID id) {
    Platform platform = platformService
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("플랫폼을 찾을 수 없습니다."));
    return ResponseEntity.ok(CommonResponse.ok(PlatformMapper.toResponse(platform)));
  }
}
