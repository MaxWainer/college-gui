package maxwainer.college.gui.object.web;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    int relatedCarriageId,
    @NotNull String index,
    @NotNull Carriage relatedCarriage,
    @Nullable Ticket ticket) {

}
