package maxwainer.college.gui.object.model;

import org.jetbrains.annotations.NotNull;

public interface UserTargetedModel {

  @NotNull String username();

  @NotNull String password();

  int passportId();

}
