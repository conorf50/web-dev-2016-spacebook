package controllers;

import java.util.*;

import models.Message;
import models.Post;
import models.User;
import models.Comments;

import play.Logger;
import play.mvc.Controller;

public class Blog  extends Controller
{
  public static void index()
  {
    User user = Accounts.getLoggedInUser();
    render(user);
  }
  
  public static void newPost(String title, String content)
  {
    User user = Accounts.getLoggedInUser();
    
    Post post = new Post (title, content);
    post.save();
    user.posts.add(post);
    user.save();
    
    Logger.info ("title:" + title + " content:" + content);
    index();
  }
  
  public static void deletePost(Long postid)
  {    
    User user = Accounts.getLoggedInUser(); 

    Post post = Post.findById(postid);
    user.posts.remove(post);

    user.save();
    post.delete();

    index();
  }
  
  public static void visit(Long id)
  {
    User currentUser = Accounts.getLoggedInUser();
    
    User user = User.findById(id);
    Logger.info("Just visiting the blog for " + user.firstName + ' ' + user.lastName);
    render(user);
  }
  
  public static void sendComment(Long id, String commentText)
  {
    User fromUser = Accounts.getLoggedInUser();;
    User toUser = User.findById(id);
    
    Logger.info("Comment from user " + 
        fromUser.firstName + ' ' + fromUser.lastName +" to " +
        toUser.firstName + ' ' + toUser.lastName +": " +
        commentText);    
    
    fromUser.sendComment(toUser, commentText);
    render();
  }  
}