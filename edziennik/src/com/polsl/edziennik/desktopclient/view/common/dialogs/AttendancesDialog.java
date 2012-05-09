package com.polsl.edziennik.desktopclient.view.common.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jidesoft.swing.JideScrollPane;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.tables.AttendanceTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.SaveExitButtonPanel;

public class AttendancesDialog extends JDialog {
	private JTable table;
	private JScrollPane scrollPane;
	private AttendanceTableModel tableModel;
	private FrameToolkit frameToolkit = new FrameToolkit();
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	private SaveExitButtonPanel buttonPanel;

	public AttendancesDialog(AttendanceTableModel model) {
		setTitle(bundle.getString("Attendances"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(30 + 140 * 2 + 270 + 40, preferredSize.getHeight());
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("AttendancesIcon"));
		setLocationRelativeTo(null);

		tableModel = model;
		table = new JTable(tableModel);
		// table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowSelectionAllowed(false);
		scrollPane = new JideScrollPane(table);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		add(scrollPane, BorderLayout.CENTER);

		buttonPanel = new SaveExitButtonPanel("saveChangesHint");
		add(buttonPanel, BorderLayout.SOUTH);

		setColumnWidths();
	}

	public void setColumnWidths() {

		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		for (int i = 1; i < 5; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(130);

		}
	}

	public JButton getSave() {
		return buttonPanel.getSaveButton();
	}

	public JButton getCancelButton() {
		return buttonPanel.getExitButton();
	}

	public void clearSelection() {
		table.clearSelection();
	}

}