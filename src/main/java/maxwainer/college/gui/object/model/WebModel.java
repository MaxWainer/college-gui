package maxwainer.college.gui.object.model;

public sealed interface WebModel permits
    UserTargetedModel,
    RegisterModel,
    OrderTicketModel,
    LoginModel,
    ClearCacheModel {

}
