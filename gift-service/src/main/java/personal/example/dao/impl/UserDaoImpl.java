package personal.example.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import personal.example.dao.UserDao;
import personal.example.dto.UserDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final DataSource dataSource;

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, List<UserDTO>> getAllUser() throws SQLException {
        Map<String, List<UserDTO>> map = new HashMap<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        StringBuilder sb = new StringBuilder();
        sb.append("select u.id, ur.point, u.date_created,\n" +
                "       CASE\n" +
                "        WHEN ur.point >= 1000000000 THEN 'TOP1'\n" +
                "        WHEN ur.point >= 500000000 && ur.point < 1000000000 THEN 'TOP2'\n" +
                "        WHEN ur.point >= 100000000 && ur.point < 500000000 THEN 'TOP3'\n" +
                "        ELSE 'TOP4'\n" +
                "    END AS top,\n" +
                "    CASE\n" +
                "        WHEN ur.point >= 1000000000 THEN u.gold + u.gem\n" +
                "        WHEN ur.point >= 500000000 && ur.point < 1000000000 THEN u.gold + u.gem\n" +
                "        WHEN ur.point >= 100000000 && ur.point < 500000000 THEN u.gem\n" +
                "        ELSE u.gold\n" +
                "    END AS gift,\n" +
                "    um.receive,\n" +
                "    um.mail_idx\n" +
                "from user u\n" +
                "right join user_rank ur on u.id = ur.user_id\n" +
                "left join user_mail um on u.id = um.user_id\n" +
                "where u.id is not null\n" +
                "order by ur.point desc");
        int ranktop1 = 0, ranktop2 = 10, ranktop3 = 30, ranktop4 = 100;
        try {
            connection = this.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserDTO item = UserDTO.builder()
                            .user_id(resultSet.getInt("id"))
                            .point(resultSet.getInt("point"))
                            .gift(resultSet.getInt("gift"))
                            .mail_idx(resultSet.getInt("mail_idx"))
                            .receive(resultSet.getString("receive"))
                            .date_created(resultSet.getDate("date_created")).build();
                String key = resultSet.getString("top");

                switch (key) {
                    case "TOP1":
                        item.setRank(++ranktop1);
                    case "TOP2":
                        item.setRank(++ranktop2);
                    case "TOP3":
                        item.setRank(++ranktop3);
                    default:
                        item.setRank(++ranktop4);
                }

                List<UserDTO> value = map.getOrDefault(key, new ArrayList<>());
                value.add(item);
                map.put(key, value);
            }
        } catch (Exception ex) {
            log.error("Occur error when get all user {}", ex.getMessage());
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }

        return map;
    }
}
