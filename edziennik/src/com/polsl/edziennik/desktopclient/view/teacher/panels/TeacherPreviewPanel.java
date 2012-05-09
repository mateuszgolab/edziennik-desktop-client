package com.polsl.edziennik.desktopclient.view.teacher.panels;

import com.polsl.edziennik.delegates.AdminDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.model.tables.TeacherTableModel;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class TeacherPreviewPanel extends TeacherSimplePreviewPanel {

	private AdminDelegate delegate;
	private Integer selected;
	private TeacherTableModel model;

	public TeacherPreviewPanel(String title, TeacherTableModel model) {
		super(title);
		selected = null;
		this.model = model;

		try {
			delegate = DelegateFactory.getAdminDelegate();
		} catch (DelegateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveTeacher() throws DelegateException {
		// zapis nowego Teachera
		if (currentTeacher == null) {
			currentTeacher = new TeacherDTO();
			setCurrentTeacher();
			currentTeacher = delegate.addTeacher(currentTeacher);
			model.add(currentTeacher);

		} else {
			setCurrentTeacher();
			delegate.updateTeacher(currentTeacher);
			model.update(currentTeacher, selected);

		}
	}

	public void setSelected(int index) {
		selected = index;
	}

}
