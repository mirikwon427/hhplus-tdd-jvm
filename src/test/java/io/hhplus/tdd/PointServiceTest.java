package io.hhplus.tdd;

import static org.assertj.core.api.Assertions.assertThat;

import io.hhplus.tdd.point.PointService;
import org.junit.jupiter.api.Test;

public class PointServiceTest {

  @Test
  void getUserPoint() {
    long id = 1L;
    long point = 100l;

    PointService pointService = new PointService();

    long userPoint = pointService.getUserPoint(id);

    assertThat(userPoint).isEqualTo(point);
  }

}
