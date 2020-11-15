package com.basf.infopipeline.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.basf.infopipeline.model.Patent;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class XmlPatentParserImplTest {


  XmlPatentParserImpl xmlPatentParser = new XmlPatentParserImpl();

  @Test
  void test() throws Exception {

    final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("US06198563B1.xml");

    Patent patent = xmlPatentParser.getPatent(getDocument(inputStream));
    assertThat(patent).isNotNull();
    assertThat(patent.getAbstractText()).isEqualTo("\n"
        + "    An optical scanning apparatus includes a light source unit for emitting a light beam, an image-forming lens for\n"
        + "    imaging the light beam in a main-scanning direction, a beam deflector for reflecting and deflecting a line image\n"
        + "    produced by the image-forming lens, a reflective optical system including a plurality of reflecting mirrors for\n"
        + "    reflecting a deflected light beam incident on the reflective optical system a plurality of times, wherein reflecting\n"
        + "    surfaces of the plurality of reflecting mirrors are tilted with respect to a system axis of the optical scanning\n"
        + "    apparatus, the reflective optical system includes an image-forming mirror for converging the deflected light beam to\n"
        + "    form a beam spot for scanning a scanned surface at a constant velocity, and the image-forming mirror has an\n"
        + "    anamorphic configuration obtained by rotating a curve drawn with a first radius on a main-scanning plane, around an\n"
        + "    axis residing on the main-scanning plane and parallel with the main-scanning direction, maintaining a second radius.\n"
        + "  ");
    assertThat(patent.getDescription()).isEqualTo("\n"
        + "\n"
        + "    A description will now be given of a fifth embodiment of the present invention.\n"
        + "    \n"
        + "    The optical scanning apparatus according to the fifth embodiment has the following dimensions.\n"
        + "  ");
    assertThat(patent.getDate()).isEqualTo("20010306");
    assertThat(patent.getInventionTitle()).isEqualTo("Optical scanning apparatus with improved design\n"
        + "      flexibility\n"
        + "    ");

    assertThat(patent.getApplicationReference()).isNotNull();

  }

  private Document getDocument(InputStream xmlFile) throws Exception {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(xmlFile);
    doc.getDocumentElement().normalize();
    return doc;

  }


}
