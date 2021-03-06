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

import com.google.common.base.Charsets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public final class SchemaLoaderTest {
  @Rule public final TemporaryFolder tempFolder1 = new TemporaryFolder();
  @Rule public final TemporaryFolder tempFolder2 = new TemporaryFolder();

  @Test public void locateInMultiplePaths() throws IOException {
    File file1 = tempFolder1.newFile();
    File file2 = tempFolder2.newFile();

    new SchemaLoader()
        .addDirectory(tempFolder1.getRoot())
        .addDirectory(tempFolder2.getRoot())
        .addProto(file1)
        .addProto(file2)
        .load();
  }

  @Test public void failLocate() throws IOException {
    File file = tempFolder2.newFile();

    SchemaLoader loader = new SchemaLoader()
        .addDirectory(tempFolder1.getRoot())
        .addProto(file);
    try {
      loader.load();
      fail();
    } catch (FileNotFoundException expected) {
    }
  }

  @Test public void locateInZipFile() throws IOException {
    File file = tempFolder1.newFile();
    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
    zipOutputStream.putNextEntry(new ZipEntry("a/b/message.proto"));
    zipOutputStream.write("message Message {}".getBytes(Charsets.UTF_8));
    zipOutputStream.close();

    Schema schema = new SchemaLoader()
        .addDirectory(file)
        .addProto(Paths.get("a", "b", "message.proto"))
        .load();
    assertThat(schema.getType("Message")).isNotNull();
  }

  @Test public void failLocateInZipFile() throws IOException {
    File file = tempFolder1.newFile();
    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
    zipOutputStream.putNextEntry(new ZipEntry("a/b/trix.proto"));
    zipOutputStream.write("message Trix {}".getBytes(Charsets.UTF_8));
    zipOutputStream.close();

    try {
      new SchemaLoader()
          .addDirectory(file)
          .addProto(Paths.get("a", "b", "rabbit_food.proto"))
          .load();
      fail();
    } catch (FileNotFoundException expected) {
    }
  }
}
