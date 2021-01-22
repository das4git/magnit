package main.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class Writer {

    private String filePath;
    private List<Integer> fields;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFields(List<Integer> fields) {
        this.fields = fields;
    }

    //Создаем 1.xml
    public void writeXml() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element element = document.createElement("entries");
            document.appendChild(element);

            for (Integer i : fields) {
                Element entry = document.createElement("entry");
                Element field = document.createElement("field");
                field.appendChild(document.createTextNode(String.valueOf(i)));
                entry.appendChild(field);
                element.appendChild(entry);
            }

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath + "/1.xml"));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);

            } catch (Exception e) {
                e.getMessage();
            }
    }
}
