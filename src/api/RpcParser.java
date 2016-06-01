package api;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class RpcParser {
	public static JSONObject parseInput(HttpServletRequest request) {
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			//每次写入磁盘，等对外部的操作都比较慢，远远慢于程序的运行，所以我们先把要写的东西攒起来，然后攒到一定数量后统一操作
			// This is why we use buffer
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			reader.close();
			return new JSONObject(jb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeOutput(HttpServletResponse response, JSONObject obj) {
		try {
			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
			PrintWriter out = response.getWriter();
			out.print(obj);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeOutput(HttpServletResponse response, JSONArray array) {
		try {
			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
			PrintWriter out = response.getWriter();
			out.print(array);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
