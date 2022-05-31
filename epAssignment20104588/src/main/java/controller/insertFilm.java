package controller;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import model.Film;
import model.FilmDAO;

import java.util.ArrayList;

@WebServlet("/insertFilm")
public class insertFilm extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request,HttpServletResponse response)
		  throws ServletException, IOException {
	  response.setHeader("Cache-Control", "no-cache");
	  response.setHeader("Pragma", "no-cache");
	  
	  String format = request.getParameter("format");
	  
	  int id = Integer.parseInt(request.getParameter("id"));
	  String title = request.getParameter("title");
	  int year = Integer.parseInt(request.getParameter("year"));
	  String director = request.getParameter("director");
	  String stars = request.getParameter("stars");
	  String review = request.getParameter("review");
	  
	  Film f = new Film(id,title,year,director,stars,review);
	  FilmDAO films = new FilmDAO();
	  
	  films.insertFilm(f);
	  ArrayList<Film> filmList = new ArrayList<Film>();
	  filmList.add(f);
	 
	  request.setAttribute("films", filmList);
	  String outputPage;
	  if ("xml".equals(format)) {
	      response.setContentType("text/xml");
	      outputPage = "/WEB-INF/results/films-xml.jsp";
	    } else if ("json".equals(format)) {
	      response.setContentType("application/json");
	      outputPage = "/WEB-INF/results/films-json.jsp";
	    } else {
	      response.setContentType("text/plain");
	      outputPage = "/WEB-INF/results/films-string.jsp";
	    }
	  RequestDispatcher dispatcher =
		      request.getRequestDispatcher(outputPage);
		    dispatcher.include(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
		  throws ServletException, IOException {
	  doGet(request, response);
  }
}
