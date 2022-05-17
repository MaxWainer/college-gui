package maxwainer.college.gui.object.web;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

// c# class:
//     public partial class User
//    {
//        public User()
//        {
//            Tickets = new HashSet<Ticket>();
//        }
//
//        public int PassportId { get; set; }
//        public string FirstName { get; set; } = null!;
//        public string SecondName { get; set; } = null!;
//        public string Patronymic { get; set; } = null!;
//
//        public virtual ICollection<Ticket> Tickets { get; set; }
//    }
public record User(
    int passportId,
    @NotNull String firstName,
    @NotNull String secondName,
    @NotNull String patronymic,
    @NotNull String password,
    @NotNull String username,
    @NotNull String role,
    @NotNull Collection<Ticket> tickets) {

  public @NotNull User updateRole(final @NotNull String newRole) {
    return new User(passportId, firstName, secondName, patronymic, password, username, newRole,
        tickets);
  }

  private static final String ADMIN_UNIQUE_ID = "admin";

  public boolean admin() {
    return role.equals("Administrator");
  }

  public boolean superUser() {
    return username.equals(ADMIN_UNIQUE_ID) && firstName.equals(ADMIN_UNIQUE_ID)
        && secondName.equals(ADMIN_UNIQUE_ID) && patronymic.equals(ADMIN_UNIQUE_ID)
        && admin();
  }

  public boolean moderator() {
    return admin() || role.equals("Moderator");
  }

}
