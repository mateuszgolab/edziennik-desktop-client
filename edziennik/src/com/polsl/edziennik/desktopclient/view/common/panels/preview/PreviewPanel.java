package com.polsl.edziennik.desktopclient.view.common.panels.preview;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.properties.Properties;

public abstract class PreviewPanel extends JPanel {
	public static final int TEXT_SIZE = 30;
	protected CellConstraints cc;
	protected Integer selected;
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	public final ResourceBundle bundle = LangManager.getResource(Properties.Component);

	public PreviewPanel() {
		cc = new CellConstraints();
	}

	public abstract void setComponents();

	public abstract void clear();

	@Override
	public abstract void setEnabled(boolean b);

	public void setSelected(int index) {
		selected = index;
	}

	@Override
	public void disable() {
		clear();
		setEnabled(false);
	}

	public abstract void save();

	public abstract void delete();
}
