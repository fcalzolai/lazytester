package com.lazytester.event.yml2;

import org.yaml.snakeyaml.Yaml;
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
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class MyYamlLoader {

  private static final Constructor CUSTOM_CONSTRUCTOR = new MyYamlConstructor();

  private MyYamlLoader() {
  }

  /**
   * Parse the only YAML document in a stream and produce the Java Map. It provides support for the custom !include
   * YAML tag to split YAML contents across several files.
   */
  public static Map<String, Object> load(InputStream inputStream) {
    return new Yaml(CUSTOM_CONSTRUCTOR)
            .load(inputStream);
  }

  public static Iterable<Object> loadAll(InputStream inputStream) {
    return new Yaml(CUSTOM_CONSTRUCTOR)
            .loadAll(inputStream);
  }


  /**
   * Custom SnakeYAML constructor that registers custom tags.
   */
  private static class MyYamlConstructor extends Constructor {

    private static final String TAG_INCLUDE = "!include";

    MyYamlConstructor() {
      // Register custom tags
      yamlConstructors.put(new Tag(TAG_INCLUDE), new IncludeConstruct());
    }

    /**
     * The actual include tag construct.
     */
    private static class IncludeConstruct implements Construct {

      @Override
      public Object construct(Node node) {
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

      private Object parseInclusions(List<Node> inclusions) {

        List<InputStream> inputStreams = inputStreams(inclusions);

        try (final SequenceInputStream sequencedInputStream =
                     new SequenceInputStream(Collections.enumeration(inputStreams))) {

          Object load = new Yaml(CUSTOM_CONSTRUCTOR)
                  .load(sequencedInputStream);
          return load;

        } catch (IOException e) {
          System.err.println("Error closing the stream." + e);
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
//          Assert.assertNotNull(String.format("Resource file %s not found.", filePath), is);
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
}
