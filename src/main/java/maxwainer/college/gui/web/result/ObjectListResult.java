package maxwainer.college.gui.web.result;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.jetbrains.annotations.NotNull;

public record ObjectListResult<T>(List<T> value) implements Result<List<T>>, List<T> {

  @Override
  public int size() {
    return value.size();
  }

  @Override
  public boolean isEmpty() {
    return value.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return value.contains(o);
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return value.iterator();
  }

  @NotNull
  @Override
  public Object @NotNull [] toArray() {
    return value.toArray();
  }

  @NotNull
  @Override
  public <T1> T1 @NotNull [] toArray(@NotNull T1 @NotNull [] a) {
    return value.toArray(a);
  }

  @Override
  public boolean add(T t) {
    return value.add(t);
  }

  @Override
  public boolean remove(Object o) {
    return value.remove(o);
  }

  @Override
  public boolean containsAll(@NotNull Collection<?> c) {
    return value.containsAll(c);
  }

  @Override
  public boolean addAll(@NotNull Collection<? extends T> c) {
    return value.addAll(c);
  }

  @Override
  public boolean addAll(int index, @NotNull Collection<? extends T> c) {
    return value.addAll(index, c);
  }

  @Override
  public boolean removeAll(@NotNull Collection<?> c) {
    return value.removeAll(c);
  }

  @Override
  public boolean retainAll(@NotNull Collection<?> c) {
    return value.retainAll(c);
  }

  @Override
  public void clear() {
    value.clear();
  }

  @Override
  public T get(int index) {
    return value.get(index);
  }

  @Override
  public T set(int index, T element) {
    return value.set(index, element);
  }

  @Override
  public void add(int index, T element) {
    value.add(index, element);
  }

  @Override
  public T remove(int index) {
    return value.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return value.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return value.lastIndexOf(o);
  }

  @NotNull
  @Override
  public ListIterator<T> listIterator() {
    return value.listIterator();
  }

  @NotNull
  @Override
  public ListIterator<T> listIterator(int index) {
    return value.listIterator(index);
  }

  @NotNull
  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return value.subList(fromIndex, toIndex);
  }
}
