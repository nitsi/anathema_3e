package net.sf.anathema.hero.template;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.io.InputOutput;

import java.io.IOException;
import java.io.InputStream;

public class GenericTemplateLoader<T> implements TemplateLoader<T> {

  private final Gson gson;
  private final Class<T> aClass;

  public GenericTemplateLoader(Class<T> aClass) {
    this.aClass = aClass;
    GsonBuilder gsonBuilder = new GsonBuilder();
    gson = gsonBuilder.create();

  }

  public T load(InputStream inputStream) {
    try {
      String json = InputOutput.toString(inputStream);
      return gson.fromJson(json, aClass);
    } catch (IOException e) {
      throw new PersistenceException(e);
    }
  }
}