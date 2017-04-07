package edu.uga.cs4300.boundary;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.uga.cs4300.logiclayer.RecipeLogicImpl;

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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String signup = request.getParameter("signup");
		String signin = request.getParameter("signon");
		
		if (signup != null)
		{
			addNewUser(request, response);
		}
		else if (signin != null)
		{
			
		}
	}

	private void addNewUser(HttpServletRequest request, HttpServletResponse response) {
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
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
