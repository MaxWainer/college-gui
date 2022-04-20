package maxwainer.college.gui.object.web;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

// c# class:
//     public partial class Direction
//    {
//        public Direction()
//        {
//            Actives = new HashSet<Active>();
//            Stations = new HashSet<Station>();
//            Tickets = new HashSet<Ticket>();
//        }
//
//        public int DirectionId { get; set; }
//        public string Name { get; set; } = null!;
//        public int StartStationId { get; set; }
//        public int EndStationId { get; set; }
//
//        public virtual ICollection<Active> Actives { get; set; }
//        public virtual ICollection<Station> Stations { get; set; }
//        public virtual ICollection<Ticket> Tickets { get; set; }
//    }
public record Direction(
    int directionId,
    @NotNull String name,
    @NotNull Collection<Active> actives,
    @NotNull Collection<Station> stations,
    @NotNull Collection<Ticket> tickets) {

}
