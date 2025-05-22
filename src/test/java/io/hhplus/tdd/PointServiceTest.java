package io.hhplus.tdd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PointServiceTest {

  @Test
  @DisplayName("유저 포인트를 조회한다.")
  void getUserPoint() {
    // given
    long id = 1L;
    long point = 100l;

    UserPointTable userPointTable = mock(UserPointTable.class);
    when(userPointTable.selectById(id)).thenReturn(new UserPoint(id, point, 0));

    // when
    PointService pointService = new PointService(userPointTable);
    long userPoint = pointService.getUserPoint(id);

    // then
    assertThat(userPoint).isEqualTo(point);
  }



}
