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
import com.squareup.wire.schema.internal.parser.ExtendElement;
import com.squareup.wire.schema.internal.parser.FieldElement;

public final class Extend {
  private final ExtendElement element;
  private final ImmutableList<Field> fields;
  private ProtoType protoType;

  Extend(String packageName, ExtendElement element) {
    this.element = element;

    ImmutableList.Builder<Field> fields = ImmutableList.builder();
    for (FieldElement field : element.fields()) {
      fields.add(new Field(packageName, field));
    }
    this.fields = fields.build();
  }

  public Location location() {
    return element.location();
  }

  public ProtoType type() {
    return protoType;
  }

  public String documentation() {
    return element.documentation();
  }

  public ImmutableList<Field> fields() {
    return fields;
  }

  void link(Linker linker) {
    linker = linker.withContext(this);
    protoType = linker.resolveNamedType(element.name());
    for (Field field : fields) {
      field.link(linker);
    }
  }

  void validate(Linker linker) {
    linker = linker.withContext(this);
    for (Field field : fields) {
      field.validate(linker, true);
    }
    linker.validateImport(location(), type());
  }
}
