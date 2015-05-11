package com.clouway.bricky.core.db.user;

import com.clouway.bricky.core.db.utils.RowFetcher;
import com.clouway.bricky.core.db.utils.StatementBuilder;
import com.clouway.bricky.core.db.utils.StorageUtils;
import com.clouway.bricky.core.user.UserDTO;
import com.google.inject.Inject;

import javax.inject.Provider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class PersistentUserRepository implements UserRepository {


  private final Provider<Connection> connectionProvider;

  @Inject
  public PersistentUserRepository(Provider<Connection> connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  @Override
  public boolean register(final UserDTO user) {

    if (isExisting(user.username)) {
      return false;
    }

    final Connection con = connectionProvider.get();

    StorageUtils.executeUpdate(new StatementBuilder() {
      @Override
      public PreparedStatement build() throws SQLException {
        PreparedStatement pstm = con.prepareStatement("INSERT INTO registrations (username, password) VALUES (?,?)");
        pstm.setString(1, user.username);
        pstm.setString(2, user.password);
        return pstm;
      }
    });

    return true;
  }

  public boolean isExisting(final String username) {
    final Connection con = connectionProvider.get();

    Boolean isExisting = StorageUtils.fetch(new StatementBuilder() {
      @Override
      public PreparedStatement build() throws SQLException {
        PreparedStatement pstm = con.prepareStatement("SELECT COUNT(username) AS nameCount FROM registrations WHERE username = ?");
        pstm.setString(1, username);
        return pstm;
      }
    }, new RowFetcher<Boolean>() {
      @Override
      public Boolean fetch(ResultSet resultSet) throws SQLException {
        int count = resultSet.getInt(1);
        if (count == 0) {
          return false;
        }
        return true;
      }
    });
    return (isExisting == null ? false : isExisting);
  }


  //
//  @Inject
//  public PersistentUserRepository(PooledConnectionProvider provider) {
//    this.provider = provider;
//  }
//
//  @Override
//  public boolean registerIfNotExisting(final User newEntity) {
//    if (isExisting(newEntity)) {
//      return false;
//    }
//
//    final Connection con = provider.get();
//
//    StorageUtils.executeUpdate(new StatementBuilder() {
//      @Override
//      public PreparedStatement build() throws SQLException {
//        PreparedStatement pstm = con.prepareStatement("INSERT INTO registrations (username, password) VALUES (?,?)");
//        pstm.setString(1, newEntity.username);
//        pstm.setString(2, newEntity.password);
//        return pstm;
//      }
//    });
//    return true;
//  }
//
//  @Override
//  public boolean isUserAuthentic(final User entity) {
//    final Connection con = provider.get();
//
//    Boolean isAuthentic = StorageUtils.fetch(new StatementBuilder() {
//      @Override
//      public PreparedStatement build() throws SQLException {
//        PreparedStatement pstm = con.prepareStatement("SELECT password FROM registrations WHERE username = ?");
//        pstm.setString(1, entity.username);
//        return pstm;
//      }
//    }, new RowFetcher<Boolean>() {
//      @Override
//      public Boolean fetch(ResultSet resultSet) throws SQLException {
//        String password = resultSet.getString(1);
//        if (password.equals(entity.password)) {
//          return true;
//        }
//        return false;
//      }
//    });
//    return (isAuthentic == null ? false : isAuthentic);
//  }
//
//  @Override
//  public int getIdOf(final User registeredUser) {
//    if (!isExisting(registeredUser)) {
//      return -1;
//    }
//    final Connection con = provider.get();
//    Integer userID = StorageUtils.fetch(new StatementBuilder() {
//      @Override
//      public PreparedStatement build() throws SQLException {
//        PreparedStatement pstm = con.prepareStatement("SELECT _id FROM registrations WHERE  username = ?");
//        pstm.setString(1, registeredUser.username);
//        return pstm;
//      }
//    }, new RowFetcher<Integer>() {
//      @Override
//      public Integer fetch(ResultSet resultSet) throws SQLException {
//        return resultSet.getInt(1);
//      }
//    });
//    if (userID == null) {
//      throw new NonExistingRegistrationException("No existing registration for user with name:" + registeredUser.username);
//    }
//
//    return userID;
//  }
//
//  @Override
//  public String getName(final int userId) {
//    final Connection con = provider.get();
//    String name = StorageUtils.fetch(new StatementBuilder() {
//      @Override
//      public PreparedStatement build() throws SQLException {
//        PreparedStatement pstm = con.prepareStatement("SELECT username FROM registrations WHERE _id = ?");
//        pstm.setInt(1, userId);
//        return pstm;
//      }
//    }, new RowFetcher<String>() {
//      @Override
//      public String fetch(ResultSet resultSet) throws SQLException {
//        return resultSet.getString(1);
//      }
//    });
//
//    if (name == null) {
//      throw new NonExistingRegistrationException("No existing registration for user with id:" + userId);
//    }
//    return name;
//  }
//

}