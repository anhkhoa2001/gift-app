package personal.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private String title;
    private String message;
    private int bonus;
    private int mailidx;
    private String receiver;

    public Notification(UserDTO user) {
        this.bonus = user.getGift();
        this.title = "Phần thưởng xếp hạng người chơi ";
        this.message = "Chúc mừng bạn đã đạt thứ hạng " + user.getRank();
        this.mailidx = user.getMail_idx();
        this.receiver = user.getReceive();
    }
}
