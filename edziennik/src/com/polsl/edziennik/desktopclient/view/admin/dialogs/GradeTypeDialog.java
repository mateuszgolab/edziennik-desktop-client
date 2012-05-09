package com.polsl.edziennik.desktopclient.view.admin.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
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
import com.polsl.edziennik.desktopclient.model.tables.GradeTypeTableModel;
import com.polsl.edziennik.desktopclient.properties.Properties;
import com.polsl.edziennik.desktopclient.view.common.dialogs.SimpleDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.button.GradeTypesButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.tablePanels.TablePanel;
import com.polsl.edziennik.modelDTO.EBasicGradeTypes;
import com.polsl.edziennik.modelDTO.grade.GradeTypeDTO;

public class GradeTypeDialog extends JDialog {

	private TablePanel listPanel;
	private GradeTypesButtonPanel buttonPanel;
	private GradeTypeTableModel model;
	public ResourceBundle bundle = LangManager.getResource(Properties.Component);
	public JPanel panel;
	private FrameToolkit frameToolkit = new FrameToolkit();
	private SimpleDialog dialog;
	private GradeTypeDTO currentGradeType;
	private CommonDelegate delegate;

	public GradeTypeDialog() {

		model = new GradeTypeTableModel();
		DataProvider provider = new DataProvider();
		provider.execute();

		setTitle(bundle.getString("gradeTypes"));
		Dimension preferredSize = frameToolkit.getSize();
		preferredSize.setSize(200, preferredSize.getHeight() / 2);
		setSize(preferredSize);
		setIconImage(frameToolkit.getTitleIcon("GradesIcon"));
		setLocationRelativeTo(null);

		panel = new JPanel(new BorderLayout());

		buttonPanel = new GradeTypesButtonPanel();

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
		listPanel.getSelectionModel().addListSelectionListener(new ListListener());
		panel.add(listPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		dialog = new SimpleDialog("gradeType");

		buttonPanel.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.clear();
				switch (dialog.showDialog()) {
				case 0:

					for (EBasicGradeTypes type : EBasicGradeTypes.values()) {
						if (type.getGradeType().equals(dialog.getText())) {
							JOptionPane.showMessageDialog(null, bundle.getString("predefGradeTypesAdding"));
							return;
						}
					}

					currentGradeType = new GradeTypeDTO();
					currentGradeType.setType(dialog.getText());
					AddWorker worker = new AddWorker();
					worker.execute();
				}
			}
		});

		buttonPanel.getRemoveButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (EBasicGradeTypes type : EBasicGradeTypes.values()) {
					if (type.getGradeType().equals(currentGradeType.getType())) {
						JOptionPane.showMessageDialog(null, bundle.getString("predefGradeTypesRemoving"));
						return;
					}
				}

				DeleteWorker worker = new DeleteWorker();
				worker.execute();
			}
		});

		buttonPanel.activate(false);
		buttonPanel.activateAdd(true);

		add(panel);
		setVisible(true);

	}

	private class AddWorker extends SwingWorker<GradeTypeDTO, Integer> {

		@Override
		protected GradeTypeDTO doInBackground() {

			AdminDelegate admin;
			try {
				admin = DelegateFactory.getAdminDelegate();
				GradeTypeDTO g = admin.addGradeType(currentGradeType);
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
				admin.removeGradeType(currentGradeType.getId());
			} catch (DelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void done() {
			model.remove(currentGradeType);
			buttonPanel.activateRemove(false);
		}
	}

	private class ListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			currentGradeType = model.get(listPanel.getSelected());
			buttonPanel.activate(true);

		}
	}

	private class DataProvider extends SwingWorker<List<GradeTypeDTO>, Integer> {
		@Override
		protected List<GradeTypeDTO> doInBackground() {

			try {
				delegate = DelegateFactory.getCommonDelegate();
				return delegate.getGradeTypes();
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
