<%@ page import="java.util.List" %>
<%@ page import="model.Film" %>
<%@ page trimDirectiveWhitespaces="true"%>
<%
System.out.println("DEBUG: String Conversion in films-string.jsp");
List<Film> films = (List<Film>) request.getAttribute("films");
String filmsStr = new String();
for (int i=0; i<films.size(); i++){
	Film f = films.get(i);
	filmsStr = filmsStr + f.getId() + "#" + f.getTitle() + "#" + f.getYear() + "#" + f.getDirector() + "#" + f.getStars() 
	+ "#" + f.getReview() + "$"; 
}
response.getWriter().println(filmsStr);
%>