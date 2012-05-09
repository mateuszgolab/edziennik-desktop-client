package com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces;

import javax.swing.filechooser.FileNameExtensionFilter;

public interface IFilter {

	FileNameExtensionFilter getDocumentFilter();

	FileNameExtensionFilter getXlsFilter();
}
