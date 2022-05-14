package maxwainer.college.gui.object.model;

// public class OrderModel
//{
//    [Required] public int TrainId { get; set; }
//    [Required] public int CarriageId { get; set; }
//    [Required] public int ActiveId { get; set; }
//    [Required] public int PassportId { get; set; }
//    [Required] public int SittingId { get; set; }
//    [Required] public int EndStationId { get; set; }
//}
public record OrderTicketModel(int trainId, int carriageId, int activeId, int passportId,
                               int sittingId, int endStationId) implements WebModel {

}
