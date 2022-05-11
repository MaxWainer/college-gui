package maxwainer.college.gui.object.web;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

// c# class:
//     public partial class Train
//    {
//        public Train()
//        {
//            Actives = new HashSet<Active>();
//            Carriages = new HashSet<Carriage>();
//        }
//
//        public int TrainId { get; set; }
//        public string Name { get; set; } = null!;
//
//        public virtual ICollection<Active> Actives { get; set; }
//        public virtual ICollection<Carriage> Carriages { get; set; }
//    }
public record Train(
    int trainId,
    @NotNull String name,
    @NotNull Active actives,
    @NotNull Collection<Carriage> carriages) {

}
