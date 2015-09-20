// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: ../wire-runtime/src/test/proto/google/protobuf/descriptor.proto at 332:1
package com.google.protobuf;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.TagMap;
import com.squareup.wire.WireEnum;
import com.squareup.wire.WireField;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public final class FieldOptions extends Message<FieldOptions> {
  public static final ProtoAdapter<FieldOptions> ADAPTER = ProtoAdapter.newMessageAdapter(FieldOptions.class);

  private static final long serialVersionUID = 0L;

  public static final CType DEFAULT_CTYPE = CType.STRING;

  public static final Boolean DEFAULT_PACKED = false;

  public static final Boolean DEFAULT_DEPRECATED = false;

  public static final String DEFAULT_EXPERIMENTAL_MAP_KEY = "";

  /**
   * The ctype option instructs the C++ code generator to use a different
   * representation of the field than it normally would.  See the specific
   * options below.  This option is not yet implemented in the open source
   * release -- sorry, we'll try to include it in a future version!
   */
  @WireField(
      tag = 1,
      adapter = "com.google.protobuf.FieldOptions$CType#ADAPTER"
  )
  public final CType ctype;

  /**
   * The packed option can be enabled for repeated primitive fields to enable
   * a more efficient representation on the wire. Rather than repeatedly
   * writing the tag and type for each element, the entire array is encoded as
   * a single length-delimited blob.
   */
  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#BOOL"
  )
  public final Boolean packed;

  /**
   * Is this field deprecated?
   * Depending on the target platform, this can emit Deprecated annotations
   * for accessors, or it will be completely ignored; in the very least, this
   * is a formalization for deprecating fields.
   */
  @WireField(
      tag = 3,
      adapter = "com.squareup.wire.ProtoAdapter#BOOL"
  )
  public final Boolean deprecated;

  /**
   * EXPERIMENTAL.  DO NOT USE.
   * For "map" fields, the name of the field in the enclosed type that
   * is the key for this map.  For example, suppose we have:
   *   message Item {
   *     required string name = 1;
   *     required string value = 2;
   *   }
   *   message Config {
   *     repeated Item items = 1 [experimental_map_key="name"];
   *   }
   * In this situation, the map key for Item will be set to "name".
   * TODO: Fully-implement this, then remove the "experimental_" prefix.
   */
  @WireField(
      tag = 9,
      adapter = "com.squareup.wire.ProtoAdapter#STRING"
  )
  public final String experimental_map_key;

  /**
   * The parser stores options it doesn't recognize here. See above.
   */
  @WireField(
      tag = 999,
      adapter = "com.google.protobuf.UninterpretedOption#ADAPTER",
      label = WireField.Label.REPEATED
  )
  public final List<UninterpretedOption> uninterpreted_option;

  public FieldOptions(CType ctype, Boolean packed, Boolean deprecated, String experimental_map_key, List<UninterpretedOption> uninterpreted_option) {
    this(ctype, packed, deprecated, experimental_map_key, uninterpreted_option, TagMap.EMPTY);
  }

  public FieldOptions(CType ctype, Boolean packed, Boolean deprecated, String experimental_map_key, List<UninterpretedOption> uninterpreted_option, TagMap tagMap) {
    super(tagMap);
    this.ctype = ctype;
    this.packed = packed;
    this.deprecated = deprecated;
    this.experimental_map_key = experimental_map_key;
    this.uninterpreted_option = immutableCopyOf(uninterpreted_option);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof FieldOptions)) return false;
    FieldOptions o = (FieldOptions) other;
    return equals(tagMap(), o.tagMap())
        && equals(ctype, o.ctype)
        && equals(packed, o.packed)
        && equals(deprecated, o.deprecated)
        && equals(experimental_map_key, o.experimental_map_key)
        && equals(uninterpreted_option, o.uninterpreted_option);
  }

  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      result = tagMap().hashCode();
      result = result * 37 + (ctype != null ? ctype.hashCode() : 0);
      result = result * 37 + (packed != null ? packed.hashCode() : 0);
      result = result * 37 + (deprecated != null ? deprecated.hashCode() : 0);
      result = result * 37 + (experimental_map_key != null ? experimental_map_key.hashCode() : 0);
      result = result * 37 + (uninterpreted_option != null ? uninterpreted_option.hashCode() : 1);
      hashCode = result;
    }
    return result;
  }

  public static final class Builder extends com.squareup.wire.Message.Builder<FieldOptions, Builder> {
    public CType ctype;

    public Boolean packed;

    public Boolean deprecated;

    public String experimental_map_key;

    public List<UninterpretedOption> uninterpreted_option;

    public Builder() {
      uninterpreted_option = newMutableList();
    }

    public Builder(FieldOptions message) {
      super(message);
      if (message == null) return;
      this.ctype = message.ctype;
      this.packed = message.packed;
      this.deprecated = message.deprecated;
      this.experimental_map_key = message.experimental_map_key;
      this.uninterpreted_option = copyOf(message.uninterpreted_option);
    }

    /**
     * The ctype option instructs the C++ code generator to use a different
     * representation of the field than it normally would.  See the specific
     * options below.  This option is not yet implemented in the open source
     * release -- sorry, we'll try to include it in a future version!
     */
    public Builder ctype(CType ctype) {
      this.ctype = ctype;
      return this;
    }

    /**
     * The packed option can be enabled for repeated primitive fields to enable
     * a more efficient representation on the wire. Rather than repeatedly
     * writing the tag and type for each element, the entire array is encoded as
     * a single length-delimited blob.
     */
    public Builder packed(Boolean packed) {
      this.packed = packed;
      return this;
    }

    /**
     * Is this field deprecated?
     * Depending on the target platform, this can emit Deprecated annotations
     * for accessors, or it will be completely ignored; in the very least, this
     * is a formalization for deprecating fields.
     */
    public Builder deprecated(Boolean deprecated) {
      this.deprecated = deprecated;
      return this;
    }

    /**
     * EXPERIMENTAL.  DO NOT USE.
     * For "map" fields, the name of the field in the enclosed type that
     * is the key for this map.  For example, suppose we have:
     *   message Item {
     *     required string name = 1;
     *     required string value = 2;
     *   }
     *   message Config {
     *     repeated Item items = 1 [experimental_map_key="name"];
     *   }
     * In this situation, the map key for Item will be set to "name".
     * TODO: Fully-implement this, then remove the "experimental_" prefix.
     */
    public Builder experimental_map_key(String experimental_map_key) {
      this.experimental_map_key = experimental_map_key;
      return this;
    }

    /**
     * The parser stores options it doesn't recognize here. See above.
     */
    public Builder uninterpreted_option(List<UninterpretedOption> uninterpreted_option) {
      checkElementsNotNull(uninterpreted_option);
      this.uninterpreted_option = uninterpreted_option;
      return this;
    }

    @Override
    public FieldOptions build() {
      return new FieldOptions(ctype, packed, deprecated, experimental_map_key, uninterpreted_option, buildTagMap());
    }
  }

  public enum CType implements WireEnum {
    /**
     * Default mode.
     */
    STRING(0),

    CORD(1),

    STRING_PIECE(2);

    public static final ProtoAdapter<CType> ADAPTER = ProtoAdapter.newEnumAdapter(CType.class);

    private final int value;

    CType(int value) {
      this.value = value;
    }

    /**
     * Return the constant for {@code value} or null.
     */
    public static CType fromValue(int value) {
      switch (value) {
        case 0: return STRING;
        case 1: return CORD;
        case 2: return STRING_PIECE;
        default: return null;
      }
    }

    @Override
    public int getValue() {
      return value;
    }
  }
}
