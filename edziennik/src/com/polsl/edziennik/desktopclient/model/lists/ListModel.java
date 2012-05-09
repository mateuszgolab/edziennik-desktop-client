package com.polsl.edziennik.desktopclient.model.lists;

import java.util.List;

import javax.swing.AbstractListModel;

public class ListModel<Entity> extends AbstractListModel {

	protected List<Entity> data;

	public ListModel(List<Entity> list) {
		data = list;
	}

	public ListModel() {
		data = null;
	}

	@Override
	public int getSize() {
		if (data != null) return data.size();
		return 0;
	}

	@Override
	public Object getElementAt(int index) {
		if (index > -1 && index < getSize()) {
			return data.get(index);
		}
		return null;
	}

	public void add(Entity e) {
		if (data != null) data.add(e);
	}

	public void delete(Entity e) {
		if (data != null) data.remove(e);
	}

	public void setModel(List<Entity> list) {
		data = list;
	}

}
