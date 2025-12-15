package web.example.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class User {
    private int seq;
    private String id;
    private String name;
    private String email;
    private String hp;
    private String addr1;
    private String addr2;
    private String etc;
    private String regDt;
    private String modDt;
}
