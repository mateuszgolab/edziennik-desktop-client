package com.polsl.edziennik.desktopclient.view.admin.menu;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.polsl.edziennik.desktopclient.controller.LogoutAction;
import com.polsl.edziennik.desktopclient.controller.admin.activities.ActivitiesAction;
import com.polsl.edziennik.desktopclient.controller.admin.activities.ExerciseTopicAction;
import com.polsl.edziennik.desktopclient.controller.admin.activities.HappyHoursManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.activities.LabActivitiesAction;
import com.polsl.edziennik.desktopclient.controller.admin.activities.ProjectManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.activities.ReportsAction;
import com.polsl.edziennik.desktopclient.controller.admin.exams.ExamsManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.groups.GroupsManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.students.StudentManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.subject.SubjectRulesManagementAction;
import com.polsl.edziennik.desktopclient.controller.admin.teachers.TeacherManagementAction;
import com.polsl.edziennik.desktopclient.view.admin.AdminMainView;
import com.polsl.edziennik.desktopclient.view.teacher.menu.TeacherIconsPanel;

public class AdminIconsPanel extends TeacherIconsPanel {

	// private JLabel test;

	public AdminIconsPanel(AdminMainView parent) {
		super();
		create();
		init();
		addActions(parent);
		addLogout();
	}

	@Override
	public void create() {
		super.create();
		exerciseTopics = button.getButton("labTopicBigIcon", "exerciseHint");
		// test = new JLabel(new
		// ImageIcon(bundle.getString("StudentsBigIcon")));
		// test.setToolTipText(bundle.getString("studentsHint"));
	}

	@Override
	public void init() {
		super.init();
		// add(test);

	}

	@Override
	public void addLogout() {
		JButton logout = new JButton(new ImageIcon(bundle.getString("LogoutBigIcon")));
		logout.setToolTipText(bundle.getString("logoutHint"));
		LogoutAction logoutAction = new LogoutAction();
		logout.addActionListener(logoutAction);
		add(logout);
	}

	public void addActions(final AdminMainView parent) {
		students.addActionListener(new StudentManagementAction(parent));
		teachers.addActionListener(new TeacherManagementAction(parent));
		activities.addActionListener(new ActivitiesAction(parent));
		labActivities.addActionListener(new LabActivitiesAction(parent));
		exerciseTopics.addActionListener(new ExerciseTopicAction(parent));
		projects.addActionListener(new ProjectManagementAction(parent));
		groups.addActionListener(new GroupsManagementAction(parent));
		subjectInfo.addActionListener(new SubjectRulesManagementAction(parent));
		reports.addActionListener(new ReportsAction(parent));
		happyHours.addActionListener(new HappyHoursManagementAction(parent));
		examBrowse.addActionListener(new ExamsManagementAction(parent));
		// test.addMouseListener(new MouseListener() {
		//
		// protected ResourceBundle menu =
		// LangManager.getResource(Properties.Menu);
		//
		// @Override
		// public void mouseReleased(MouseEvent e) {
		//
		// }
		//
		// @Override
		// public void mousePressed(MouseEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void mouseExited(MouseEvent e) {
		// test.setIcon(new ImageIcon(bundle.getString("StudentsBigIcon")));
		//
		// }
		//
		// @Override
		// public void mouseEntered(MouseEvent e) {
		// test.setIcon(new ImageIcon(bundle.getString("GroupsBigIcon")));
		//
		// }
		//
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// // TODO Auto-generated method stub
		// StudentsManagement studenciPanel = new StudentsManagement();
		// parent.addTab(menu.getString("manageStudents") + "  ", studenciPanel,
		// new ImageIcon(bundle.getString("StudentsIcon")));
		// parent.invalidate();
		// parent.validate();
		//
		// }
		// });

	}
}