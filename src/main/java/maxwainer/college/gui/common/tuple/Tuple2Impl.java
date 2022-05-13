package maxwainer.college.gui.common.tuple;

final class Tuple2Impl<K, V> implements Tuple2<K, V> {

  private final K key;
  private final V value;

  Tuple2Impl(final K key, final V value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public K key() {
    return key;
  }

  @Override
  public V value() {
    return value;
  }
}
