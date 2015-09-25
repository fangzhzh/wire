/*
 * Copyright 2015 Square Inc.
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
package com.squareup.wire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ExtensionRegistry {
  static final ExtensionRegistry NO_EXTENSIONS = new ExtensionRegistry();

  private final Map<Class<? extends Message>, List<Extension<?, ?>>> messageToExtensions =
      new LinkedHashMap<>();

  /**
   * Creates a new instance that can encode and decode the extensions specified in
   * {@code extensionLists}. Typically the lists are generated as an {@code EXTENSIONS} field on
   * the "Ext_" prefixed classes.
   */
  @SafeVarargs
  public ExtensionRegistry(Iterable<Extension<?, ?>>... extensionLists) {
    this(Arrays.asList(extensionLists));
  }

  /**
   * Creates a new instance that can encode and decode the extensions specified in
   * {@code extensionLists}. Typically the lists are generated as an {@code EXTENSIONS} field on
   * the "Ext_" prefixed classes.
   */
  public ExtensionRegistry(Iterable<? extends Iterable<Extension<?, ?>>> extensionLists) {
    for (Iterable<Extension<?, ?>> extensionList : extensionLists) {
      for (Extension<?, ?> extension : extensionList) {
        registerExtension(extension);
      }
    }
  }

  private <T extends Message<T>> void registerExtension(Extension<T, ?> extension) {
    Class<? extends Message> messageClass = extension.getExtendedType();
    List<Extension<?, ?>> extensions = messageToExtensions.get(messageClass);
    if (extensions == null) {
      extensions = new ArrayList<>();
      messageToExtensions.put(messageClass, extensions);
    }
    extensions.add(extension);
  }

  @SuppressWarnings("unchecked")
  public <T extends Message<T>> List<Extension<T, ?>> extensions(Class<T> messageClass) {
    List<Extension<T, ?>> map = (List) messageToExtensions.get(messageClass);
    return map != null ? map : Collections.<Extension<T, ?>>emptyList();
  }

  /**
   * Returns the extension for {@code tag} on {@code messageType}, or null if no such extension is
   * known.
   */
  public <T extends Message<T>> Extension<T, ?> get(Class<T> messageType, int tag) {
    for (Extension<T, ?> extension : extensions(messageType)) {
      if (extension.getTag() == tag) return extension;
    }
    return null;
  }
}
