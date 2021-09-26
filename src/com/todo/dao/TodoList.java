package com.todo.dao;

import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByDateReverse;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
		
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
		t.last_num--;
		
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		for (TodoItem item : list) {
			System.out.println(item.getNum()+". [" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDateasc() {
		Collections.sort(list, new TodoSortByDate());
	}
	
	public void sortByDatedesc() {
		Collections.sort(list, new TodoSortByDateReverse());
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}

}
