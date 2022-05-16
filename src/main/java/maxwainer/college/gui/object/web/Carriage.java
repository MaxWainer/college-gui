package maxwainer.college.gui.object.web;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

// c# class:
//     public partial class Carriage
//    {
//        public Carriage()
//        {
//            Sittings = new HashSet<Sitting>();
//        }
//
//        public int CarriageId { get; set; }
//        public string Index { get; set; } = null!;
//        public int RelatedTrainId { get; set; }
//
//        public virtual Train RelatedTrain { get; set; } = null!;
//        public virtual ICollection<Sitting> Sittings { get; set; }
//    }
public record Carriage(
    int carriageId,
    int relatedTrainId,
    @NotNull String index,
    @NotNull Train relatedTrain,
    @NotNull Collection<Sitting> sittings) {

  public @NotNull Collection<Sitting> sittingsFiltered() {
    return sittings.stream()
        .filter(Sitting::notToken)
        .toList();
  }

}
