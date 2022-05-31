package model;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class FilmDAO {
	
	Film oneFilm = null;
	Connection conn = null;
    Statement stmt = null;
	String user = "craiga";
    String password = "wolumbaT7";

    //String url = "jdbc:google:mysql://ep-assignment-20104588:europe-west2:ep-assignment20104588/films?useSSL=false";
    String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/"+user;
	
    public FilmDAO() {}

	private void openConnection(){
		
		System.out.println("DEBUG* In openConnection()");
		// loading jdbc driver for mysql
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(Exception e) { System.out.println(e); }

		// connecting to database
		try{
			// connection string for demos database, username demos, password demos
 			conn = DriverManager.getConnection(url, user, password);
		    stmt = conn.createStatement();
		} catch(SQLException se) { System.out.println(se); }	   
    }
	
	private void closeConnection(){
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Film getNextFilm(ResultSet rs){
		
    	Film thisFilm=null;
		
    	try {
			thisFilm = new Film(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getInt("year"),
				rs.getString("director"),
				rs.getString("stars"),
				rs.getString("review"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return thisFilm;		
	}
	
	/**
	  * Gets all films in database.
	  * <p>
	  * @return ArrayList<Film>  an array list of all films and film values in database
	  * <p>
	  * @author Alasdair Craig
	  */
	public ArrayList<Film> getAllFilms(){
		
		ArrayList<Film> allFilms = new ArrayList<Film>();
	    openConnection();
	    
	    // Create select statement and execute it
	    try{
	    	String selectSQL = "SELECT * FROM films";
		    
		    //optional debug message 
		    System.out.println("DEBUG** SQL Query: " + selectSQL);
		    
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		   
		    // Retrieve the results
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    	
			    //optional debug message
			    //System.out.println("DEBUG** Next film result is: " + oneFilm.toString());
			    allFilms.add(oneFilm);
		    }
		   
		    stmt.close();
		    closeConnection();
		}catch(SQLException se){ 
			 System.out.println(se); 
		}
	    return allFilms;
    }
	
	public ArrayList<Film> getFilm(String searchFilm){
		
		ArrayList<Film> foundFilms = new ArrayList<Film>();
	    openConnection();
	    
	    // Create select statement and execute it
	    try{
	    	String selectSQL = "SELECT * FROM films WHERE title LIKE '%" + searchFilm + "%';";
		    
		    //optional debug message 
		    //System.out.println("DEBUG** SQL Query: " + selectSQL);
		    
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		   
		    // Retrieve the results
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    	
			    //optional debug message
			    //System.out.println("DEBUG** Next film result is: " + oneFilm.toString());
			    foundFilms.add(oneFilm);
		    }
		   
		    stmt.close();
		    closeConnection();
		}catch(SQLException se){ 
			 System.out.println(se); 
		}
	    return foundFilms;
    }

   public Film getFilmByID(int id){
	   
		openConnection();
		oneFilm=null;
	    // Create select statement and execute it
		try{
		    String selectSQL = "SELECT * FROM films WHERE id="+id;
		    
		    //optional debug message 
		    System.out.println("DEBUG** SQL Query: " + selectSQL);
		    
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
	    	// Retrieve the results
		    while(rs1.next()){
		    	oneFilm = getNextFilm(rs1);
		    	
		    	//optional debug message
		    	//System.out.println("DEBUG** Film result is: " + oneFilm.toString());
		    }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }

	   return oneFilm;
   } 
   
   public Film createFilm(){
	    int id;
	    String title;
	    int year;
	    String director;
	    String stars;
	    String review;

	    Scanner in = new Scanner(System.in);
	    
	    System.out.println("Please enter ID");
	    id = Integer.parseInt(in.nextLine());
	    
	    System.out.println("Please enter a Title");
	    title = in.nextLine();
	    
	    System.out.println("Please enter a Year");
	    year = Integer.parseInt(in.nextLine());
	       
	    System.out.println("Please enter a Director");
	    director = in.nextLine();
	   
	    System.out.println("Please enter any Stars");
	    stars = in.nextLine();
	    
	    System.out.println("Please enter any Review");
	    review = in.nextLine();
	    
	    return new Film(id, title, year, director, stars, review);

	  }
   
   public int insertFilm(Film in) {
	   
	   openConnection();
	   int result = 0;
	   	   
	   try{
		   String selectSQL = "INSERT INTO films (id,title,year,director,stars,review) VALUES "
			+ "("+in.getId()+",'"+ in.getTitle()+"',"+in.getYear()+",'"+in.getDirector()+"','"+in.getStars()+"','"+in.getReview()+ "');";
		   
		 	//optional debug message 
		    System.out.println("DEBUG** SQL Query: " + selectSQL);
		   
		    result = stmt.executeUpdate(selectSQL);
		    
		    //optional debug message 
		    System.out.println("DEBUG** Result: " + result);
		    
		    stmt.close();
		    closeConnection();
	   } catch(SQLException se) { System.out.println(se); }
	   
		return result;
   } 
   
   public int deleteFilm(Film f) {
	   
	   openConnection();
	   int result = 0;
	   
	   try{
		   String selectSQL = "DELETE FROM films WHERE id= " + f.getId() + ";";
		   
		 	//optional debug message 
		    System.out.println("DEBUG** SQL Query: " + selectSQL);
		   
		    result = stmt.executeUpdate(selectSQL);
		    
		    //optional debug message 
		    System.out.println("DEBUG** Result: " + result);
		    
		    stmt.close();
		    closeConnection();
	   } catch(SQLException se) { System.out.println(se); }
	   
		return result;
   }
   
   public int updateFilm(Film up) {
	   
	   openConnection();
	   int result = 0;
	   	   
	   try{
		   String selectSQL = "UPDATE films " + "SET id="+up.getId()+","+"title='"+up.getTitle()+"',"+"Year="+up.getYear()+",director='"+up.getDirector()
		   +"',stars='"+up.getStars()+"',review='"+up.getReview()+"' WHERE id="+up.getId()+";";
		   
		 	//optional debug message 
		    System.out.println("DEBUG** SQL Query: " + selectSQL);
		   
		    result = stmt.executeUpdate(selectSQL);
		    
		    //optional debug message 
		    System.out.println("DEBUG** Result: " + result);
		    
		    stmt.close();
		    closeConnection();
	   } catch(SQLException se) { System.out.println(se); }
	   
		return result;
   } 
}
