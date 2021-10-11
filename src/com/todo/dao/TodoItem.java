package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String category;
    private String desc;
    private String due_date;
    private String current_date;
    public int is_completed;
	static int last_num = 0;
    public int num;
    
    
    public TodoItem(String title, String desc, String category, String due_date){
        this.title = title;
        this.category = category;
        this.desc = desc;
        this.due_date = due_date;
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.current_date = transFormat.format(new Date());
        last_num++;
        num = last_num;
        
    }
    
    public TodoItem(String title, String desc, String category, String due_date, String current_date){
        this.title = title;
        this.category = category;
        this.desc = desc;
        this.due_date = due_date;
        this.current_date = current_date;
        
        last_num++;
        num = last_num;
        
    }
    
    
    
    public int getNum() {
    	return num;
    }
    
    public void setNum(int num) {
    	this.num = num;
    }
    
    public int getLastNum() {
    	return last_num;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getDue_date() {
        return due_date;
    }
    
    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
        
    }
    
    public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}
    
    public String toString() {
    	if(is_completed == 0) {
    		return num + ". [" + category + "] " + title + " - " + desc + " - " + due_date + " - " + current_date;
    	} return num + ". [" + category + "] " + title + "[V] - " + desc + " - " + due_date + " - " + current_date;
    }
    
    public String toSaveString() {
    	return num + "##" + title + "##" + category + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }
}
