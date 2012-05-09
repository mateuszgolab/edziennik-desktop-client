package com.polsl.edziennik.desktopclient.view.common.panels.tablePanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.jidesoft.swing.JideScrollPane;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.DefaultSimpleFilterPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.filter.SimpleFilterPanel;

public abstract class SimpleTablePanel extends JPanel {

	private JScrollPane scrollPane;
	protected JTable table;
	private SimpleFilterPanel filter;
	private TableModel tableModel;
	protected static ResourceBundle bundle = LangManager.getResource(Properties.Component);
	protected String hint;
	protected String labelName;
	protected TableRowSorter<TableModel> sorter;
	protected int filterPosition;

	public SimpleTablePanel(String title, TableModel tableModel, String hint, String labelName,
			final ButtonPanel buttonPanel, final JPanel preview) {

		super(new BorderLayout());
		this.tableModel = tableModel;
		this.hint = hint;
		this.labelName = labelName;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		table = new JTable(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		sorter = new TableRowSorter<TableModel>(tableModel);
		table.setRowSorter(sorter);

		scrollPane = new JideScrollPane(table);
		// setColumnWidths();

		add(scrollPane, BorderLayout.CENTER);
		filter = new DefaultSimpleFilterPanel(tableModel, hint, labelName);
		if (filter != null) {
			add(filter, BorderLayout.NORTH);
			filter.getIcon().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					runFilter(filter.getText());

					if (buttonPanel != null) {
						buttonPanel.activate(false);
					}

					if (preview != null) {
						preview.setEnabled(false);
						preview.disable();
					}

					clearSelection();
				}
			});
		}
	}

	public SimpleTablePanel(String title, String hint, String labelName) {

		super(new BorderLayout());
		this.hint = hint;
		this.labelName = labelName;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

	}

	public SimpleTablePanel(String title, String hint, TableModel tableModel, SimpleFilterPanel filter, JPanel preview) {
		super(new BorderLayout());
		this.tableModel = tableModel;
		this.hint = hint;
		this.filter = filter;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		table = new JTable(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		sorter = new TableRowSorter<TableModel>(tableModel);
		table.setRowSorter(sorter);

		scrollPane = new JideScrollPane(table);
		// setColumnWidths();

		add(scrollPane, BorderLayout.CENTER);
	}

	// sam panel bez filtróê i combobox'ów
	public SimpleTablePanel(TableModel tableModel, String title) {
		super(new BorderLayout());
		this.tableModel = tableModel;

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(0, 6, 6, 6)));

		table = new JTable(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		scrollPane = new JideScrollPane(table);
		// setColumnWidths();

		add(scrollPane, BorderLayout.CENTER);

	}

	public ListSelectionModel getSelectionModel() {
		return table.getSelectionModel();
	}

	public int getSelected() {
		int[] selected = table.getSelectedRows();

		if (selected == null || selected.length == 0)
			return -1;
		else {
			if (sorter != null) return sorter.convertRowIndexToModel(selected[0]);
			return selected[0];
		}

	}

	public void setModel(TableModel model) {
		tableModel = model;
		table = new JTable(model);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		scrollPane = new JideScrollPane(table);
		// setColumnWidths();

		add(scrollPane, BorderLayout.CENTER);
		filter = new DefaultSimpleFilterPanel(tableModel, hint, labelName);

		add(filter, BorderLayout.NORTH);
		setColumnWidths();
	}

	public abstract void setColumnWidths();

	public void refresh() {
		if (table != null) table.repaint();
		table.revalidate();
		SwingUtilities.updateComponentTreeUI(table);

	}

	public void runFilter(String text) {
		RowFilter<TableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter(text, filterPosition);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

	public void clearSelection() {
		table.getSelectionModel().removeSelectionInterval(table.getSelectionModel().getMinSelectionIndex(),
				table.getSelectionModel().getMaxSelectionIndex());
	}
}
