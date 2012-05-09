package com.polsl.edziennik.desktopclient.view.admin.panels;

import javax.swing.ComboBoxModel;
import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.model.tables.TableModel;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.SimpleFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.SimpleTablePanel;

public class ProjectTablePanel extends SimpleTablePanel {

	public ProjectTablePanel(TableModel tableModel, ComboBoxModel comboBoxModel, SimpleFilterPanel filter,
			final ButtonPanel buttonPanel, final JPanel preview) {

		super(bundle.getString("ProjectFrameTitle"), tableModel, bundle.getString("ProjectHint"), "teacherName",
				buttonPanel, preview);
		setColumnWidths();
		filterPosition = 2;

	}

	@Override
	public void setColumnWidths() {

		if (table.getColumnModel() == null) return;

		table.getColumnModel().getColumn(0).setPreferredWidth(23);
		for (int i = 1; i <= 2; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(215);

		}

	}

}
