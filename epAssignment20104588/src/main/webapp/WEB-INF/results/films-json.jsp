  <%@ page import="java.util.List" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="model.Film" %>
<%@ page trimDirectiveWhitespaces="true"%>
<%
System.out.println("DEBUG: JSON Conversion in films-json.jsp");
List<Film> films = (List<Film>) request.getAttribute("films");
Gson gson = new Gson();
String jsonInString = gson.toJson(films);
response.getWriter().println(jsonInString);
%>