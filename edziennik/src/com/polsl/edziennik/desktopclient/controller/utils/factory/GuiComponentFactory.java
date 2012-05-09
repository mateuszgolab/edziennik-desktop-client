package com.polsl.edziennik.desktopclient.controller.utils.factory;

public final class GuiComponentFactory {

	private static volatile IGuiComponentAbstractFactory instance = null;

	public static IGuiComponentAbstractFactory getInstance() {
		if (instance == null) {
			synchronized (IGuiComponentAbstractFactory.class) {
				if (instance == null) {
					instance = new DefaultGuiComponentFactory();
				}

			}
		}
		return instance;
	}

	private GuiComponentFactory() {

	}

}
