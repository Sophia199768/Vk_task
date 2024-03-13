package org.itmo.vk_task.pojo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "user_token")
    private String userToken;
    private String date;
    @Column(name = "status_code")
    private String statusCode;
}
