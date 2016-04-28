import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import play.*;
import play.db.jpa.Blob;
import play.jobs.*;
import play.libs.MimeTypes;
import play.test.*;
 
import models.*;
 
@OnApplicationStart
public class Bootstrap extends Job
{
 public void doJob() throws FileNotFoundException
 {
 Fixtures.deleteDatabase();
 Fixtures.loadModels("data.yml");

 String photoName = "public/images/homer.gif";
 Blob blob = new Blob ();
 blob.set(new FileInputStream(photoName), MimeTypes.getContentType(photoName));
 User homer = User.findByEmail("homer@simpson.com");
 homer.profilePicture = blob;
 homer.save();
 }
}