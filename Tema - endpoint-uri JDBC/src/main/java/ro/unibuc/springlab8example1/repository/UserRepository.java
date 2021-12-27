package ro.unibuc.springlab8example1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserDetails;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User save(User user) {
        String saveUserSql = "INSERT INTO users (username, full_name, user_type, account_created) VALUES (?,?,?,?)";
        jdbcTemplate.update(saveUserSql, user.getUsername(), user.getFullName(), user.getUserType().name(), LocalDateTime.now());

        User savedUser = getUserWith(user.getUsername());
        UserDetails userDetails = user.getUserDetails();

        if (userDetails != null) {
            String saveUserDetailsSql = "INSERT INTO user_details (cnp, age, other_information) VALUES (?, ?, ?)";
            jdbcTemplate.update(saveUserDetailsSql, userDetails.getCnp(), userDetails.getAge(), userDetails.getOtherInformation());

            UserDetails savedUserDetails = getUserDetailsWith(userDetails.getCnp());
            savedUser.setUserDetails(savedUserDetails);

            String saveUsersUserDetails = "INSERT INTO users_user_details (users, user_details) VALUES (?, ?)";
            jdbcTemplate.update(saveUsersUserDetails, savedUser.getId(), savedUserDetails.getId());
        }

        return savedUser;
    }

    public User get(String username) {
        return getAllUserInfo(username);
    }

    private User getUserWith(String username) {
        String selectSql = "SELECT * from users WHERE users.username = ?";
        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .build();

        List<User> users = jdbcTemplate.query(selectSql, rowMapper, username);

        if (!users.isEmpty()) {
            return users.get(0);
        }

        throw new UserNotFoundException("User not found");
    }

    private UserDetails getUserDetailsWith(String cnp) {
        String selectSql = "SELECT * from user_details WHERE user_details.cnp = ?";
        RowMapper<UserDetails> rowMapper = (resultSet, rowNo) -> UserDetails.builder()
                .id(resultSet.getLong("id"))
                .cnp(resultSet.getString("cnp"))
                .age(resultSet.getInt("age"))
                .otherInformation(resultSet.getString("other_information"))
                .build();

        List<UserDetails> details = jdbcTemplate.query(selectSql, rowMapper, cnp);

        if (!details.isEmpty()) {
            return details.get(0);
        }

        throw new UserNotFoundException("User details not found");
    }

    private User getAllUserInfo(String username) {
        String sql = "select u.id, username, full_name, user_type, account_created, cnp, age, other_information " +
                " from users u " +
                " join user_details ud on u.id = ud.id " +
                " WHERE u.username = ?";

        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("u.id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
//                .accountCreated()
                .userDetails(UserDetails.builder()
                        .id(resultSet.getLong("id"))
                        .cnp(resultSet.getString("cnp"))
                        .age(resultSet.getInt("age"))
                        .otherInformation(resultSet.getString("other_information"))
                        .build())
                .build();
        List<User> users = jdbcTemplate.query(sql, rowMapper, username);

        if (!users.isEmpty()) {
            return users.get(0);
        }

        throw new UserNotFoundException("User details not found");
    }

    public User updateStudent(User user, String oldUserName) {
        boolean userNameUpdated = false;
        if (user.getUsername() != null || user.getFullName() != null) {
            String updateUserSql = createUpdateUserStatement(user);
            int updatedRows =0;
            if (user.getUsername() != null && user.getFullName() != null) {
                updatedRows = jdbcTemplate.update(updateUserSql, user.getUsername(),
                        user.getFullName(),
                        oldUserName);
            } else if (user.getUsername() != null) {
                updatedRows = jdbcTemplate.update(updateUserSql, user.getUsername(),
                        oldUserName);
            } else if (user.getFullName() != null) {
                updatedRows = jdbcTemplate.update(updateUserSql, user.getFullName(),
                        oldUserName);
            }

            System.out.println(updatedRows);

            if (updatedRows != 0 && user.getUsername() != null) {
                userNameUpdated = true;
            }
        }
        User updatedUser = null;
        if (userNameUpdated) {
             updatedUser = getUserWith(user.getUsername());
        } else {
            updatedUser = getUserWith(oldUserName);
        }

        if (updatedUser != null &&
                (user.getUserDetails().getAge() != null || user.getUserDetails().getOtherInformation() != null)) {
            updateUsersDetails(user, updatedUser);
            if (user.getUsername() != null){
                updatedUser = getAllUserInfo(user.getUsername());
            } else {
                updatedUser = getAllUserInfo(oldUserName);
            }
        }

        return updatedUser;
    }

    private void updateUsersDetails(User toUpdateUser, User updatedUser) {
        String updateUserSql = createUpdateUserDetailsStatement(toUpdateUser.getUserDetails());
        int updatedRows = 0;
        UserDetails userDetails = toUpdateUser.getUserDetails();
        if (userDetails.getAge() != null && userDetails.getOtherInformation() != null) {
            updatedRows = jdbcTemplate.update(updateUserSql, userDetails.getAge(),
                    userDetails.getOtherInformation(),
                    updatedUser.getId());
        } else if (userDetails.getAge() != null) {
            updatedRows = jdbcTemplate.update(updateUserSql, userDetails.getAge(),
                    updatedUser.getId());
        } else if (userDetails.getOtherInformation() != null){
            updatedRows = jdbcTemplate.update(updateUserSql,
                    userDetails.getOtherInformation(),
                    updatedUser.getId());
        }

        System.out.println(updatedRows);

    }

    private String createUpdateUserStatement(User user) {
        String updateUserSql = "UPDATE users SET ";
        if (user.getUsername() != null) {
            updateUserSql += "username = ? ";
        }

        if (user.getFullName() != null) {
            if (user.getUsername() != null) {
                updateUserSql += ", ";
            }
            updateUserSql += " full_name = ? ";
        }

        updateUserSql += " where username = ?";

        return updateUserSql;
    }

    private String createUpdateUserDetailsStatement(UserDetails userDetails) {
        String updateUserSql = "UPDATE user_details SET ";
        if (userDetails.getAge() != null) {
            updateUserSql += "age = ? ";
        }

        if (userDetails.getOtherInformation() != null) {
            if (userDetails.getAge() != null) {
                updateUserSql += ", ";
            }
            updateUserSql += " other_information = ? ";
        }

        updateUserSql += " where id = ?";

        return updateUserSql;
    }


    private String quote(String s) {
        return new StringBuilder()
                .append('\'')
                .append(s)
                .append('\'')
                .toString();
    }

    public User delete(String userName) {
        User userToDelete = getAllUserInfo(userName);

        if (userToDelete == null) {
            throw new UserNotFoundException("User not found");
        }
        String deleteUserUsersDetails = "delete from users_user_details where users = ? and user_details = ?";
        String deleteUserDetails = "delete from user_details where id = ?";
        String deleteUser = "delete from users where id = ?";

        int updatedRows = 0;

        updatedRows = jdbcTemplate.update(deleteUserUsersDetails, userToDelete.getId(), userToDelete.getUserDetails().getId());
        if (updatedRows == 0) {
            throw new UserNotFoundException("Could not delete users_user_details.");
        }

        updatedRows = jdbcTemplate.update(deleteUserDetails, userToDelete.getUserDetails().getId());
        if (updatedRows == 0) {
            throw new UserNotFoundException("Could not delete user details.");
        }

        updatedRows = jdbcTemplate.update(deleteUser, userToDelete.getId());
        if (updatedRows == 0) {
            throw new UserNotFoundException("Could not delete user.");
        }

        return userToDelete;
    }

    public List<User> getByType(UserType type) {
        String sql = "select u.id, username, full_name, user_type, account_created, cnp, age, other_information " +
                " from users u " +
                " join user_details ud on u.id = ud.id " +
                " WHERE u.user_type = ?";

        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("u.id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
//                .accountCreated()
                .userDetails(UserDetails.builder()
                        .id(resultSet.getLong("id"))
                        .cnp(resultSet.getString("cnp"))
                        .age(resultSet.getInt("age"))
                        .otherInformation(resultSet.getString("other_information"))
                        .build())
                .build();
        List<User> users = jdbcTemplate.query(sql, rowMapper, type.toString());

        if (!users.isEmpty()) {
            return users;
        }
        throw new UserNotFoundException("No users was found with the following type: " + type);
    }
}
