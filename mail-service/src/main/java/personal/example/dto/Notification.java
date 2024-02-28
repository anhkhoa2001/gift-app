package personal.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private String title;
    private String message;
    private int bonus;
    private String mailidx;
    private String receiver;


}
