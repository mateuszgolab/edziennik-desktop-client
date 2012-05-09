package com.polsl.edziennik.desktopclient.view;

import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

public final class EdziennikDesktop {
	private static volatile EdziennikDesktop instance = null;
	private static AdminMainView adminView = null;
	private static TeacherMainView teacherView = null;
	private static StudentMainView studentView = null;

	public static EdziennikDesktop getInstance() {
		if (instance == null) {
			synchronized (EdziennikDesktop.class) {
				if (instance == null) {
					instance = new EdziennikDesktop();
				}
			}
		}
		return instance;
	}

	private EdziennikDesktop() {
	}

	public void setAdminVisible(boolean value) {
		adminView.setVisible(value);
	}

	public void setTeacherVisible(boolean value) {
		teacherView.setVisible(value);
	}

	public void setStudentVisible(boolean value) {
		studentView.setVisible(value);
	}

	public TeacherMainView getTeacherParent() {
		if (teacherView != null && teacherView.isVisible()) return teacherView;
		return null;
	}

	public AdminMainView getAdminParent() {
		if (adminView != null && adminView.isVisible()) return adminView;
		return null;
	}

	public StudentMainView getStudentParent() {
		if (studentView != null && studentView.isVisible()) return studentView;
		return null;
	}

	public void createAdmin() {
		adminView = new AdminMainView();
		setAdminVisible(true);
	}

	public void createTeacher() {
		teacherView = new TeacherMainView();
		setTeacherVisible(true);
	}

	public void createStudent() {
		studentView = new StudentMainView();
		setStudentVisible(true);
	}

	public void hide() {
		if (adminView != null) setAdminVisible(false);
		if (teacherView != null) setTeacherVisible(false);
		if (studentView != null) setStudentVisible(false);
	}

}
