package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import com.polsl.edziennik.desktopclient.model.tables.TableModel;

public class ProjectStudentTablePanel extends TablePanel {

	public ProjectStudentTablePanel(TableModel tableModel) {
		super(tableModel, "");
	}

	@Override
	public int getComboSelectedIndex() {
		return 0;
	}

	public void setColumnWidths() {

		if (table.getColumnModel() == null) return;

		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		for (int i = 1; i < 4; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(130);

		}

	}
}