package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.properties.Properties;

public class SimpleDialog extends JOptionPane {

	private String labelName;
	private JLabel label;
	private JLabel label2;
	private JTextField text;
	private JTextField text2;
	private ILabel Ilabel;
	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	private JPanel panel;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private String[] options = new String[] { bundle.getString("saveTextButton"), bundle.getString("cancelTextButton") };

	public SimpleDialog(String labelName, String label2Name) {
		this.labelName = labelName;
		Ilabel = factory.createLabel();
		label = Ilabel.getLabel(labelName);
		label2 = Ilabel.getLabel(label2Name);
		panel = new JPanel();
		text = new JTextField(20);
		text2 = new JTextField(10);
		panel.add(label);
		panel.add(text);
		panel.add(label2);
		panel.add(text2);
	}

	public SimpleDialog(String labelName) {
		this.labelName = labelName;
		Ilabel = factory.createLabel();
		label = Ilabel.getLabel(labelName);
		panel = new JPanel();
		text = new JTextField(20);
		panel.add(label);
		panel.add(text);
	}

	public int showDialog() {

		return this.showOptionDialog(null, panel, bundle.getString(labelName), JOptionPane.DEFAULT_OPTION,
				JOptionPane.YES_NO_OPTION, new ImageIcon(), options, options[0]);
	}

	public void clear() {
		text.setText("");
		if (text2 != null) text2.setText("");
	}

	public String getText() {
		return text.getText();

	}

	public Float getWeight() {
		return new Float(text2.getText());
	}
}
