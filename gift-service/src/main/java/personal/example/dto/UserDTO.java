package personal.example.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserDTO {
    private int user_id;
    private int point;
    private int gift;
    private int rank;
    private Date date_created;
    private String receive;
    private int mail_idx;
}
