package com.test1.todo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "Todo")
@Table(name = "Todo", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
public class Todo {
	@Id
	@Column(name = "NAME", nullable = false, unique = true, length = 20)
	private String name;
	
	@Column(name = "DESCR", nullable = true, unique = false, length = 255)
	private String desc;
	
	///////////////////////
	public Todo() {
		// TODO Auto-generated constructor stub
	}
	
	public Todo(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
	
	@Override
	public
	String toString() {
		return this.getName();
	}
	
	///////////////////////
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
