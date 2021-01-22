package main.java;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    //Парсим 2.xml
    public List<Integer> parseXml() {

        List<Integer> fields = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath + "/2.xml"));
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("entry");
            for (int i = 0; i < nodeList.getLength(); i++) {
                int n = Integer.parseInt(nodeList
                        .item(i)
                        .getAttributes()
                        .item(0)
                        .getTextContent());
                fields.add(n);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return fields;
    }

    //трансформируем Xml по образцу
    public void transformXml() throws Exception {
        try {
            Source xslt = new StreamSource(new File("src/main/resources/transformer.xslt"));
            Source xml = new StreamSource(new File(this.filePath + "/1.xml"));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer =  transformerFactory.newTransformer(xslt);
            transformer.transform(xml, new StreamResult(new File(this.filePath + "/2.xml")));

        } catch (Exception e) {
            e.getMessage();
        }
    }
}
