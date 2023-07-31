package app.helpers;

import app.models.ErrorMessage;
import lombok.var;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseHelper {

  public static void ReturnErrorStatus(HttpServletResponse response, int httpStatus, String errorMessage)
      throws IOException {
    var error = new ErrorMessage(errorMessage);
    response.setContentType("text/json");
    enableCors(response);
    response.setStatus(httpStatus);
    PrintWriter out = response.getWriter();
    out.println(JsonHelper.ToJson(error));
  }

  public static <T> void ReturnOk(HttpServletResponse response, T valueResponse) throws IOException {
    response.setContentType("text/json");
    enableCors(response);
    response.setStatus(200);
    PrintWriter out = response.getWriter();
    var resp = JsonHelper.ToJson(valueResponse);
    out.println(resp);
  }

  public static void ReturnOk(HttpServletResponse response) throws IOException {
    response.setContentType("text/json");
    enableCors(response);
    response.setStatus(200);
    PrintWriter out = response.getWriter();
    out.println("");
  }

  private static void enableCors(HttpServletResponse response) {
    response.addHeader("Access-Control-Allow-Origin", "http://localhost:5173");
    response.addHeader("Access-Control-Allow-Headers", "Authorization");
    response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
    response.addHeader("Access-Control-Allow-Credentials", "true");
  }
}

