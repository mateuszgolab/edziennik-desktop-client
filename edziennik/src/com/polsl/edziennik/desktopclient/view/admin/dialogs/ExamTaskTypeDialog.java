package com.polsl.edziennik.desktopclient.view.admin.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.polsl.edziennik.delegates.AdminDelegate;
import com.polsl.edziennik.delegates.CommonDelegate;
import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.FrameToolkit;
import com.polsl.edziennik.desktopclient.controller.utils.LangManager;
import com.polsl.edziennik.desktopclient.model.tables.ExamTaskTypeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.dialogs.SimpleDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ExamTaskTypeButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.TablePanel;
import com.polsl.edziennik.modelDTO.exam.ExamTaskTypeDTO;

public class ExamTaskTypeDialog extends JDialog {

	private TablePanel listPanel;
	private ExamTaskTypeButtonPanel buttonPanel;
	private ExamTaskTypeTableModel model;
	public ResourceBundle bundle = LangManager.getResource(Properties.Component);
	public JPanel panel;
	private FrameToolkit frameToolkit = new FrameToolkit();
	private SimpleDialog dialog;
	private ExamTaskTypeDTO currentExamTaskType;
	private CommonDelegate delegate;

	public ExamTaskTypeDialog() {

		model = new ExamTaskTypeTableModel();
		DataProvider provider = new DataProvider();
		provider.execute();

		setTitle(bundle.getString("examTaskTypes"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(300, preferredSize.getHeight() / 2);
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("examTaskIcon"));
		setLocationRelativeTo(null);

		panel = new JPanel(new BorderLayout());

		buttonPanel = new ExamTaskTypeButtonPanel();

		listPanel = new TablePanel(model, "") {

			@Override
			public int getComboSelectedIndex() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void runFilter(Long min, Long max) {
				// TODO Auto-generated method stub

			}

			@Override
			public void runFilter(Long min, Long max, String s, String s2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void runFilter(Long min, Long max, String s) {
				// TODO Auto-generated method stub

			}
		};
		listPanel.getColumnModel().getColumn(0).setPreferredWidth(150);
		listPanel.getColumnModel().getColumn(1).setPreferredWidth(100);
		listPanel.getSelectionModel().addListSelectionListener(new ListListener());
		panel.add(listPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		dialog = new SimpleDialog("examTaskType", "taskWeight");

		buttonPanel.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.clear();
				switch (dialog.showDialog()) {
				case 0:
					currentExamTaskType = new ExamTaskTypeDTO();
					currentExamTaskType.setType(dialog.getText());
					currentExamTaskType.setWeight(dialog.getWeight());
					AddWorker worker = new AddWorker();
					worker.execute();
				}
			}
		});

		buttonPanel.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				DeleteWorker worker = new DeleteWorker();
				worker.execute();
			}
		});

		add(panel);
		setVisible(true);
		buttonPanel.activate(false);
		buttonPanel.activateAdd(true);

	}

	private class AddWorker extends SwingWorker<ExamTaskTypeDTO, Integer> {

		@Override
		protected ExamTaskTypeDTO doInBackground() {

			AdminDelegate admin;
			try {
				admin = DelegateFactory.getAdminDelegate();
				ExamTaskTypeDTO g = admin.addExamTaskType(currentExamTaskType);
				return g;
			} catch (DelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void done() {
			try {
				model.add(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private class DeleteWorker extends SwingWorker<Void, Integer> {

		@Override
		protected Void doInBackground() {

			AdminDelegate admin;
			try {
				admin = DelegateFactory.getAdminDelegate();
				admin.removeExamTaskType(currentExamTaskType.getId());
			} catch (DelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void done() {
			model.remove(currentExamTaskType);
			buttonPanel.activateRemove(false);
		}
	}

	private class ListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			currentExamTaskType = model.get(listPanel.getSelected());
			buttonPanel.activateRemove(true);

		}
	}

	private class DataProvider extends SwingWorker<List<ExamTaskTypeDTO>, Integer> {
		@Override
		protected List<ExamTaskTypeDTO> doInBackground() {

			try {
				delegate = DelegateFactory.getCommonDelegate();
				return delegate.getExamTaskTypes();
			} catch (DelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void done() {
			try {
				model.setModel(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
