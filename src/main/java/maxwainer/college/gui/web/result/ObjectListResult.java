package maxwainer.college.gui.web.result;

import java.util.List;

public record ObjectListResult<T>(List<T> value) implements Result<List<T>> {

}
