package controller;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Film;
import model.FilmDAO;

class Main {
  public static void main(String[] args) throws SQLException {

    Scanner in = new Scanner(System.in);
    String selection;
 
    FilmDAO films = new FilmDAO();
       
    do{
      System.out.println("------------------");
      System.out.println("Film Inventory");
      System.out.println("Choose from these options");
      System.out.println("------------------");

      System.out.println("[1] Retrieve all films");
      System.out.println("[2] Search for film");
      System.out.println("[3] Insert new film into database");
      System.out.println("[4] Update film in database");
      System.out.println("[5] Delete film from database");
      System.out.println("[6] Exit");

      System.out.println("------------------");
      System.out.println();
      System.out.println("Enter choice >");

      selection = in.nextLine();

      switch (selection) {
        case "1":
          System.out.println("List films");
          ArrayList<Film> allFilms = films.getAllFilms();
          for (int i = 0; i < allFilms.size(); i++){
            System.out.println(allFilms.get(i).toString());
          }
          System.out.println();
          break;

          
	      case "2":
	        System.out.println("\n Enter search word(s)");
	        String searchfilm = in.nextLine();
	        ArrayList<Film> foundFilms = films.getFilm(searchfilm);
	        for (int i = 0; i < foundFilms.size(); i++){
	            System.out.println(foundFilms.get(i).toString());
	        }
	        System.out.println();
	        break;
      
        
        /*
        case "2":
          System.out.println("\n Enter film ID");
          int ID = Integer.parseInt(in.nextLine());
          System.out.println(films.getFilmByID(ID));
          System.out.println();
          break;
      */

        case "3":
          System.out.println("Add a new film");
          Film film = films.createFilm();
          films.insertFilm(film);
          System.out.println();
          break;

        case "4":
          System.out.println("Update a film");
          System.out.println("\n Enter Film ID to update");
          int uID = Integer.parseInt(in.nextLine());
          System.out.println(films.getFilmByID(uID));
          Film updatedFilm = updateFilm(films.getFilmByID(uID));
          films.updateFilm(updatedFilm);
          break;

        case "5":
          System.out.println("Delete film");
          System.out.println("\nEnter Film ID to delete");
          int dID = Integer.parseInt(in.nextLine());
          Film f = films.getFilmByID(dID);
          films.deleteFilm(f);
          break;

        case "6":
          System.out.println("Exiting");
          break;
          default:
          System.out.println("Invalid Selection");


      }
    }while (!selection.equals("6"));
  }

  private static Film updateFilm(Film film) {
    // TODO Auto-generated method stub
    String title;
    int year;
    String director;
    String stars;
    String review;

    Scanner in = new Scanner(System.in);
    System.out.println("Updating Film with ID:" + film.getId());
    System.out.println("Please enter Title");
    title = in.nextLine();
    if (title.equals(""))
      title = film.getTitle();

    System.out.println("Please enter Year");
    String strYear = in.nextLine();
    if (strYear.equals(""))
      year = film.getYear();
    else
      year = Integer.parseInt(strYear);

    System.out.println("Please enter Director");
    director = in.nextLine();
    if (director.equals(""))
      director = film.getDirector();

    System.out.println("Please enter Stars");
    stars = in.nextLine();
    if (stars.equals(""))
      stars = film.getStars();

    System.out.println("Please enter Review");
    review = in.nextLine();
    if (review.equals(""))
      review = film.getReview();

    return new Film(film.getId(),title,year,director,stars,review);
  }
}