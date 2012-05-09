package com.polsl.edziennik.desktopclient.view.admin.panels;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.SimpleTablePanel;

public class GroupTablePanel extends SimpleTablePanel {

	public GroupTablePanel(TableModel tableModel, final ButtonPanel buttonPanel, final JPanel preview) {
		super(bundle.getString("GroupFrameTitle"), tableModel, bundle.getString("courseHint"), "course", buttonPanel,
				preview);
		setColumnWidths();
		filterPosition = 3;
	}

	public GroupTablePanel() {
		super(bundle.getString("GroupFrameTitle"), bundle.getString("courseHint"), "course");
	}

	public void setColumnWidths() {
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		for (int i = 1; i < 5; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(105);

		}

	}

}
