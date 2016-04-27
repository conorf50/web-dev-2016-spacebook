package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collections;

import controllers.Accounts;

import java.util.ArrayList;
import java.util.List;

import play.db.jpa.Model;
import play.db.jpa.Blob;

@Entity
public class User extends Model
{
  public String firstName;
  public String lastName;
  public String username;

  public String email;
  public String password;
  public String statusText;
  public Blob   profilePicture;
  public Blob   thumbnailPicture;
  public int    age;
  public String nationality;

  @OneToMany(mappedBy = "sourceUser")
  public List<Friendship> friendships = new ArrayList<Friendship>();
  
  @OneToMany(mappedBy = "to")
  public List<Message> inbox = new ArrayList<Message>();
  
  @OneToMany(mappedBy = "from")
  public List<Message> outbox = new ArrayList<Message>();
  
  @OneToMany(mappedBy = "to")
  public List<Comments> comments = new ArrayList<Comments>();
  
 
  @OneToMany
  public List<Post> posts = new ArrayList<Post>();  

 
  public boolean isOnline;
  
  
  public User(String firstName, String lastName,String email, String password, int age, String nationality)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.age = age;
    this.nationality = nationality;
    this.username = firstName;
    isOnline = false;
  }
  
  public static User findByEmail(String email)
  {
    return find("email", email).first();
  }

  public boolean checkPassword(String password)
  {
    return this.password.equals(password);
  } 
  
  public boolean isOnline(){
	  return Accounts.isOnline;
  }
  
  public void befriend(User friend)
  {
    Friendship friendship = new Friendship(this, friend);
    friendships.add(friendship);
    friendship.save();
    save();
  }

  public void unfriend(User friend)
  {
    Friendship thisFriendship = null;
    
    for (Friendship friendship:friendships)
    {
      if (friendship.targetUser== friend)
      {
        thisFriendship = friendship;
      }
    }
    friendships.remove(thisFriendship);
    thisFriendship.delete();
    save();
  }  
  
  public void sendMessage (User to, String messageText, String subject)
  {
    Message message = new Message (this, to, messageText, subject);
    outbox.add(message);
    to.inbox.add(message);
    message.save();
  }

public void sendComment(User to, String commentText) {
	Comments comment = new Comments (this, to, commentText);
	 to.comments.add(comment);
	 comment.save();
	
}

//  public void sendComment (User to, String comment)
//  {
//    Comment comment = new Comment (this, to, comment);
//    outbox.add(message);
//    to.inbox.add(message);
//    message.save();
//  }
  
}