package web.example.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
 
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AttachVO {
    private int boardid;
    private int attachid;
    private String fname;
    private long fsize;
    
    public AttachVO(String file)
    {   String[] token = file.split(",");
        this.attachid = Integer.parseInt(token[0]);
        this.fname = token[1];
        this.fsize = Long.parseLong(token[2]);
    }
}
