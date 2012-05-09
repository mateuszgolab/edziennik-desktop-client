package com.polsl.edziennik.desktopclient.model.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.properties.Properties;

public abstract class TableModel<Entity> extends AbstractTableModel {

	protected List<Entity> data;
	protected String[] columnNames = null;
	protected Class[] classNames = null;
	protected static ResourceBundle entity = LangManager.getResource(Properties.Entity);

	public TableModel() {

		setColumns();
		data = new ArrayList<Entity>();
	}

	public TableModel(List<Entity> entities) {

		setColumns();
		data = entities;
	}

	@Override
	public int getRowCount() {
		if (data == null) return 0;
		return data.size();
	}

	@Override
	public int getColumnCount() {
		if (columnNames == null) return 0;
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnNames != null && columnIndex > -1 && columnIndex < columnNames.length)
			return columnNames[columnIndex];
		else
			return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (classNames != null && columnIndex > -1 && columnIndex < classNames.length) {
			return classNames[columnIndex];
		} else {
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void setColumnNames(String[] names) {
		columnNames = names;
	}

	public void setData(List<Entity> entities) {
		data = entities;
		fireTableDataChanged();
	}

	public void add(Entity e) {
		if (e != null) {
			data.add(e);
			fireTableDataChanged();
		}
	}

	public void remove(Integer i) {
		if (i != null && i > -1 && i < data.size()) {
			data.remove(i.intValue());
		}
		fireTableDataChanged();
	}

	public void remove(Entity e) {
		if (data == null) return;
		data.remove(e);
		fireTableDataChanged();
	}

	public void update(Entity entity, Integer index) {

		if (entity != null && index != null && index > -1 && index < data.size()) {
			data.set(index, entity);
		}
		fireTableDataChanged();
	}

	public Entity get(int rowIndex) {
		if (rowIndex > -1 && rowIndex < data.size()) {
			return data.get(rowIndex);
		}
		return null;
	}

	public abstract void setColumns();

	public void setModel(List<Entity> entities) {
		data = entities;
		fireTableDataChanged();
	}

	public List<Entity> getData() {
		return data;
	}

	public String boolResult(boolean b) {
		if (b) return "tak";
		return "nie";
	}

	public boolean boolResult(String s) {
		if (s.compareTo("tak") == 0)
			return true;
		else
			return false;
	}

	public void add(List<Entity> list) {
		data.addAll(list);
		fireTableDataChanged();
	}

	public void remove(List<Entity> list) {
		data.removeAll(list);
		fireTableDataChanged();
	}

	public void removeIndexes(List<Integer> list) {
		for (Integer i : list)
			data.remove(i);
		fireTableDataChanged();
	}

	public void removeAll() {
		data.clear();
		fireTableDataChanged();

	}

}
