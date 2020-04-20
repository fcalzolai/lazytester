package com.lazytester.event.yaml;

import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.parser.Parser;
import org.yaml.snakeyaml.resolver.Resolver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.*;

public class MyYamlComposer extends Composer {

  protected final Parser parser;

  private final Resolver resolver;
  private final Map<String, Node> anchors;
  private final Set<Node> recursiveNodes;

  public MyYamlComposer(Parser parser, MyYamlComposer composer) {
    this(parser, new Resolver(), composer);
  }

  private MyYamlComposer(Parser parser, Resolver resolver, MyYamlComposer composer) {
    super(parser, resolver);
    this.parser = parser;
    this.resolver = resolver;
    this.anchors = composer == null
            ? new HashMap<>()
            : composer.anchors;
    this.recursiveNodes = composer == null
            ? new HashSet<>()
            : composer.recursiveNodes;
  }

  /**
   * Checks if further documents are available.
   *
   * @return <code>true</code> if there is at least one more document.
   */
  public boolean checkNode() {
    // Drop the STREAM-START event.
    if (parser.checkEvent(Event.ID.StreamStart)) {
      parser.getEvent();
    }
    // If there are more documents available?
    return !parser.checkEvent(Event.ID.StreamEnd);
  }

  /**
   * Reads and composes the next document.
   *
   * @return The root node of the document or <code>null</code> if no more
   * documents are available.
   */
  public Node getNode() {
    // Drop the DOCUMENT-START event.
    parser.getEvent();
    // Compose the root node.
    Node node = composeNode(null);
    // Drop the DOCUMENT-END event.
    parser.getEvent();
//    this.anchors.clear();
//    recursiveNodes.clear();
    return node;
  }

  /**
   * Reads a document from a source that contains only one document.
   * <p>
   * If the stream contains more than one document an exception is thrown.
   * </p>
   *
   * @return The root node of the document or <code>null</code> if no document
   * is available.
   */
  public Node getSingleNode() {
    // Drop the STREAM-START event.
    parser.getEvent();
    // Compose a document if the stream is not empty.
    Node document = null;
    if (!parser.checkEvent(Event.ID.StreamEnd)) {
      document = getNode();
    }
    // Ensure that the stream contains no more documents.
    if (!parser.checkEvent(Event.ID.StreamEnd)) {
      Event event = parser.getEvent();
      throw new ComposerException("expected a single document in the stream",
              document.getStartMark(), "but found another document", event.getStartMark());
    }
    // Drop the STREAM-END event.
    parser.getEvent();
    return document;
  }

  private Node composeNode(Node parent) {
    if (parent != null) recursiveNodes.add(parent);
    final Node node;
    if (parser.checkEvent(Event.ID.Alias)) {
      AliasEvent event = (AliasEvent) parser.getEvent();
      String anchor = event.getAnchor();
      if (!anchors.containsKey(anchor)) {
        throw new ComposerException(null, null, "found undefined alias " + anchor,
                event.getStartMark());
      }
      node = anchors.get(anchor);
      if (recursiveNodes.remove(node)) {
        node.setTwoStepsConstruction(true);
      }
    } else {
      NodeEvent event = (NodeEvent) parser.peekEvent();
      String anchor = event.getAnchor();
      // the check for duplicate anchors has been removed (issue 174)
      if (parser.checkEvent(Event.ID.Scalar)) {
        node = composeScalarNode(anchor);
      } else if (parser.checkEvent(Event.ID.SequenceStart)) {
        node = composeSequenceNode(anchor);
      } else {
        node = composeMappingNode(anchor);
      }
    }
    recursiveNodes.remove(parent);
    return node;
  }

  protected Node composeScalarNode(String anchor) {
    ScalarEvent ev = (ScalarEvent) parser.getEvent();
    String tag = ev.getTag();
    boolean resolved = false;
    Tag nodeTag;
    if (tag == null || tag.equals("!")) {
      nodeTag = resolver.resolve(NodeId.scalar, ev.getValue(),
              ev.getImplicit().canOmitTagInPlainScalar());
      resolved = true;
    } else {
      nodeTag = getNodeTag(ev, tag); //FIXME check the content of this tag, if t's an include import from the specified file
    }
    Node node = new ScalarNode(nodeTag, resolved, ev.getValue(), ev.getStartMark(),
            ev.getEndMark(), ev.getScalarStyle());
    if (anchor != null) {
      anchors.put(anchor, node);
    }
    return node;
  }

  private Tag getNodeTag(ScalarEvent ev, String tag) {
    if (tag.equals("!include")) {
      String value = ev.getValue();
      URL url = MyNewYamlConstructor.class.getClassLoader().getResource(value);
      MyYaml yaml = MyYaml.getMyYaml();
      try {
        Object load = yaml.load(new FileReader(url.getPath()));
        System.out.println(yaml);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    return new Tag(tag);
  }

  protected Node composeSequenceNode(String anchor) {
    SequenceStartEvent startEvent = (SequenceStartEvent) parser.getEvent();
    String tag = startEvent.getTag();
    Tag nodeTag;
    boolean resolved = false;
    if (tag == null || tag.equals("!")) {
      nodeTag = resolver.resolve(NodeId.sequence, null, startEvent.getImplicit());
      resolved = true;
    } else {
      nodeTag = new Tag(tag);
    }
    final ArrayList<Node> children = new ArrayList<Node>();
    SequenceNode node = new SequenceNode(nodeTag, resolved, children, startEvent.getStartMark(),
            null, startEvent.getFlowStyle());
    if (anchor != null) {
      anchors.put(anchor, node);
    }
    while (!parser.checkEvent(Event.ID.SequenceEnd)) {
      children.add(composeNode(node));
    }
    Event endEvent = parser.getEvent();
    node.setEndMark(endEvent.getEndMark());
    return node;
  }

  protected Node composeMappingNode(String anchor) {
    MappingStartEvent startEvent = (MappingStartEvent) parser.getEvent();
    String tag = startEvent.getTag();
    Tag nodeTag;
    boolean resolved = false;
    if (tag == null || tag.equals("!")) {
      nodeTag = resolver.resolve(NodeId.mapping, null, startEvent.getImplicit());
      resolved = true;
    } else {
      nodeTag = new Tag(tag);
    }

    final List<NodeTuple> children = new ArrayList<NodeTuple>();
    MappingNode node = new MappingNode(nodeTag, resolved, children, startEvent.getStartMark(),
            null, startEvent.getFlowStyle());
    if (anchor != null) {
      anchors.put(anchor, node);
    }
    while (!parser.checkEvent(Event.ID.MappingEnd)) {
      composeMappingChildren(children, node);
    }
    Event endEvent = parser.getEvent();
    node.setEndMark(endEvent.getEndMark());
    return node;
  }

  protected void composeMappingChildren(List<NodeTuple> children, MappingNode node) {
    Node itemKey = composeKeyNode(node);
    if (itemKey.getTag().equals(Tag.MERGE)) {
      node.setMerged(true);
    }
    Node itemValue = composeValueNode(node);
    children.add(new NodeTuple(itemKey, itemValue));
  }

  protected Node composeKeyNode(MappingNode node) {
    return composeNode(node);
  }

  protected Node composeValueNode(MappingNode node) {
    return composeNode(node);
  }
}
