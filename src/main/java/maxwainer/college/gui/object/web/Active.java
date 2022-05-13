package maxwainer.college.gui.object.web;

import java.time.LocalDateTime;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

// c# class:
//     public partial class Active
//    {
//        public Active()
//        {
//            Tickets = new HashSet<Ticket>();
//        }
//
//        public int ActiveId { get; set; }
//        public int StationId { get; set; }
//        public DateTime StartDateTime { get; set; }
//        public int MainDirectionId { get; set; }
//        public int TrainId { get; set; }
//        public DateTime MainStartDateTime { get; set; }
//
//        public virtual Direction MainDirection { get; set; } = null!;
//        public virtual Station Station { get; set; } = null!;
//        public virtual Train Train { get; set; } = null!;
//        public virtual ICollection<Ticket> Tickets { get; set; }
//    }
public record Active(
    int activeId,
    int stationId,
    int trainId,
    @NotNull Direction mainDirection,
    @NotNull Train train,
    @NotNull Collection<Ticket> tickets,
    @NotNull LocalDateTime mainStartDateTime,
    @NotNull LocalDateTime startDateTime) {

}
