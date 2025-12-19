package web.example.app.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
 
@Data
@ToString
@EqualsAndHashCode(exclude= {"title","author","regDate","contents","hit","attList"})
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
    private int boardid;
    private String title;
    private String author;
    private java.sql.Date regDate;
    private String contents;
    private int hit;
    private List<AttachVO> attList = new ArrayList<>();
 
    public BoardVO(int intValue) {
        this.boardid=intValue;
    }
}
