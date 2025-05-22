package io.hhplus.tdd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import java.util.List;
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
    PointHistoryTable pointHistoryTable = mock(PointHistoryTable.class);
    when(userPointTable.selectById(id)).thenReturn(new UserPoint(id, point, 0));

    // when
    PointService pointService = new PointService(userPointTable, pointHistoryTable);
    long userPoint = pointService.getUserPoint(id);

    // then
    assertThat(userPoint).isEqualTo(point);
  }

  @DisplayName("유저의 포인트 내역을 조회한다.")
  @Test
  void testGetUserPointHistories() {
    // given
    long userId = 1L;

    UserPointTable userPointTable = mock(UserPointTable.class);
    PointHistoryTable pointHistoryTable = mock(PointHistoryTable.class);

    List<PointHistory> pointHistories = List.of(
        new PointHistory(1L, userId, 100, TransactionType.CHARGE,0),
        new PointHistory(2L, userId, 200, TransactionType.USE,0),
        new PointHistory(3L, userId, 300, TransactionType.CHARGE,0)
    );
    when(pointHistoryTable.selectAllByUserId(userId)).thenReturn(pointHistories);

    // when
    PointService pointService = new PointService(userPointTable, pointHistoryTable);
    List<PointHistory> histories = pointService.getPointHistories(userId);

    // then
    assertThat(pointHistories).isNotEmpty();
    assertThat(histories.get(0).amount()).isEqualTo(pointHistories.get(0).amount());
  }

  @DisplayName("유저가 포인트를 충전한다.")
  @Test
  void testChargeUserPoint() {
    // given
    long userId = 1L;
    long amount = 100L;
    UserPoint userPoint = new UserPoint(userId, 0, 0);

    UserPointTable userPointTable = mock(UserPointTable.class);
    PointHistoryTable pointHistoryTable = mock(PointHistoryTable.class);
    when(userPointTable.selectById(userId)).thenReturn(userPoint);

    // when
    PointService pointService = new PointService(userPointTable, pointHistoryTable);
    UserPoint chargedPoint =pointService.chargeUserPoint(userId, amount);
    pointService.insertPointHistory(userId, amount, TransactionType.CHARGE);

    // then
    verify(userPointTable).insertOrUpdate(userId, amount);
    verify(pointHistoryTable).insert(userId, amount, TransactionType.CHARGE, 0);
    assertThat(chargedPoint.point()).isEqualTo(userPoint.point() + amount);
  }

}
