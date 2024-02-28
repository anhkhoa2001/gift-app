package personal.example.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    private int server;

    @Basic
    private String username;

    @Basic
    private int avatar;

    @Basic
    private int level;

    @Basic
    private int gold;

    @Basic
    private int gem;

    @Basic
    private int vip;

    @Basic
    private String cp;

    @Basic
    private Date date_created;
}
