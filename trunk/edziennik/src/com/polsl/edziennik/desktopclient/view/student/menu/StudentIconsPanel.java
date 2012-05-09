package com.polsl.edziennik.desktopclient.view.student.menu;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.polsl.edziennik.desktopclient.controller.LogoutAction;
import com.polsl.edziennik.desktopclient.controller.student.activities.ActivitiesAction;
import com.polsl.edziennik.desktopclient.controller.student.activities.ExerciseTopicSimpleAction;
import com.polsl.edziennik.desktopclient.controller.student.activities.HappyHoursAction;
import com.polsl.edziennik.desktopclient.controller.student.activities.LabActivitiesAction;
import com.polsl.edziennik.desktopclient.controller.student.activities.ProjectActivitiesBrowseAction;
import com.polsl.edziennik.desktopclient.controller.student.activities.ReportsAction;
import com.polsl.edziennik.desktopclient.controller.student.exams.StudentExamsAction;
import com.polsl.edziennik.desktopclient.controller.student.groups.GroupsOverviewAction;
import com.polsl.edziennik.desktopclient.controller.student.subject.SubjectRulesAction;
import com.polsl.edziennik.desktopclient.controller.student.teachers.TeachersOverviewAction;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

public class StudentIconsPanel extends JPanel {

	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected ResourceBundle bundle = LangManager.getInstance().getResource(Properties.Component);
	protected JButton teachers;
	protected JButton activities;
	protected JButton labActivities;
	protected JButton projectActivities;
	protected JButton reports;
	protected JButton examBrowse;
	protected JButton subjectInfo;
	protected JButton groups;
	protected JButton happyHours;
	protected JButton exerciseTopics;
	protected IButton button;

	public StudentIconsPanel() {
		button = factory.createIconButton();
	}

	public StudentIconsPanel(StudentMainView parent) {

		button = factory.createIconButton();

		create();
		init();
		addActions(parent);
		addLogout();

	}

	public void create() {
		activities = button.getButton("exerciseBigIcon", "ActivitiesHint");
		labActivities = button.getButton("labActivitiesBigIcon", "labActivitiesHint");
		projectActivities = button.getButton("projectBigIcon", "projectActivitiesNameHint");
		reports = button.getButton("reportBigIcon", "reportHint");
		teachers = button.getButton("TeachersBigIcon", "teachersHint");
		groups = button.getButton("GroupsBigIcon", "groupsHint");
		examBrowse = button.getButton("ExamsBigIcon", "examsHint");
		subjectInfo = button.getButton("SubjectBigIcon", "subjectHint");
		happyHours = button.getButton("happyHoursBigIcon", "happyHoursHint");
		exerciseTopics = button.getButton("labTopicBigIcon", "exerciseHint");
	}

	public void init() {

		add(teachers);
		add(groups);
		add(activities);
		add(labActivities);
		add(projectActivities);
		add(exerciseTopics);
		add(reports);
		add(examBrowse);
		add(happyHours);
		add(subjectInfo);
	}

	public void addLogout() {
		JButton logout = new JButton(new ImageIcon(bundle.getString("LogoutBigIcon")));
		logout.setToolTipText(bundle.getString("logoutHint"));
		LogoutAction logoutAction = new LogoutAction();
		logout.addActionListener(logoutAction);
		add(logout);
	}

	public void addActions(StudentMainView parent) {
		// projects.addActionListener(new
		// ProjectActivitiesBrowseAction(parent));
		// studentsManage.addActionListener(new StudentBrowseAction(parent));
		teachers.addActionListener(new TeachersOverviewAction(parent));
		activities.addActionListener(new ActivitiesAction(parent));
		labActivities.addActionListener(new LabActivitiesAction(parent));
		subjectInfo.addActionListener(new SubjectRulesAction(parent));
		groups.addActionListener(new GroupsOverviewAction(parent));
		exerciseTopics.addActionListener(new ExerciseTopicSimpleAction(parent));
		reports.addActionListener(new ReportsAction(parent));
		examBrowse.addActionListener(new StudentExamsAction(parent));
		happyHours.addActionListener(new HappyHoursAction(parent));
		projectActivities.addActionListener(new ProjectActivitiesBrowseAction(parent));
	}

}
