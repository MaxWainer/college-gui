package maxwainer.college.gui.common.tuple;

final class Tuple3Impl<F, S, T> implements Tuple3<F, S, T> {

  private final F first;
  private   final S second;
  private final T third;

  Tuple3Impl(final F first, final S second, final T third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }

  @Override
  public F first() {
    return first;
  }

  @Override
  public S second() {
    return second;
  }

  @Override
  public T third() {
    return third;
  }
}
