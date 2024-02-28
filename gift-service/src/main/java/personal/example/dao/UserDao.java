package personal.example.dao;

import personal.example.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserDao {

    Map<String, List<UserDTO>> getAllUser() throws SQLException;
}
