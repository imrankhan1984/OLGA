/*
 * -------------------------
 * 
 * MIT License
 * 
 * Copyright (c) 2018, Schneider Electric USA, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * ---------------------
 */
package semanticstore.ontology.library.generator.code.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import freemarker.template.TemplateException;
import semanticstore.ontology.library.generator.olga.OLGA;

public class PartialOptionNotSet {

  @BeforeClass
  public static void testSimple() throws OWLOntologyStorageException, IOException,
      TemplateException, XmlPullParserException, MavenInvocationException {
    File resourcesDirectory = new File("src/test/resources/simple/simple.owl");
    OLGA.main(new String[] {"--out", "./Output/", "--code", "cs", "--library", "trinity", "--name",
        "SimpleOntology", "--path", resourcesDirectory.getAbsolutePath()});
    assertTrue(!OLGA.getResult().contains("Fail"));
  }

  @Test
  public void testBuilding() {
    Path fileName =
        Paths.get("Output/SimpleOntology-dotnetTrinity/SimpleOntology/Rdf/Model/Building.cs")
            .toAbsolutePath();
    assertEquals(fileName.getFileName().toString(), "Building.cs");
    try (Stream<String> stream = Files.lines(fileName)) {
      assertTrue(!stream.anyMatch(
          line -> line.contains("public partial class Building : PhysicalLocation, IBuilding")));
    } catch (IOException e) {
      fail();
    }

  }
}
