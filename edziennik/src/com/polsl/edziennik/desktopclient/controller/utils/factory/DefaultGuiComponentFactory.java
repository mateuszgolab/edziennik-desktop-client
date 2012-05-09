package com.polsl.edziennik.desktopclient.controller.utils.factory;

import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing.DefaultFilter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing.DefaultIconButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing.DefaultLabel;
import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing.DefaultMenu;
import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing.DefaultTextArea;
import com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing.DefaultTextButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IMenu;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ITextArea;

public class DefaultGuiComponentFactory implements IGuiComponentAbstractFactory {

	@Override
	public IButton createIconButton() {

		return new DefaultIconButton();
	}

	@Override
	public IButton createTextButton() {
		return new DefaultTextButton();
	}

	@Override
	public IMenu createMenu() {
		return new DefaultMenu();
	}

	@Override
	public ILabel createLabel() {
		return new DefaultLabel();
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
