package maxwainer.college.gui.object.model;

public record RegisterModel(
    String firstName,
    String secondName,
    String patronymic,
    String username,
    String password,
    int passportId) implements UserTargetedModel {
}
