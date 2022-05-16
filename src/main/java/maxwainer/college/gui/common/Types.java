package maxwainer.college.gui.common;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class Types {

  private static final BiMap<Class<?>, Class<?>> PRIMITIVE_TO_BOXED = HashBiMap.create();
  private static final BiMap<Class<?>, Class<?>> BOXED_TO_PRIMITIVE = PRIMITIVE_TO_BOXED.inverse();

  static {
    PRIMITIVE_TO_BOXED.forcePut(int.class, Integer.class);
    PRIMITIVE_TO_BOXED.forcePut(long.class, Long.class);
    PRIMITIVE_TO_BOXED.forcePut(double.class, Double.class);
    PRIMITIVE_TO_BOXED.forcePut(float.class, Float.class);
    PRIMITIVE_TO_BOXED.forcePut(short.class, Short.class);
    PRIMITIVE_TO_BOXED.forcePut(byte.class, Byte.class);
    PRIMITIVE_TO_BOXED.forcePut(boolean.class, Boolean.class);
    PRIMITIVE_TO_BOXED.forcePut(char.class, Character.class);
    PRIMITIVE_TO_BOXED.forcePut(String.class, String.class);
  }

  private Types() {
    MoreExceptions.instantiationError();
  }

  public static boolean isPrimitive(final @NotNull Class<?> clazz) {
    return PRIMITIVE_TO_BOXED.containsKey(clazz) || BOXED_TO_PRIMITIVE.containsKey(clazz);
  }

  public static Class<?> asBoxedPrimitive(final @NotNull Class<?> clazz) {
    return isBoxed(clazz) ? clazz : boxed(clazz);
  }

  public static boolean isBoxed(final @NotNull Class<?> clazz) {
    return BOXED_TO_PRIMITIVE.containsKey(clazz);
  }

  public static Class<?> unboxed(final @NotNull Class<?> clazz) {
    final var result = BOXED_TO_PRIMITIVE.get(clazz);

    return result == null ? clazz : result;
  }

  public static Class<?> boxed(final @NotNull Class<?> clazz) {
    final var result = PRIMITIVE_TO_BOXED.get(clazz);

    return result == null ? clazz : result;
  }
}
