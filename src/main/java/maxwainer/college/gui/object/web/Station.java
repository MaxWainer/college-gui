package maxwainer.college.gui.object.web;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

// c# class:
//     public partial class Station
//    {
//        public Station()
//        {
//            Actives = new HashSet<Active>();
//            Tickets = new HashSet<Ticket>();
//        }
//
//        public int StationId { get; set; }
//        public int RelatedDirection { get; set; }
//        public string Name { get; set; } = null!;
//
//        public virtual Direction RelatedDirectionNavigation { get; set; } = null!;
//        public virtual ICollection<Active> Actives { get; set; }
//        public virtual ICollection<Ticket> Tickets { get; set; }
//    }
public record Station(
    int stationId,
    @NotNull Direction relatedDirectionNavigation,
    @NotNull Collection<Active> actives,
    @NotNull Collection<Ticket> tickets) {

}
