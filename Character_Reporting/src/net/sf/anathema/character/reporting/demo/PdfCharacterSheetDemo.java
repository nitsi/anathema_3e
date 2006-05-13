package net.sf.anathema.character.reporting.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.reporting.encoder.PdfFirstPageEncoder;
import net.sf.anathema.character.reporting.second.SecondEditionPartEncoder;
import net.sf.anathema.framework.resources.AnathemaResources;
import net.sf.anathema.framework.resources.IAnathemaResources;
import net.sf.anathema.lib.control.BrowserControl;
import net.sf.anathema.lib.resources.IResources;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class PdfCharacterSheetDemo {

  public static void main(String[] args) {
    Document document = new Document(PageSize.A4, 40, 40, 15, 15);
    try {
      System.err.println(document.getPageSize());
      File outputStream = new File("iText.pdf");
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputStream));
      document.open();
      PdfContentByte directContent = writer.getDirectContent();
      IGenericCharacter character = createDemoCharacter();
      DemoGenericDescription description = createDemoDescription();
      SecondEditionPartEncoder partEncoder = new SecondEditionPartEncoder(createDemoResources(), 7);
      new PdfFirstPageEncoder(partEncoder).encode(directContent, character, description);
      BrowserControl.displayUrl(outputStream.toURL());
    }
    catch (Exception de) {
      System.err.println(de.getMessage());
    }
    finally {
      document.close();
    }
  }

  private static IResources createDemoResources() {
    Locale.setDefault(Locale.ENGLISH);
    IAnathemaResources resources = new AnathemaResources();
//    new CharacterCoreModule().initAnathemaResources(resources);
    return resources;
  }

  private static IGenericCharacter createDemoCharacter() {
    DemoGenericCharacter character = new DemoGenericCharacter();
    character.getConcept().setConceptText("Tolles Konzept");
    return character;
  }

  private static DemoGenericDescription createDemoDescription() {
    DemoGenericDescription description = new DemoGenericDescription();
    description.setName("Hugo the Brave");
    return description;
  }
}