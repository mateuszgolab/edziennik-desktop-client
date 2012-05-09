package com.polsl.edziennik.desktopclient.view.common.panels.button;

import java.awt.FlowLayout;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.properties.Properties;

public class SaveExitButtonPanel extends JPanel {
	protected JButton exit;
	protected JButton save;
	protected ResourceBundle components = LangManager.getResource(Properties.Component);
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected IButton Button;

	public SaveExitButtonPanel(String hint) {
		super(new FlowLayout(FlowLayout.LEADING, 6, 6));

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		Button = factory.createTextButton();

		save = Button.getButton("saveTextButton", hint);
		exit = Button.getButton("exitTextButton", "exitTextButton");

		add(save);
		add(exit);

	}

	public void activate(boolean value) {
		exit.setEnabled(value);
		save.setEnabled(value);
	}

	public void activateSave(boolean value) {
		save.setEnabled(value);
	}

	public JButton getExitButton() {
		return exit;
	}

	public JButton getSaveButton() {
		return save;
	}

}