package com.polsl.edziennik.desktopclient.controller.utils;

import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class HtmlEditor extends JEditorPane {

	public HtmlEditor(String html) {
		// editor = new JEditorPane();
		HTMLEditorKit kit = new HTMLEditorKit();
		setEditorKit(kit);

		// add some styles to the html
		StyleSheet styleSheet = kit.getStyleSheet();
		styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
		// styleSheet.addRule("h1 {color: blue;}");
		// styleSheet.addRule("h2 {color: #ff0000;}");
		styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");

		// create some simple html as a string

		// create a document, set it on the editor, then add the html
		Document doc = kit.createDefaultDocument();
		setDocument(doc);
		setText(html);

		// scroll = new JScrollPane(editor);

		// add(scroll);
	}

}
