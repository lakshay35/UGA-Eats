package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.uga.cs4300.logiclayer.RecipeLogicImpl;
import edu.uga.cs4300.objectlayer.User;
import edu.uga.cs4300.persistlayer.DbAccessImpl;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * Servlet implementation class RecipeServlet
 */
@WebServlet("/RecipeServlet")
public class RecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String templateDir = "/WEB-INF/templates";
	
	private TemplateProcessor process;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecipeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		process = new TemplateProcessor(templateDir, getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String signup = request.getParameter("signup");
		String signin = request.getParameter("signon");
		System.out.println("hey");
		System.out.println(signup);
		if (signup != null)
		{
			System.out.println(signup);
			addNewUser(request, response);
		}
		else if (signin != null)
		{
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			RecipeLogicImpl loginUser = new RecipeLogicImpl();
			User user = loginUser.validateLogin(username, password);
			if(user != null) {
				openHomePage(user.getFirst_name(), request, response);
			} else {
				reloadLoginPage(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void openHomePage(String firstName, HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		String templateName = "homepage.ftl";
		process.processTemplate(templateName, root, request, response);
	}
	
	public void reloadLoginPage(HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		String templateName = "signin.ftl";
		process.processTemplate(templateName, root, request, response);
	}
	
	private void addNewUser(HttpServletRequest request, HttpServletResponse response) {
		String fname = request.getParameter("firstName");
		String lname = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		RecipeLogicImpl userCtrl = new RecipeLogicImpl();
		
		userCtrl.addUser(fname, lname, username, password, email);
		
		try {
			response.sendRedirect("signup.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
