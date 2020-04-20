package com.lazytester.event.yaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;

import java.io.Reader;

import static org.junit.Assert.assertNotNull;

public class MyYaml extends Yaml {

  private static MyYaml instance;

  private MyYamlComposer composer;

  private MyYaml(BaseConstructor constructor) {
    super(constructor);
  }

  public static MyYaml createMyYaml(BaseConstructor constructor) {
    if (instance == null) {
      instance = new MyYaml(constructor);
    }
    return instance;
  }

  public static MyYaml getMyYaml() {
    assertNotNull(instance);
    return instance;
  }

  @Override
  public <T> T load(Reader io) {
    composer = new MyYamlComposer(new ParserImpl(new StreamReader(io)), composer);
    constructor.setComposer(composer);
    return (T) constructor.getSingleData(Object.class);
  }

//  public MyYamlComposer getComposer() {
//    return composer;
//  }
}
