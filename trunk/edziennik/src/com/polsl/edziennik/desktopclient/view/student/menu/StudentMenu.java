package com.polsl.edziennik.desktopclient.view.student.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.polsl.edziennik.desktopclient.controller.student.SummaryAction;
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
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IMenu;
import com.polsl.edziennik.desktopclient.view.student.StudentMainView;

public class StudentMenu extends JMenuBar {
	protected IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	protected JMenu activities;
	protected JMenu teachers;
	protected JMenu groups;
	protected JMenu subject;
	protected JMenu exams;
	protected IMenu menu;
	protected JMenu students;
	protected JMenu profile;

	public StudentMenu() {
		menu = factory.createMenu();
	}

	public StudentMenu(StudentMainView parent) {
		menu = factory.createMenu();
		create();
		init();
		addActions(parent);
	}

	public void create() {
		activities = menu.getMenu("Activities", "ActivitiesIcon");
		teachers = menu.getMenu("Teachers", "TeachersIcon");
		groups = menu.getMenu("Groups", "GroupsIcon");
		subject = menu.getMenu("Subject", "SubjectIcon");
		exams = menu.getMenu("Exams", "ExamsIcon");
		students = menu.getMenu("summary", "StudentsIcon");
		// profile = menu.getMenu("Profile", "profileIcon");

	}

	public void init() {
		add(activities);
		add(teachers);
		add(groups);
		add(subject);
		add(exams);
		add(students);
		// add(profile);
	}

	public void addActions(StudentMainView parent) {

		teachers.add(new TeachersOverviewAction(parent));
		activities.add(new ExerciseTopicSimpleAction(parent));
		activities.add(new ProjectActivitiesBrowseAction(parent));
		subject.add(new SubjectRulesAction(parent));
		activities.add(new HappyHoursAction(parent));
		activities.add(new ReportsAction(parent));
		groups.add(new GroupsOverviewAction(parent));
		activities.add(new ActivitiesAction(parent));
		activities.add(new LabActivitiesAction(parent));
		students.add(new SummaryAction(parent));
		exams.add(new StudentExamsAction(parent));

		// activities.add(new ExerciseTopicAction(parent));
	}

}
