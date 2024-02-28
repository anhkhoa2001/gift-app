package personal.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import personal.example.dao.UserDao;
import personal.example.dto.UserDTO;
import personal.example.model.UserModel;

import java.util.List;

@Repository
public interface UserRepo  extends JpaRepository<UserModel, Integer>, UserDao {

    @Query(value = "select u.id, ur.point, ur.point as gift, u.date_created from user u\n" +
            "right join user_rank ur on u.id = ur.user_id\n" +
            "where u.id is not null\n" +
            "order by ur.point desc", nativeQuery = true)
    List<UserDTO> getUser();
}
