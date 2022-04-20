package maxwainer.college.gui.object.web;

import java.time.LocalDateTime;
import org.jetbrains.annotations.NotNull;

// c# class:
//     public partial class Ticket
//    {
//        public int TicketId { get; set; }
//        public int RelatedDirectionId { get; set; }
//        public int RelatedActiveId { get; set; }
//        public DateTime StartDate { get; set; }
//        public int PassportId { get; set; }
//        public int EndStationId { get; set; }
//        public int SittingId { get; set; }
//
//        public virtual Station EndStation { get; set; } = null!;
//        public virtual User Passport { get; set; } = null!;
//        public virtual Active RelatedActive { get; set; } = null!;
//        public virtual Direction RelatedDirection { get; set; } = null!;
//        public virtual Sitting Sitting { get; set; } = null!;
//    }
public record Ticket(
    int ticketId,
    @NotNull LocalDateTime startDate,
    @NotNull Station endStation,
    @NotNull User passport,
    @NotNull Active relatedActive,
    @NotNull Sitting sitting,
    @NotNull Direction relatedDirection) {

}
