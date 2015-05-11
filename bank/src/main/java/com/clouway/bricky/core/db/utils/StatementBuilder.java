package com.clouway.bricky.core.db.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public interface StatementBuilder {

  PreparedStatement build() throws SQLException;

}
