package com.lazytester.event.yaml;

import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class MyNewYamlConstructor<T> extends Constructor {

  private static final String TAG_INCLUDE = "!include";

  private final Class<T> clazz;

  public MyNewYamlConstructor(Class<T> clazz) {
    super(clazz);
    this.clazz = clazz;
    yamlConstructors.put(new Tag(TAG_INCLUDE), new IncludeConstruct());
  }

  private class IncludeConstruct implements Construct {

    @Override
    public T construct(Node node) {
      List<Node> inclusions = castToSequenceNode(node);
      return parseInclusions(inclusions);
    }

    @Override
    public void construct2ndStep(Node node, Object object) {
      // do nothing
    }

    private List<Node> castToSequenceNode(Node node) {
      try {
        return ((SequenceNode) node).getValue();

      } catch (ClassCastException e) {
        throw new IllegalArgumentException(String.format("The !import value must be a sequence node, but " +
                "'%s' found.", node));
      }
    }

    private T parseInclusions(List<Node> inclusions) {

      List<InputStream> inputStreams = inputStreams(inclusions);

      try (final SequenceInputStream sequencedInputStream =
                   new SequenceInputStream(Collections.enumeration(inputStreams))) {

        T load = MyYaml.getMyYaml().load(sequencedInputStream);
        return load;

      } catch (IOException e) {
        System.err.println("Error closing the stream. " + e);
        return null;
      }
    }

    private List<InputStream> inputStreams(List<Node> scalarNodes) {
      return scalarNodes.stream()
              .map(this::inputStream)
              .filter(Objects::nonNull)
              .collect(toList());
    }

    private InputStream inputStream(Node scalarNode) {
      String filePath = castToScalarNode(scalarNode).getValue();
      final InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
//      Assert.assertNotNull(String.format("Resource file %s not found.", filePath), is);
      return is;
    }

    private ScalarNode castToScalarNode(Node scalarNode) {
      try {
        return ((ScalarNode) scalarNode);

      } catch (ClassCastException e) {
        throw new IllegalArgumentException(String.format("The value must be a scalar node, but '%s' found" +
                ".", scalarNode));
      }
    }
  }

}
