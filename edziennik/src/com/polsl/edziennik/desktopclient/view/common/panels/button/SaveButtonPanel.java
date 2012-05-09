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

public class SaveButtonPanel extends JPanel {

	protected JButton save;
	protected ResourceBundle components = LangManager.getResource(Properties.Component);
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected IButton Button;

	public SaveButtonPanel() {
		super(new FlowLayout(FlowLayout.LEADING, 6, 6));

		// setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Zarz¹dzaj studentami"),
		// BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		Button = factory.createIconButton();

		create();
		add(save);

	}

	public void activate(boolean value) {
		save.setEnabled(value);
	}

	public void activateSave(boolean value) {
		save.setEnabled(value);
	}

	public void create() {
		save = Button.getButton("saveIcon", "saveButton");
	}

	public JButton getSaveButton() {
		return save;
	}

}
