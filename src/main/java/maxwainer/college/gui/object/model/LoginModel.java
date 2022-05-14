package maxwainer.college.gui.object.model;

public record LoginModel(String username, String password, int passportId) implements
    UserTargetedModel, WebModel {

}
