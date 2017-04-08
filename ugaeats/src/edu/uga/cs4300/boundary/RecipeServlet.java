package edu.uga.cs4300.boundary;

import java.io.IOException;
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
import edu.uga.cs4300.persistlayer.DbAccessImpl;

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
			loginUser.validateLogin(username, password);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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
