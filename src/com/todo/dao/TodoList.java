package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;

public class TodoList {
	Connection conn;

	public TodoList() {
		this.conn = DbConnect.getConnection(); 
	}
	
	//데이터 이전
	public void importData(String filename,TodoList l) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into DbConnect (priority, level, title, memo, category, current_date, due_date)"
					+ " values (?,?,?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String priority = st.nextToken();
				String level = st.nextToken();
				String title = st.nextToken();
				String category = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				if(l.isDuplicate(title)) {
					
				}
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, priority);
				pstmt.setString(2, level);
				pstmt.setString(3, title);
				pstmt.setString(4, description);
				pstmt.setString(5, category);
				pstmt.setString(6, current_date);
				pstmt.setString(7, due_date);
				
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
			}
			System.out.println(records + "개의 데이터를 불러왔습니다.");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//데이터 추가
	public int addItem(TodoItem t) {
		String sql = "insert into DbConnect (priority, level, title, memo, category, current_date, due_date)"
				+ " values (?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getPriority());
			pstmt.setString(2, t.getLevel());
			pstmt.setString(3, t.getTitle());
			pstmt.setString(4, t.getDesc());
			pstmt.setString(5, t.getCategory());
			pstmt.setString(6, t.getCurrent_date());
			pstmt.setString(7, t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	//데이터 삭제
	public int deleteItem(int[] ids) {
		String sql = "delete from DbConnect where id=?;";
		PreparedStatement pstmt = null;
		int count = ids.length;
		try {
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<ids.length; i++) {
				pstmt.setInt(1, ids[i]);
				count--;
				count = pstmt.executeUpdate();
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	//데이터 수정
	public int editItem(TodoItem t) {
		String sql = "update DbConnect set priority=?, level=?, title=?, memo=?, category=?, current_date=?, due_date=?"
				+ " where id =?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getPriority());
			pstmt.setString(2, t.getLevel());
			pstmt.setString(3, t.getTitle());
			pstmt.setString(4, t.getDesc());
			pstmt.setString(5, t.getCategory());
			pstmt.setString(6, t.getCurrent_date());
			pstmt.setString(7, t.getDue_date());
			pstmt.setInt(8,t.getNum());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int editPriority(TodoItem t) {
		String sql = "update DbConnect set priority=? where id =?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getPriority());
			pstmt.setInt(2,t.getNum());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	//데이터 목록 불러오기
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM DbConnect";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String priority = rs.getString("priority");
				String level = rs.getString("level");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int  is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(priority, level, title, description, category, due_date);
				t.setNum(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//키워드 목록 불러오기
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM DbConnect WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String priority = rs.getString("priority");
				String level = rs.getString("level");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int  is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(priority, level, title, description, category, due_date);
				t.setNum(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//데이터 갯수 계산
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM DbConnect;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	//데이터 중복 검사
	public Boolean isDuplicate(String title) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
			break;
		}
		return false;
	}
	
	//카테고리 불러오기
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM DbConnect";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//키워드와 동일한 카테고리 불러오기
	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM DbConnect WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String priority = rs.getString("priority");
				String level = rs.getString("level");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(priority, level, title, description, category, due_date);
				t.setNum(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//데이터 제목, 날짜별 정렬
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM DbConnect ORDER BY "+orderby;
			if(ordering==0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String priority = rs.getString("priority");
				String level = rs.getString("level");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(priority, level, title, description, category, due_date);
				t.setNum(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	//완료된 목록 체크
	public int completeItem(int[] ids) {
		String sql = "update DbConnect set is_completed=1 where id=?;";
		PreparedStatement pstmt;
		int count = ids.length;
		try {
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<ids.length; i++) {
				pstmt.setInt(1, ids[i]);
				count--;
				count = pstmt.executeUpdate();
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int deleteCompletedItem(int target) {
		String sql = "delete from DbConnect where is_completed=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, target);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<String> getTitles() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT title FROM DbConnect";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String title = rs.getString("title");
				list.add(title);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
