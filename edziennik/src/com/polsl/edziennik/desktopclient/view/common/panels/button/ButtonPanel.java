package com.polsl.edziennik.desktopclient.view.common.panels.button;

import java.awt.FlowLayout;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.properties.Properties;

public abstract class ButtonPanel extends JPanel {

	protected JButton add;
	protected JButton remove;
	protected JButton save;
	protected ResourceBundle components = LangManager.getResource(Properties.Component);
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected IButton Button;
	protected String addName;
	protected String saveName;
	protected String removeName;

	public ButtonPanel() {
		super(new FlowLayout(FlowLayout.LEADING, 6, 6));

		// setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Zarz¹dzaj studentami"),
		// BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		Button = factory.createIconButton();

		create();
		add(add);
		add(remove);
		add(save);
		addOthers();

	}

	public ButtonPanel(String saveName, String addName, String removeName) {

		super(new FlowLayout(FlowLayout.LEADING, 6, 6));

		this.addName = addName;
		this.saveName = saveName;
		this.removeName = removeName;
		Button = factory.createIconButton();

		create();
		add(add);
		add(remove);
		add(save);
		addOthers();

	}

	public void activate(boolean value) {
		remove.setEnabled(value);
		save.setEnabled(value);
	}

	public void activateSave(boolean value) {
		save.setEnabled(value);
	}

	public JButton getAddButton() {
		return add;
	}

	public JButton getRemoveButton() {
		return remove;
	}

	public JButton getSaveButton() {
		return save;
	}

	public abstract void create();

	public abstract void addOthers();

}
