package io.yogh.gwt.wui.place;

public abstract class ApplicationPlace implements Place {
  public abstract static class Tokenizer<P extends ApplicationPlace> extends CompositeTokenizer<P> {}

}
