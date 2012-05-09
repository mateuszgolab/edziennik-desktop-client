package com.polsl.edziennik.desktopclient.view.common.panels.filter;

import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.properties.Properties;

public abstract class SimpleFilterPanel extends JPanel {
	protected JPanel filter;
	protected JButton icon;
	protected JLabel label;
	protected ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected String hint;
	protected String labelName;
	protected ILabel Label;

	public SimpleFilterPanel() {
		// setFilter(15);
		// if (filter != null) add(filter);
	}

	public SimpleFilterPanel(TableModel tableModel, String hint, String labelName) {
		this.hint = hint;
		this.labelName = labelName;

		Label = factory.createLabel();

		setFilter(15);
		if (filter != null) add(filter);

	}

	public abstract void setFilter(int size);

	public abstract String getText();

	public JButton getIcon() {
		return icon;
	}
}