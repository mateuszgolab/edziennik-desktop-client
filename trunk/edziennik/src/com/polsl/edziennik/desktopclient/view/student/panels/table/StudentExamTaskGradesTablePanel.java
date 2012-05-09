package com.polsl.edziennik.desktopclient.view.student.panels.table;

import javax.swing.table.TableModel;

import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.SimpleTablePanel;

public class StudentExamTaskGradesTablePanel extends SimpleTablePanel {

	public StudentExamTaskGradesTablePanel(TableModel tableModel, String title) {
		super(tableModel, title);
		setColumnWidths();
	}

	@Override
	public void setColumnWidths() {
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);

	}

}
