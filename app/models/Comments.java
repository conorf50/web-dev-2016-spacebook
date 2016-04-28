package models;

import javax.persistence.*;
import play.db.jpa.*;

@Entity
public class Comments extends Model
{
  public String comment;


  @ManyToOne
  public User from;

  @ManyToOne
  public User to;

  public Comments(User from, User to, String comment)
  {
    this.from = from;
    this.to = to;
    this.comment = comment;
  }
}