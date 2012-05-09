package com.polsl.edziennik.desktopclient.controller.utils.factory.implementations.swing;

import java.util.ResourceBundle;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IFilter;
import com.polsl.edziennik.desktopclient.properties.Properties;

/**
 * 
 * Klasa implementuj¹ca interfejs IFilter ,dostarcza filtry dokumentów oraz
 * arkuszów kalkulacyjnych
 * 
 * @author Mateusz Go³¹b
 * 
 */
public class DefaultFilter implements IFilter {

	private static ResourceBundle component = LangManager.getResource(Properties.Component);

	@Override
	public FileNameExtensionFilter getDocumentFilter() {

		FileNameExtensionFilter filter = new FileNameExtensionFilter(component.getString("documents"), "pdf", "doc",
				"docx");
		return filter;
	}

	@Override
	public FileNameExtensionFilter getXlsFilter() {

		FileNameExtensionFilter filter = new FileNameExtensionFilter(component.getString("excel"), "xls", "xlsx");
		return filter;
	}

}
