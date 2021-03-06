// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: ../wire-runtime/src/test/proto/foreign.proto at 28:1
package com.squareup.wire.protos.foreign;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.TagMap;
import com.squareup.wire.WireField;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;

public final class ForeignMessage extends Message<ForeignMessage> {
  public static final ProtoAdapter<ForeignMessage> ADAPTER = ProtoAdapter.newMessageAdapter(ForeignMessage.class);

  private static final long serialVersionUID = 0L;

  public static final Integer DEFAULT_I = 0;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT32"
  )
  public final Integer i;

  public ForeignMessage(Integer i) {
    this(i, TagMap.EMPTY);
  }

  public ForeignMessage(Integer i, TagMap tagMap) {
    super(tagMap);
    this.i = i;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof ForeignMessage)) return false;
    ForeignMessage o = (ForeignMessage) other;
    return equals(tagMap(), o.tagMap())
        && equals(i, o.i);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = tagMap().hashCode();
      result = result * 37 + (i != null ? i.hashCode() : 0);
      super.hashCode = result;
    }
    return result;
  }

  public static final class Builder extends com.squareup.wire.Message.Builder<ForeignMessage, Builder> {
    public Integer i;

    public Builder() {
    }

    public Builder(ForeignMessage message) {
      super(message);
      if (message == null) return;
      this.i = message.i;
    }

    public Builder i(Integer i) {
      this.i = i;
      return this;
    }

    @Override
    public ForeignMessage build() {
      return new ForeignMessage(i, buildTagMap());
    }
  }
}
