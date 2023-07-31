package app.helpers;

import app.models.ErrorMessage;
import lombok.var;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseHelper {

    public static void ReturnErrorStatus(HttpServletResponse response, int httpStatus, String errorMessage) throws IOException {
        var     error = new ErrorMessage(errorMessage);
        response.setContentType("text/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(httpStatus);
        PrintWriter out = response.getWriter();
        out.println(JsonHelper.ToJson(error));
    }

    public static <T> void ReturnOk(HttpServletResponse response, T valueResponse) throws IOException {
        response.setContentType("text/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(200);
        PrintWriter out = response.getWriter();
        var resp = JsonHelper.ToJson(valueResponse);
        out.println(resp);
    }

    public static void ReturnOk(HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(200);
        PrintWriter out = response.getWriter();
        out.println("");
    }
}
