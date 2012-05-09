package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;


public class TeacherTablePanel extends SimpleTablePanel{
	
	public TeacherTablePanel(TableModel tableModel, final ButtonPanel buttonPanel, final JPanel preview)
	{
		super(bundle.getString("TeacherFrameTitle"), tableModel, bundle.getString("teacherNamesHint"), "teacherName", buttonPanel, preview);
		setColumnWidths();
		filterPosition = 3;
	}
	
	public void setColumnWidths() {
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		for (int i = 1; i < 5; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(105);

		}

	}
	

}
