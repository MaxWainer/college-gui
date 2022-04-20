package maxwainer.college.gui.object.web;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

// c# class:
//     public partial class Sitting
//    {
//        public Sitting()
//        {
//            Tickets = new HashSet<Ticket>();
//        }
//
//        public int SitId { get; set; }
//        public string Index { get; set; } = null!;
//        public int RelatedCarriageId { get; set; }
//
//        public virtual Carriage RelatedCarriage { get; set; } = null!;
//        public virtual ICollection<Ticket> Tickets { get; set; }
//    }
public record Sitting(
    int sitId,
    @NotNull String index,
    @NotNull Carriage relatedCarriage,
    @NotNull Collection<Ticket> tickets) {

}
