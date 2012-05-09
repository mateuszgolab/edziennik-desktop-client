package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import com.polsl.edziennik.desktopclient.model.tables.TableModel;

public class ExamTaskTablePanel extends SimpleTablePanel {

	public ExamTaskTablePanel(TableModel<?> tableModel) {
		super(tableModel, bundle.getString("examTasksHint"));
		setColumnWidths();
	}

	@Override
	public void setColumnWidths() {

		if (table.getColumnModel() == null) return;

		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		for (int i = 1; i <= 3; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(145);
		}
	}

}
