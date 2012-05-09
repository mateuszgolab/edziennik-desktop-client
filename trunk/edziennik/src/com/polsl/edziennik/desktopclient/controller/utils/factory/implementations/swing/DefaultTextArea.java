package com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing;

import javax.swing.JTextArea;

import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ITextArea;

/**
 * 
 * Klasa implementuj¹ca interfejs ITextArea ,dostarcza komponent JTextArea
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class DefaultTextArea implements ITextArea {

	@Override
	public JTextArea getTextArea(int x, int y) {
		JTextArea area = new JTextArea(x, y);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);

		return area;
	}

	@Override
	public JTextArea getTextArea() {
		JTextArea area = new JTextArea();
		area.setLineWrap(true);
		area.setWrapStyleWord(true);

		return area;
	}

}
