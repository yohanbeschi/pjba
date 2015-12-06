package org.isk.pjba;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PjbaClassLoader extends ClassLoader {
  public static volatile PjbaClassLoader CLASS_LOADER = new PjbaClassLoader();

  private final ConcurrentMap<String, byte[]> rawClasses = new ConcurrentHashMap<>();
  private final ConcurrentMap<String, Class<?>> loaded = new ConcurrentHashMap<String, Class<?>>();

  public void put(final String javaClass, final byte[] bytes) {
    if (null != this.rawClasses.putIfAbsent(javaClass, bytes)) {
      throw new RuntimeException("Illegal attempt to define duplicate class");
    }
  }

  public boolean isLoaded(final String javaClass) {
    return this.loaded.containsKey(javaClass);
  }

  @Override
  protected Class<?> findClass(final String name) throws ClassNotFoundException {
    Class<?> clazz = this.loaded.get(name);
    if (null != clazz) {
      return clazz;
    }

    final byte[] bytes = this.rawClasses.remove(name);
    if (bytes != null) {

      // We don't define PJBA classes in the parent class loader.
      clazz = super.defineClass(name, bytes, 0, bytes.length);

      if (this.loaded.putIfAbsent(name, clazz) != null) {
        throw new RuntimeException("Attempted duplicate class definition for " + name);
      }
      return clazz;
    }
    return super.findClass(name);
  }

  public static void reset() {
    PjbaClassLoader.CLASS_LOADER = new PjbaClassLoader();
    Thread.currentThread().setContextClassLoader(PjbaClassLoader.CLASS_LOADER);
  }
}
