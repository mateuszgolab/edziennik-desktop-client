package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.jidesoft.swing.JideScrollPane;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.tables.LabActivityAttendanceTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.SaveExitButtonPanel;

public class LabActivityAttendancesDialog extends JDialog {
	private JTable table;
	private JScrollPane scrollPane;
	private LabActivityAttendanceTableModel tableModel;
	private FrameToolkit frameToolkit = new FrameToolkit();
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private SaveExitButtonPanel buttonPanel;

	public LabActivityAttendancesDialog() {
		setTitle(bundle.getString("Attendances"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(30 + 140 * 3 + 270 + 40, preferredSize.getHeight());
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("AttendancesIcon"));
		setLocationRelativeTo(null);

		tableModel = new LabActivityAttendanceTableModel();
		table = new JTable(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane = new JideScrollPane(table);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		add(scrollPane, BorderLayout.CENTER);

		buttonPanel = new SaveExitButtonPanel("saveChangesHint");
		add(buttonPanel, BorderLayout.SOUTH);

		setColumnWidths();
	}

	public void setColumnWidths() {
		if (table.getColumnCount() <= 0) return;
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		for (int i = 1; i < 4; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(140);

		}
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setPreferredWidth(190);
	}
}