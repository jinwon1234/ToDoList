package springpr.toyproject.todolist.dto;

import lombok.Data;
@Data
public class SearchToDoCond {

    private String titleCond;
    private String contentCond;
    private String statusCond;
}
