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
    int price,
    @NotNull String sitType,
    @NotNull String index,
    @NotNull Carriage relatedCarriage,
    @Nullable Ticket ticket) {

  public boolean taken() {
    return ticket != null;
  }

  public boolean notToken() {
    return !taken();
  }

  public boolean willBeFree() {
    // check is ticket null
    if (ticket == null) return true;

    // get actives
    final var actives = relatedCarriage.relatedTrain()
        .actives();

    final var filtered = actives.stream()
        // check is
        .filter(active -> active.stationId() == ticket.endStationId())
        .findFirst();

    return filtered.isEmpty();
  }
}
