package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.bean.Category;
import models.bean.Publisher;
import models.dao.CategoryDao;
import models.dao.PublisherDao;

public abstract class Controller extends HttpServlet {

	public Controller() {
		super();
	}

	public void init() {
		System.out.println("controller init()");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void message(String message) {
		System.out.println(message);
	}

	protected void setCategoriesAndPublishers(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		CategoryDao categoryDao = new CategoryDao();
		PublisherDao publisherDao = new PublisherDao();
		List<Category> categories = categoryDao.findAll();
		List<Publisher> publishers = publisherDao.findAll();
		request.setAttribute("categories", categories);
		request.setAttribute("publishers", publishers);
	}

}
