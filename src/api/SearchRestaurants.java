package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MongoDBConnection;
import db.MySQLDBConnection;
import model.Restaurant;
import yelp.YelpAPI;

/**
 * Servlet implementation class SearchRestaurants
 */
@WebServlet({ "/SearchRestaurants", "/restaurants" })
public class SearchRestaurants extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final DBConnection connection = new MongoDBConnection();
	private static final DBConnection connection = new MySQLDBConnection();



	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchRestaurants() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// allow access only if session exists
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.setStatus(403);
			return;
		}
		
		
		JSONArray array = new JSONArray(); //json object list
		

		//try {
			if (request.getParameterMap().containsKey("user_id") && request.getParameterMap().containsKey("lat")
					&& request.getParameterMap().containsKey("lon")) {
				String userId = request.getParameter("user_id");
				double lat = Double.parseDouble(request.getParameter("lat"));
				double lon = Double.parseDouble(request.getParameter("lon"));
				// return some fake restaurants
				//array.put(new JSONObject().put("name", "Panda Express"));
				//array.put(new JSONObject().put("name", "Hong Kong Express"));
				array = connection.searchRestaurants(userId, lat, lon);
			}
		//} catch (JSONException e) {
		//	e.printStackTrace();
		//}
		RpcParser.writeOutput(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

}
