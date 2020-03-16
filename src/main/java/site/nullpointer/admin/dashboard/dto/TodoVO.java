package site.nullpointer.admin.dashboard.dto;

import site.nullpointer.admin.dashboard.entiy.TodoEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 待办任务
 */
public class TodoVO implements Serializable {
    private String id;// id
    /**
     * 内容
     */
    private String text;
    /**
     * 是否完成
     */
    private Boolean done;
    /**
     * 创建时间
     */
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public TodoVO(String id, String text, Boolean done) {
        this.id = id;
        this.text = text;
        this.done = done;
    }
    public TodoVO(TodoEntity entity){
        this.id=entity.getId();
        this.text=entity.getText();
        this.done=entity.getDone();
        this.createDate=entity.getCreateTime();
    }
    public TodoEntity toEntity(){
        TodoEntity entity=new TodoEntity();
        entity.setId(id);
        entity.setDone(done);
        entity.setText(text);
        entity.setCreateTime(createDate);
        return entity;
    }

    public TodoVO(){

    }
}
