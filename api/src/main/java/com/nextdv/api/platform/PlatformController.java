package com.nextdv.api.platform;

import com.nextdv.api.common.ApiResponse;
import com.nextdv.domain.platform.Platform;
import com.nextdv.domain.platform.PlatformService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformController {

  private final PlatformService platformService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<PlatformResponse>>> list() {
    List<Platform> platforms = platformService.findAll();
    return ResponseEntity.ok(ApiResponse.ok(PlatformMapper.toResponseList(platforms)));
  }
}
