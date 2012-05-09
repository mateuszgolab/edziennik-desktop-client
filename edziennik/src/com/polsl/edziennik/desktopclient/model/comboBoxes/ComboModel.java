package com.polsl.edziennik.desktopclient.model.comboBoxes;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

public class ComboModel<Entity> implements ComboBoxModel { // extends
															// DefaultComboBoxModel{//
	protected List<Entity> data;
	private List<Entity> tmp;
	protected Entity selectedItem;

	public ComboModel() {
		data = new ArrayList<Entity>();
		selectedItem = null;
	}

	public ComboModel(List<Entity> entities) {

		if (entities != null) {
			data = entities;
			selectedItem = entities.get(0);
		} else {
			data = new ArrayList<Entity>();
			selectedItem = null;
		}
	}

	@Override
	public int getSize() {
		if (data == null) return 0;
		return data.size();
	}

	@Override
	public void setSelectedItem(Object anItem) {

		selectedItem = (Entity) anItem;
	}

	@Override
	public Entity getSelectedItem() {
		return selectedItem;
	}

	public void add(Entity g) {
		if (data == null) return;
		data.add(g);
	}

	public void remove(Entity g) {
		if (data == null) return;
		data.remove(g);
	}

	public void setModel(List<Entity> entities) {
		if (entities != null && entities.size() > 0) {
			data = entities;
			selectedItem = entities.get(0);
		} else {
			data = new ArrayList<Entity>();
			selectedItem = null;
		}

		// fireContentsChanged(data, 0, data.size()-1);

	}

	@Override
	public Entity getElementAt(int index) {
		if (index >= 0 && index < data.size()) return data.get(index);
		return null;
	}

	public void setEditable(boolean b) {
		if (b) {
			if (tmp != null) data = tmp;
		} else {
			tmp = new ArrayList<Entity>(data);
			data.clear();
			data.add(selectedItem);
		}
	}

	public void removeAll() {
		data.clear();
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

}