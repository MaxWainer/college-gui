package maxwainer.college.gui.object.model;

import org.jetbrains.annotations.NotNull;

public sealed interface UserTargetedModel permits LoginModel, RegisterModel {

  @NotNull String username();

  @NotNull String password();

  int passportId();

}
