package io.hhplus.tdd.point;

import io.hhplus.tdd.database.UserPointTable;
import org.springframework.stereotype.Service;

@Service
public class PointService {

  private final UserPointTable userPointTable;

  public PointService(UserPointTable userPointTable) {
    this.userPointTable = userPointTable;
  }

  public long getUserPoint(long id) {
    UserPoint userPoint = userPointTable.selectById(id);
    return userPoint.point();
  }
}
