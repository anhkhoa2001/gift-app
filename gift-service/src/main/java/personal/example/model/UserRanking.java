package personal.example.model;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "user_rank")
public class UserRanking {

    @Id
    private Integer user_id;

    @Basic
    private int point;

    @Basic
    private Date date_point;
}
