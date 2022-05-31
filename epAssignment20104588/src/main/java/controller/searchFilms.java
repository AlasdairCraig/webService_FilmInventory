package controller;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import model.Film;
import model.FilmDAO;

import java.util.ArrayList;

@WebServlet("/searchFilms")
public class searchFilms extends HttpServlet {
private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchFilms() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
  public void doGet(HttpServletRequest request,HttpServletResponse response)
		  throws ServletException, IOException {
	  response.setHeader("Cache-Control", "no-cache");
	  response.setHeader("Pragma", "no-cache");
	  
	  String format = request.getParameter("format");	
	  String searchWord = request.getParameter("searchWord");
	  
	  FilmDAO films = new FilmDAO();
	  ArrayList<Film> filmList = films.getFilm(searchWord);
	  
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
	  
		 //optional DEBUG to console
	  //for (int i = 0; i < filmList.size(); i++){
		  //System.out.println(filmList.get(i).toString());
	  //}
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
		  throws ServletException, IOException {
	  doGet(request, response);
  }
}
