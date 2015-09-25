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

import com.squareup.wire.schema.internal.parser.RpcElement;

public final class Rpc {
  private final RpcElement element;
  private final Options options;
  private ProtoType requestType;
  private ProtoType responseType;

  Rpc(RpcElement element) {
    this.element = element;
    this.options = new Options(Options.METHOD_OPTIONS, element.options());
  }

  public Location location() {
    return element.location();
  }

  public String name() {
    return element.name();
  }

  public String documentation() {
    return element.documentation();
  }

  public ProtoType requestType() {
    return requestType;
  }

  public ProtoType responseType() {
    return responseType;
  }

  public Options options() {
    return options;
  }

  void link(Linker linker) {
    linker = linker.withContext(this);
    requestType = linker.resolveNamedType(element.requestType());
    responseType = linker.resolveNamedType(element.responseType());
  }

  void linkOptions(Linker linker) {
    linker = linker.withContext(this);
    options.link(linker);
  }

  void validate(Linker linker) {
    linker = linker.withContext(this);
    linker.validateImport(location(), requestType);
    linker.validateImport(location(), responseType);
  }
}
