package maxwainer.college.gui.web.result;


public record EnumResult<T extends Enum<T>>(T value) implements Result<T> {
}
