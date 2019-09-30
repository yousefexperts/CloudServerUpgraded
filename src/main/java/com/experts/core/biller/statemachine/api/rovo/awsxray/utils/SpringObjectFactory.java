package com.experts.core.biller.statemachine.api.rovo.awsxray.utils;

import com.mongodb.DBObject;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.ObjectFactory;
import org.mongodb.morphia.mapping.DefaultCreator;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.Mapper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;


public class SpringObjectFactory implements ObjectFactory
{

  /** If we're not able to create an object, proxy back to the regular factory **/
  private ObjectFactory defaultCreator = new DefaultCreator();

  @Resource
  protected SpringObjectFactory springObjectFactory;

  @Resource
  protected Morphia morphia;

  @Resource
  private List<MorphiaListener> morphiaListeners = new ArrayList<>();

  
  @PostConstruct
  public void initMorphia() {

    if (morphiaListeners.size() == 0) {
      throw new IllegalStateException("No morphia listeners registered. There should at least be a CompanyEntityListener registered.");
    }

    morphia.getMapper().getOptions().setObjectFactory(springObjectFactory);
  }

  @Override
  public <T> T createInstance(Class<T> clazz) {
    return getListenerInstance(clazz).orElse(defaultCreator.createInstance(clazz));
  }


  private <T> Optional<T> getListenerInstance(Class<T> clazz) {
    return (Optional<T>) morphiaListeners.stream()
        .filter(listener -> listener.getClass().isAssignableFrom(clazz))
        .findFirst();
  }

  @Override
  public <T> T createInstance(Class<T> clazz, DBObject dbObj) {
    return getListenerInstance(clazz).orElse(defaultCreator.createInstance(clazz, dbObj));
  }

  @Override
  public Object createInstance(Mapper mapper, MappedField mf, DBObject dbObj) {
    return defaultCreator.createInstance(mapper, mf, dbObj);
  }

  @Override
  public List createList(MappedField mf) {
    return defaultCreator.createList(mf);
  }

  @Override
  public Map createMap(MappedField mf) {
    return defaultCreator.createMap(mf);
  }

  @Override
  public Set createSet(MappedField mf) {
    return defaultCreator.createSet(mf);
  }
}
