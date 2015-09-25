/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.wire.schema;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.squareup.wire.schema.internal.parser.EnumElement;
import java.util.Collection;
import java.util.Map;
import java.util.NavigableSet;

public final class EnumType extends Type {
  private final ProtoType protoType;
  private final EnumElement element;
  private final ImmutableList<EnumConstant> constants;
  private final Options options;

  EnumType(ProtoType protoType, EnumElement element,
      ImmutableList<EnumConstant> constants, Options options) {
    this.protoType = protoType;
    this.element = element;
    this.constants = constants;
    this.options = options;
  }

  @Override public Location location() {
    return element.location();
  }

  @Override public ProtoType name() {
    return protoType;
  }

  @Override public String documentation() {
    return element.documentation();
  }

  @Override public Options options() {
    return options;
  }

  @Override public ImmutableList<Type> nestedTypes() {
    return ImmutableList.of(); // Enums do not allow nested type declarations.
  }

  /** Returns the constant named {@code name}, or null if this enum has no such constant. */
  public EnumConstant constant(String name) {
    for (EnumConstant constant : constants()) {
      if (constant.name().equals(name)) {
        return constant;
      }
    }
    return null;
  }

  /** Returns the constant tagged {@code tag}, or null if this enum has no such constant. */
  public EnumConstant constant(int tag) {
    for (EnumConstant constant : constants()) {
      if (constant.tag() == tag) {
        return constant;
      }
    }
    return null;
  }

  public ImmutableList<EnumConstant> constants() {
    return constants;
  }

  @Override void validate(Linker linker) {
    linker = linker.withContext(this);

    if (!"true".equals(options.get("allow_alias"))) {
      validateTagUniqueness(linker);
    }
  }

  private void validateTagUniqueness(Linker linker) {
    Multimap<Integer, EnumConstant> tagToConstant = LinkedHashMultimap.create();
    for (EnumConstant constant : constants) {
      tagToConstant.put(constant.tag(), constant);
    }

    for (Map.Entry<Integer, Collection<EnumConstant>> entry : tagToConstant.asMap().entrySet()) {
      if (entry.getValue().size() > 1) {
        StringBuilder error = new StringBuilder();
        error.append(String.format("multiple enum constants share tag %s:", entry.getKey()));
        int index = 1;
        for (EnumConstant constant : entry.getValue()) {
          error.append(String.format("\n  %s. %s (%s)",
              index++, constant.name(), constant.location()));
        }
        linker.addError("%s", error);
      }
    }
  }

  @Override void link(Linker linker) {
  }

  @Override void linkOptions(Linker linker) {
    options.link(linker);
    for (EnumConstant constant : constants) {
      constant.linkOptions(linker);
    }
  }

  @Override Type retainAll(NavigableSet<String> identifiers) {
    String typeName = protoType.toString();

    // If this type is not retained, prune it.
    if (!identifiers.contains(typeName)) return null;

    ImmutableList<EnumConstant> retainedConstants = constants;
    if (Pruner.hasMarkedMember(identifiers, protoType)) {
      ImmutableList.Builder<EnumConstant> retainedConstantsBuilder = ImmutableList.builder();
      for (EnumConstant constant : constants) {
        if (identifiers.contains(typeName + '#' + constant.name())) {
          retainedConstantsBuilder.add(constant);
        }
      }
      retainedConstants = retainedConstantsBuilder.build();
    }

    return new EnumType(protoType, element, retainedConstants, options);
  }
}
