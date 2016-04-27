package models;

import javax.persistence.*;
import play.db.jpa.*;

@Entity
public class Message extends Model
{
  public String messageText;
  public String subject;


  @ManyToOne
  public User from;

  @ManyToOne
  public User to;

  public Message(User from, User to, String messageText, String subject)
  {
    this.from = from;
    this.to = to;
    this.messageText = messageText;
    this.subject = subject;
  }
}