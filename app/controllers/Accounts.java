package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Accounts extends Controller
{
	
  public static boolean isOnline  = false;
  public static void signup()
  {
	isOnline = true;  
    render();
  }

  public static void login()
  {
	isOnline = true;
    render();
  }

  public static void logout()
  {
	isOnline =false;
    session.clear();
    index();
  }

  public static void index()
  {
    render();
  }

  public static User getLoggedInUser()
  {
    User user = null;
    User username = null;
    if (session.contains("logged_in_userid"))
    {
      String userId = session.get("logged_in_userid");
      user = User.findById(Long.parseLong(userId));

    }
    else
    {
      login();
    }
    return user;
  }
  
  
  public static User viewBlog()
  {



render();
return null;  }
  
  public static void register(String firstName, String lastName, String username ,int age, String nationality, String email, String password)
  {
    Logger.info(firstName + " " + lastName + " " + email + " " + password);
    User user = new User(firstName, lastName, username, email, age, nationality);
    isOnline = true;
    user.save();
    index();
  }

  public static void authenticate(String email, String password)
  {
    Logger.info("Attempting to authenticate with " + email + ":" + password);

    User user = User.findByEmail(email);
    if ((user != null) && (user.checkPassword(password) == true))
    {
      Logger.info("Authentication successful");
      isOnline = true;
      session.put("logged_in_userid", user.id);
      Home.index();
    }
    else
    {
      Logger.info("Authentication failed");
      login();
    }
  }
}