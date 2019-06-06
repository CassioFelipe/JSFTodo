package com.test1.todo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@ManagedBean(name = "Todo")
@SessionScoped
public class Todos implements Serializable{
	private List<Todo> todosList;
	private Todo selectedTodo;
	private String newName;
	private SessionFactory factory;
	private String validateNewName;
	
	public Todos() {
		newName = "";
		validateNewName = "is-invalid";
		factory = new Configuration().configure().buildSessionFactory();
		todosList = new ArrayList<>();
		createTodos();
		
	}
	
	private void createTodos() {
		//todosList.add(new Todo("Nome 1", "Desc 1"));
		//todosList.add(new Todo("Nome 2", "Desc 2"));
		
		Session session = factory.openSession();
		Transaction tx;
		
		try {
			tx = session.beginTransaction();
			long count = (long) session.createQuery("select count(*) from Todo").uniqueResult();
			if(count > 0) {
			 todosList = (ArrayList) session.createQuery("from Todo").list();
			 tx.commit();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	
	public void select() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String entry = (String)params.get("name");
		
		System.out.println(entry);
		
		for (Todo a : todosList) {
			if(a.getName().equals(entry)) {
				System.out.println("Achou");
				selectedTodo = a;
				return;
			}
		}
	}
	
	public void newNameListener(ValueChangeEvent e) {
		String nome = e.getNewValue().toString();
		if(nome.length() <4 || getByName(nome) != null) {
			validateNewName = "is-invalid";
		}else {
			validateNewName = "is-valid";
		}
	}
	
	public void newTodo(ActionEvent e) {
		if (getNewName().length() < 4) {
			System.out.println("Deve conter pelo menos 4 caracteres");
			return;
		}
		
		if (getByName(getNewName()) != null) {
			System.out.println("Ja existe");
			return;
		}
		
		Todo t = new Todo(getNewName(), "");
		todosList.add(t);
		todosList.sort((o1,o2) -> o1.getName().compareTo(o2.getName()));
		hPersistFromObject(t);
	}
	
	
	public void deleteTodo(ActionEvent e) {
		String name = (String) e.getComponent().getAttributes().get("name");
		
		Todo t = getByName(name);
		if (t == null) {
			System.out.println("Nao encontrado");
			return;
		}
		
		if (selectedTodo == t) {
			selectedTodo = null;
		}
		
		todosList.remove(t);
		
		hDeleteFromObject(t);
	}
	
	private Todo getByName(String name) {
		for (Todo i : todosList) {
			if (i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}
	
	public void inputChange(ValueChangeEvent e) {
		String name = e.getComponent().getAttributes().get("name").toString();
		Todo t = getByName(name);
		t.setDesc(e.getNewValue().toString());
		System.out.println("Mudou " + t.getDesc());
		
		hUpdateFromObject(t);
		
	}

	public void hUpdateFromObject(Todo t) {
		Session session = factory.openSession();
		
		try {
			Transaction tx = session.beginTransaction();
			Todo t2 = (Todo) session.get(Todo.class, t.getName());
			if ( t2 != null) {
				t2.setDesc(t.getDesc());
				session.update(t2);
				tx.commit();
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void hPersistFromObject(Todo t) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.persist(t);
			tx.commit();
			
		} catch (HibernateException ex) {
			if( tx != null ) tx.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void hDeleteFromObject(Todo t) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			Todo t2 = (Todo) session.get(Todo.class, t.getName());
			if (t2 == null) {
				return;
			}
			session.delete(t2);
			tx.commit();
		}catch(HibernateException ex) {
			ex.printStackTrace();
		}finally {
			session.close();
		}
	}
	
	//////////////Getters and Setters/////////////////
	public List<Todo> getTodosList() {
		return todosList;
	}

	public void setTodosList(List<Todo> todosList) {
		this.todosList = todosList;
	}

	public Todo getSelectedTodo() {
		return selectedTodo;
	}

	public void setSelectedTodo(Todo selectedTodo) {
		this.selectedTodo = selectedTodo;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getValidateNewName() {
		return validateNewName;
	}
	
	
	
	//////////Getters and Setters///////////
	
	
}
