package com.etz.cashpoint.switchit;

import org.springframework.stereotype.Service;

/**
 *
 * @author mishael.harry
 */
@Service
public class XMLUtils {

    String xml;

    public XMLUtils() {
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getXMLValue(String tagName) {
        try {
            return removeNewLine(this.xml.substring(this.xml.indexOf("<" + tagName + ">") + tagName.length() + 2, this.xml
                    .indexOf("</" + tagName + ">")).trim());
        } catch (Exception sd) {
        }
        return "";
    }

    private String removeNewLine(String textValue) {
        textValue = textValue.replaceAll("\n", "");
        textValue = textValue.replaceAll("\t", "");
        textValue = textValue.replaceAll("\\n", "");
        textValue = textValue.replaceAll("\\t", "");
        textValue = textValue.replaceAll("\r", "");
        textValue = textValue.replaceAll("\\r", "");
        textValue = textValue.replaceAll("\r\n", "");
        textValue = textValue.replaceAll("\\r\\n", "");
        return textValue;
    }
}
