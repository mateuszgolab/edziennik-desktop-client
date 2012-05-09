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

public class AddRemoveButtonPanel extends JPanel {

	protected JButton add;
	protected JButton remove;
	protected ResourceBundle components = LangManager.getResource(Properties.Component);
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected IButton Button;

	public AddRemoveButtonPanel() {
		super(new FlowLayout(FlowLayout.LEADING, 6, 6));

		// setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Zarz¹dzaj studentami"),
		// BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		Button = factory.createIconButton();

		create();
		add(add);
		add(remove);

	}

	public void activate(boolean value) {
		add.setEnabled(value);
		remove.setEnabled(value);
	}

	public void activateAdd(boolean value) {
		add.setEnabled(value);
	}

	public void activateRemove(boolean value) {
		remove.setEnabled(value);
	}

	public void create() {
		add = Button.getButton("addIcon", "addButton");
		remove = Button.getButton("removeIcon", "removeButton");
	}

	public JButton getAddButton() {
		return add;
	}

	public JButton getRemoveButton() {
		return remove;
	}

}
