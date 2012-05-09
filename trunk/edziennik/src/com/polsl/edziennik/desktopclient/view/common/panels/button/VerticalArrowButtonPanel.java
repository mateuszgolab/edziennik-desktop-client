package com.polsl.edziennik.desktopclient.view.common.panels.button;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.polsl.edziennik.desktopclient.controller.utils.factory.GuiComponentFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.IGuiComponentAbstractFactory;
import com.polsl.edziennik.desktopclient.controller.utils.factory.interfaces.IButton;

public class VerticalArrowButtonPanel extends JPanel {

	private JButton left;
	private JButton allLeft;
	private JButton right;
	private JButton allRight;
	private IGuiComponentAbstractFactory factory = GuiComponentFactory.getInstance();
	private IButton button;
	private CellConstraints cc;

	public VerticalArrowButtonPanel() {

		FormLayout layout = new FormLayout("pref, min",
				"center:pref, 100dlu, pref, 2dlu, pref,2dlu, pref, 2dlu,pref, 2dlu, pref, 2dlu, pref,2dlu, pref, 2dlu pref");

		setLayout(layout);

		button = factory.createIconButton();

		left = button.getButton("leftIcon", "replaceStudentToHint");
		allLeft = button.getButton("allLeftIcon", "replaceAllStudentsToHint");
		right = button.getButton("rightIcon", "replaceStudentFromHint");
		allRight = button.getButton("allRightIcon", "replaceAllStudentsFromHint");

		cc = new CellConstraints();
		add(left, cc.xy(1, 3, "center, center"));
		add(allLeft, cc.xy(1, 5, "center, center"));
		add(allRight, cc.xy(1, 7, "center, center"));
		add(right, cc.xy(1, 9, "center, center"));

	}

	public JButton getLeft() {
		return left;
	}

	public JButton getAllLeft() {
		return allLeft;
	}

	public JButton getRight() {
		return right;
	}

	public JButton getAllRight() {
		return allRight;
	}

	@Override
	public void setEnabled(boolean b) {
		left.setEnabled(b);
		right.setEnabled(b);
	}
}
