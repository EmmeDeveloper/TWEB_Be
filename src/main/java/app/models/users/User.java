package app.models.users;


import app.commons.Constants;
import lombok.Data;
import lombok.NonNull;

@Data
public class User {

  @NonNull
  private String Id;
  @NonNull
  private String Role;
  private String Email = "";
  private String Account = "";
  private String Name = ""; 
  private String Surname = "";
  private String Address = "";
  private String BirthDate = "";
  private String Phone = "";
  private String MemberSince = "";

  public boolean IsAdmin() {
    return Role.equalsIgnoreCase(Constants.Roles.ADMIN);
  }
}
