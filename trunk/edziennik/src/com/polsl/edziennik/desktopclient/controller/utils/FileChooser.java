package com.polsl.edziennik.desktopclient.controller.utils;

import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import com.polsl.edziennik.desktopclient.properties.Properties;

public final class FileChooser {

	private static volatile JFileChooser downloadInstance = null;
	private static volatile JFileChooser sendInstance = null;
	private static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	
	public static JFileChooser getDownloadInstance() {
		if (downloadInstance == null) {
			synchronized (FileChooser.class) {
				if (downloadInstance == null) {
					downloadInstance = new JFileChooser(".");
					downloadInstance.setName(bundle.getString("saveFile"));
				}
			}
		}
		return downloadInstance;
	}

	public static JFileChooser getSendInstance() {
		if (sendInstance == null) {
			synchronized (FileChooser.class) {
				if (sendInstance == null) {
					sendInstance = new JFileChooser(".");
					sendInstance.setName(bundle.getString("sendFile"));
				}
			}
		}
		return sendInstance;
	}

	private FileChooser() {

	}

}
