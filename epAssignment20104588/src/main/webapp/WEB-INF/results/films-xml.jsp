<%@ page import="java.util.List" %>
<%@ page import="javax.xml.bind.JAXBContext" %>
<%@ page import="javax.xml.bind.JAXBException" %>
<%@ page import="javax.xml.bind.Marshaller" %>
<%@ page import="model.Film" %>
<%@ page import="model.FilmList" %>
<%@ page trimDirectiveWhitespaces="true"%>
<%
System.out.println("DEBUG: XML Conversion in films-xml.jsp");

FilmList films = new FilmList((List<Film>)request.getAttribute("films"));
try
{
    JAXBContext jaxbContext = JAXBContext.newInstance(FilmList.class); 
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    jaxbMarshaller.marshal(films, out); // out is the back to browser
}
catch (JAXBException e)
{
    e.printStackTrace();
}
%>