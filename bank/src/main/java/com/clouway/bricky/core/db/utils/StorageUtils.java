package com.clouway.bricky.core.db.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class StorageUtils {

  public static void close(AutoCloseable pipe) {
    if (pipe != null) {
      try {
        pipe.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static <T> T fetch(StatementBuilder stmtBuilder, RowFetcher<T> fetcher) {
    PreparedStatement pstm = null;
    ResultSet resultSet = null;
    try {
      pstm = stmtBuilder.build();
      resultSet = pstm.executeQuery();
      if (resultSet.next()) {
        return fetcher.fetch(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(resultSet);
      close(pstm);
    }
    return null;
  }

  public static void executeUpdate(StatementBuilder stmtBuilder) {
    PreparedStatement pstmt = null;
    try {
      pstmt = stmtBuilder.build();
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      close(pstmt);
    }
  }

}
