package com.nextdv.domain.platform;

import java.util.List;

public interface PlatformRepository {

  List<Platform> findAll(boolean activeOnly);
}
