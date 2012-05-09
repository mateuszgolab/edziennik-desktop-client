package com.polsl.edziennik.desktopclient.view.teacher.menu;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.polsl.edziennik.desktopclient.controller.LogoutAction;
import com.polsl.edziennik.desktopclient.controller.student.activities.ExerciseTopicSimpleAction;
import com.polsl.edziennik.desktopclient.controller.student.subject.SubjectRulesAction;
import com.polsl.edziennik.desktopclient.controller.student.teachers.TeachersOverviewAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.ActivitiesAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.HappyHoursAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.LabActivitiesAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.ProjectAction;
import com.polsl.edziennik.desktopclient.controller.teacher.activities.ReportsAction;
import com.polsl.edziennik.desktopclient.controller.teacher.exams.ExamBrowseAction;
import com.polsl.edziennik.desktopclient.controller.teacher.groups.GroupsOverviewAction;
import com.polsl.edziennik.desktopclient.controller.teacher.students.StudentManagementAction;
import com.polsl.edziennik.desktopclient.view.student.menu.StudentIconsPanel;
import com.polsl.edziennik.desktopclient.view.teacher.TeacherMainView;

public class TeacherIconsPanel extends StudentIconsPanel {

	protected JButton students;
	protected JButton projects;

	public TeacherIconsPanel() {
		super();
	}

	public TeacherIconsPanel(TeacherMainView parent) {

		super();

		create();
		init();
		addActions(parent);
		addLogout();

	}

	@Override
	public void create() {
		super.create();
		students = button.getButton("StudentsBigIcon", "studentsHint");
		projects = button.getButton("projectBigIcon", "projectHint");

	}

	@Override
	public void init() {
		add(students);
		add(teachers);
		add(groups);
		add(activities);
		add(labActivities);
		add(exerciseTopics);
		add(projects);
		add(reports);
		add(examBrowse);
		add(happyHours);
		add(subjectInfo);

	}

	@Override
	public void addLogout() {
		JButton logout = new JButton(new ImageIcon(bundle.getString("LogoutBigIcon")));
		logout.setToolTipText(bundle.getString("logoutHint"));
		LogoutAction logoutAction = new LogoutAction();
		logout.addActionListener(logoutAction);
		add(logout);
	}

	public void addActions(TeacherMainView parent) {
		students.addActionListener(new StudentManagementAction(parent));
		teachers.addActionListener(new TeachersOverviewAction(parent));
		activities.addActionListener(new ActivitiesAction(parent));
		labActivities.addActionListener(new LabActivitiesAction(parent));
		subjectInfo.addActionListener(new SubjectRulesAction(parent));
		groups.addActionListener(new GroupsOverviewAction(parent));
		exerciseTopics.addActionListener(new ExerciseTopicSimpleAction(parent));
		reports.addActionListener(new ReportsAction(parent));
		happyHours.addActionListener(new HappyHoursAction(parent));
		projects.addActionListener(new ProjectAction(parent));
		examBrowse.addActionListener(new ExamBrowseAction(parent));

	}

}
