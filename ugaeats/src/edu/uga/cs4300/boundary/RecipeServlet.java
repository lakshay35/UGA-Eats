package edu.uga.cs4300.boundary;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import edu.uga.cs4300.logiclayer.RecipeLogicImpl;
import edu.uga.cs4300.objectlayer.Recipe;
import edu.uga.cs4300.objectlayer.User;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;
import sun.misc.BASE64Encoder;

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
		//String createNewRecipe = request.getParameter("createrecipe");
		String createRecipe = request.getParameter("createButton");
		String loadPopularRecipes = request.getParameter("loadPopular");
		String viewRecipe = request.getParameter("loadRecipe");
		
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
		else if (createRecipe != null)
		{
			showRecipeMaker(request, response);
		}
		else if (ServletFileUpload.isMultipartContent(request))
		{
			createNewRecipe(request, response);
		}
		else if (loadPopularRecipes != null)
		{
			getPopularRecipes(request, response);
			/*RecipeLogicImpl temp = new RecipeLogicImpl();
			List<Blob> temp2 = new ArrayList<Blob>();
			temp2 = temp.getImages();
			System.out.println(temp2.size());
			Blob blob = temp2.get(0);
			int blobLength;
			byte[] blobArray;
			String image = null;
			try {
				blobLength = (int) blob.length();
				blobArray = blob.getBytes(1, blobLength);
				BASE64Encoder encoder = new BASE64Encoder();
				image = encoder.encode(blobArray);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Gson g = new Gson();
			System.out.println(g.toJson(image));
			response.setContentType("text/plain");
			try {
				response.getWriter().write(image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		else if (viewRecipe != null)
		{
			System.out.println(viewRecipe);
			viewSelectedRecipe(request, response);
		}
	}

	private void viewSelectedRecipe(HttpServletRequest request, HttpServletResponse response) {
		String tempId = request.getParameter("id");
		int id = Integer.parseInt(tempId);
		RecipeLogicImpl userCtrl = new RecipeLogicImpl();
		Recipe recipe = userCtrl.getRecipeById(id);
		Gson g = new Gson();
		System.out.println(g.toJson(recipe));
		response.setContentType("application/json");
		try {
			response.getWriter().write(g.toJson(recipe));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getPopularRecipes(HttpServletRequest request, HttpServletResponse response) {
		RecipeLogicImpl userCtrl = new RecipeLogicImpl();
		List<Recipe> recipes = new ArrayList<Recipe>();
		recipes = userCtrl.getImages();
		
		Gson g = new Gson();
		System.out.println(g.toJson(recipes));
		response.setContentType("application/json");
		try {
			response.getWriter().write(g.toJson(recipes));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showRecipeMaker(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			SimpleHash root = new SimpleHash(df.build());
			String templateName = "createrecipe.ftl";
			process.processTemplate(templateName, root, request, response);
		}
		else
		{
			try {
				response.sendRedirect("signin.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void createNewRecipe(HttpServletRequest request, HttpServletResponse response) {
		RecipeLogicImpl userCtrl = new RecipeLogicImpl();
		InputStream fileContent = null;
		String recipeName = null;
		String permission = null;
		List<String> ingredients = new ArrayList<String>();
		List<String> instructions = new ArrayList<String>();
		try {
			List<FileItem> formItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem formItem : formItems)
			{
				if (formItem.isFormField())
				{
					String name = formItem.getFieldName();
					
					if (name.equals("recipename"))
					{
						recipeName = formItem.getString();
						System.out.println("recipeName");
					}
					else if (name.equals("ingredients"))
					{
						ingredients.add(formItem.getString());
					}
					else if (name.equals("steps"))
					{
						instructions.add(formItem.getString());
					}
					else if (name.equals("visibility"))
					{
						permission = formItem.getString();
					}
				}
				else
				{
					fileContent = formItem.getInputStream();
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		int user_id = (Integer)(session.getAttribute("id"));
		int check = userCtrl.createRecipe(recipeName, ingredients, instructions, permission, user_id, fileContent);
		if (check != 0)
		{
			
		}
	}

	private void viewRecipes(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			System.out.println("no session and view recipes works");
			System.out.println("github branch");
			DefaultObjectWrapperBuilder df = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
			SimpleHash root = new SimpleHash(df.build());
			String templateName = "viewrecipe.ftl";
			root.put("checklogin", 0);
			root.put("fname", "Not Logged In");
			process.processTemplate(templateName, root, request, response);
		}
		else
		{
			HttpSession session2 = request.getSession();
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
			HttpSession session2 = request.getSession();
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

}

