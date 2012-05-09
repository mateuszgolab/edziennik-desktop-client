package com.polsl.edziennik.desktopclient.controller.utils.factory;

import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ILabel;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IMenu;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.ITextArea;

public interface IGuiComponentAbstractFactory {

	IButton createIconButton();

	IButton createTextButton();

	IMenu createMenu();

	ILabel createLabel();

	IFilter createFilter();

	ITextArea createTextArea();

}
