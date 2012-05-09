package com.polsl.edziennik.desktopclient.controller.utils.factory;

import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.jide.JIDEIconButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.jide.JIDELabel;
import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.jide.JIDEMenu;
import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.jide.JIDETextButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing.DefaultFilter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing.DefaultTextArea;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IMenu;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ITextArea;

public class JIDEGuiComponentFactory implements IGuiComponentAbstractFactory {

	@Override
	public IButton createIconButton() {

		return new JIDEIconButton();
	}

	@Override
	public IButton createTextButton() {
		return new JIDETextButton();
	}

	@Override
	public IMenu createMenu() {
		return new JIDEMenu();
	}

	@Override
	public ILabel createLabel() {
		return new JIDELabel();
	}

	@Override
	public IFilter createFilter() {
		return new DefaultFilter();
	}

	@Override
	public ITextArea createTextArea() {
		return new DefaultTextArea();
	}
}
