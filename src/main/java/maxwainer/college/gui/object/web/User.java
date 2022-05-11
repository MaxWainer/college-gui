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

}
