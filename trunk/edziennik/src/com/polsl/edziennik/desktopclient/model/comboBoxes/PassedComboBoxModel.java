package com.polsl.edziennik.desktopclient.model.comboBoxes;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.event.ListDataListener;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;

public class PassedComboBoxModel extends ComboModel<String> {

	private ResourceBundle bundle = LangManager.getResource(Properties.Component);

	public PassedComboBoxModel() {
		data = new ArrayList<String>(3);
		data.add(bundle.getString("all"));
		data.add(bundle.getString("yes"));
		data.add(bundle.getString("no"));
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

}
