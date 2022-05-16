package maxwainer.college.gui.object.web;

import java.util.Collection;
import java.util.LinkedList;
import org.jetbrains.annotations.NotNull;

// c# class:
//     public int DirectionId { get; set; }
//    public string Name { get; set; } = null!;
//    public int StartStationId { get; set; }
//    public int EndStationId { get; set; }
//
//    public virtual ICollection<Active> Actives { get; set; }
//    public virtual ICollection<Station> Stations { get; set; }
//    public virtual ICollection<Ticket> Tickets { get; set; }
public record Direction(
    int directionId,
    int endStationId,
    int startStationId,
    @NotNull String name,
    @NotNull Collection<Active> actives,
    @NotNull LinkedList<Station> stations,
    @NotNull Collection<Ticket> tickets) {

}
