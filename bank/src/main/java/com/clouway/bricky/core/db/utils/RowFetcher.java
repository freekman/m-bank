package com.clouway.bricky.core.db.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public interface RowFetcher<T> {

  T fetch(ResultSet resultSet) throws SQLException;

}
