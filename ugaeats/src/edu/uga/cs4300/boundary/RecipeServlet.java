package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import edu.uga.cs4300.logiclayer.RecipeLogicImpl;
import edu.uga.cs4300.objectlayer.User;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;

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
		String signin = request.getParameter("signin");
		String home = request.getParameter("home");
		String viewRecipes = request.getParameter("viewRecipe");
		String createrecipe = request.getParameter("createButton");
		String viewProfile = request.getParameter("viewProfile");
		
		if (signup != null)
		{
			addNewUser(request, response);
		}
		else if (signin != null)
		{
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			RecipeLogicImpl loginUser = new RecipeLogicImpl();
			User user = loginUser.validateLogin(username, password);
			if(user != null) {
				openHomePage(user, request, response);
			} else 
			{
				reloadLoginPage(request, response);
			}
		}
		else if (home != null)
		{
			goToHome(request, response);
		}
		else if (viewRecipes != null)
		{
			viewRecipes(request, response);
		}
		else if(createrecipe != null) {
			createRecipe(request, response);
		}
		else if(viewProfile != null)
		{
			viewProfile(request, response);
		}
	}
	
	private void viewProfile(HttpServletRequest request, HttpServletResponse response)
	{
		
	}

	private void viewRecipes(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			System.out.println("no session and view recipes works");
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			SimpleHash root = new SimpleHash(df.build());
			String templateName = "viewrecipe.ftl";
			root.put("checklogin", 0);
			root.put("fname", "Not Logged In");
			process.processTemplate(templateName, root, request, response);
		}
		else
		{
			HttpSession session2 = request.getSession(false);
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			SimpleHash root = new SimpleHash(df.build());
			String templateName = "viewrecipe.ftl";
			root.put("fname", session2.getAttribute("firstName"));
			root.put("checklogin", 1);
			process.processTemplate(templateName, root, request, response);
		}
	}

	private void goToHome(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			SimpleHash root = new SimpleHash(df.build());
			String templateName = "homepage.ftl";
			root.put("checklogin", 0);
			root.put("fname", "Not Logged In");
			process.processTemplate(templateName, root, request, response);
		}
		else
		{
			HttpSession session2 = request.getSession(false);
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			SimpleHash root = new SimpleHash(df.build());
			String templateName = "homepage.ftl";
			root.put("fname", session2.getAttribute("firstName"));
			root.put("checklogin", 1);
			process.processTemplate(templateName, root, request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void openHomePage(User user, HttpServletRequest request, HttpServletResponse response) {
		DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		SimpleHash root = new SimpleHash(df.build());
		String templateName = "homepage.ftl";
		
		HttpSession session = request.getSession();
		synchronized(session) {
			
			session.setAttribute("id", user.getId());
			session.setAttribute("firstName", user.getFirst_name());
			session.setAttribute("lastName", user.getLast_name());
			session.setAttribute("username", user.getUsername());
			session.setAttribute("email", user.getEmail());
		}
		root.put("fname", session.getAttribute("firstName"));
		root.put("checklogin", 1);
		
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
	
	public void createRecipe(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			System.out.println("no session and create recipes works");
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			SimpleHash root = new SimpleHash(df.build());
			String templateName = "createrecipe.ftl";
			root.put("checklogin", 0);
			root.put("fname", "Not Logged In");
			process.processTemplate(templateName, root, request, response);
		}
		else
		{
			HttpSession session2 = request.getSession(false);
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			SimpleHash root = new SimpleHash(df.build());
			String templateName = "createrecipe.ftl";
			root.put("fname", session2.getAttribute("firstName"));
			root.put("checklogin", 1);
			process.processTemplate(templateName, root, request, response);
		}
	}

		

}
