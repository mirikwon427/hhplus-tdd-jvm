package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PointService {

  private final UserPointTable userPointTable;
  private final PointHistoryTable pointHistoryTable;

  public PointService(UserPointTable userPointTable, PointHistoryTable pointHistoryTable) {
    this.userPointTable = userPointTable;
    this.pointHistoryTable = pointHistoryTable;
  }

  public long getUserPoint(long id) {
    UserPoint userPoint = userPointTable.selectById(id);
    return userPoint.point();
  }

  public List<PointHistory> getPointHistories(long userId) {
    return pointHistoryTable.selectAllByUserId(userId);
  }
}
