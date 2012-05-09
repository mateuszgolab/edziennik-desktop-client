package com.polsl.edziennik.desktopclient.controller.utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import com.polsl.edziennik.desktopclient.properties.Properties;

public class FrameToolkit {
	Toolkit toolkit;
	private ResourceBundle bundle = LangManager.getResource(Properties.Component);

	public FrameToolkit() {
		toolkit = Toolkit.getDefaultToolkit();
	}

	public Dimension getSize() {
		int width = toolkit.getScreenSize().width / 2;
		int height = toolkit.getScreenSize().height / 2;

		// if (DEFAULT_HEIGH < toolkit.getScreenSize().height) height =
		// DEFAULT_HEIGH;
		// if (DEFAULT_WIDTH < toolkit.getScreenSize().width) height =
		// DEFAULT_WIDTH;

		return new Dimension(width, height);
	}

	public Image getTitleIcon(String icon) {
		return toolkit.getImage(bundle.getString(icon));
	}

}
