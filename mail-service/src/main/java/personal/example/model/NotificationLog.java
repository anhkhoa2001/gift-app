package personal.example.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import personal.example.dto.Notification;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "notification_log")
public class NotificationLog {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String requestId;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "REQUEST")
    private String request;

    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "PROCESS_TIME")
    private long processTime;

    public NotificationLog(String payload,
                           String response,
                           long processTime,
                           boolean status) {
        this.processTime = processTime;
        this.request = payload;
        this.response = response;
        this.status = status;
        this.createTime = new Date();
    }

    public NotificationLog() {

    }
}
