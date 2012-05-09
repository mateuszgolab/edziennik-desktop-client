package com.polsl.edziennik.desktopclient.view.admin.panels.preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.AttendanceTableModel;
import com.polsl.edziennik.desktopclient.model.tables.ProjectActivityTableModel;
import com.polsl.edziennik.desktopclient.model.tables.RegularGradeTableModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentGradesTableModel;
import com.polsl.edziennik.desktopclient.view.common.dialogs.AttendancesDialog;
import com.polsl.edziennik.desktopclient.view.common.dialogs.GradesSheetDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ProjectActivityPreviewButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.ProjectActivityPreviewPanel;
import com.polsl.edziennik.modelDTO.activity.ProjectActivityDTO;
import com.polsl.edziennik.modelDTO.attendance.AttendanceDTO;
import com.polsl.edziennik.modelDTO.grade.GradeTypeDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;

public class AdminProjectActivityPreviewPanel extends ProjectActivityPreviewPanel
{
    
    private AttendancesDialog attendancesDialog;
    private GradesSheetDialog gradesDialog;
    private RegularGradeTableModel regularGradeTableModel;
    private ProjectActivityPreviewButtonPanel buttonPanel;
    private StudentGradesTableModel studentGradesTableModel;
    private AttendanceTableModel attendanceModel;
    
    public AdminProjectActivityPreviewPanel(String title, ProjectActivityTableModel model)
    {
        super(title, model);
        setEnabled(false);
    }
    
    @Override
    public void create()
    {
        super.create();
        studentGradesTableModel = new StudentGradesTableModel();
        regularGradeTableModel = new RegularGradeTableModel();
        buttonPanel = new ProjectActivityPreviewButtonPanel();
        
        buttonPanel.getAttendancesButton().addActionListener(new AttendancesListener());
        buttonPanel.getGradesButton().addActionListener(new GradesListener());
        
        regularGradeTableModel = new RegularGradeTableModel();
        attendanceModel = new AttendanceTableModel();
        
        attendancesDialog = new AttendancesDialog(attendanceModel);
        attendancesDialog.getCancelButton().addActionListener(new CancelListener());
        attendancesDialog.getSave().addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                UpdateAttendancesWorker uaw = new UpdateAttendancesWorker();
                uaw.execute();
                uaw.startProgress();
            }
        });
        
        gradesDialog = new GradesSheetDialog(regularGradeTableModel, "grade");
        gradesDialog.getExitButton().addActionListener(new CancelListener());
        gradesDialog.getSaveButton().addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                new AddGradesSheetWorker().execute();
                
            }
        });
        
    }
    
    @Override
    public void setComponents()
    {
        super.setComponents();
        add(buttonPanel, cc.xyw(1, 21, 8));
    }
    
    @Override
    public void setEnabled(boolean b)
    {
        super.setEnabled(b);
        buttonPanel.activate(b);
    }
    
    @Override
    public void save()
    {
        try
        {
            // zapis nowego ProjectActivity
            if (currentActivity == null)
            {
                currentActivity = new ProjectActivityDTO();
                setData();
                currentActivity = DelegateFactory.getTeacherDelegate().addProjectActivity(currentActivity);
                model.add(currentActivity);
                
            }
            else
            {
                setData();
                model.update(currentActivity, selected);
                // DelegateFactory.getTeacherDelegate().updateProjectActivity(currentActivity);
                
            }
        }
        catch (DelegateException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private class AttendancesListener implements ActionListener
    {
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (currentActivity != null && currentActivity.getId() != null)
            {
                new AttendancesProvider(projectId).execute();
                
            }
            else
                attendancesDialog.setVisible(true);
            
        }
    }
    
    private class GradesListener implements ActionListener
    {
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            
            if (currentActivity != null && currentActivity.getId() != null)
            {
                new GradeTypesProvider().execute();
                new GradesProvider().execute();
                
            }
            else
                gradesDialog.setVisible(true);
        }
        
    }
    
    private class CancelListener implements ActionListener
    {
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (attendancesDialog != null)
                attendancesDialog.dispose();
            if (gradesDialog != null)
                gradesDialog.dispose();
            
        }
    };
    
    private class UpdateAttendancesWorker extends Worker<Void>
    {
        
        public UpdateAttendancesWorker()
        {
            super("save");
        }
        
        @Override
        protected Void doInBackground() throws Exception
        {
            startProgress();
            DelegateFactory.getTeacherDelegate().updateAttendances(attendanceModel.getData());
            return null;
        }
        
        @Override
        public void done()
        {
            stopProgress();
        }
        
    }
    
    private class GradesProvider extends Worker<List<StudentDTO>>
    {
        
        @Override
        protected List<StudentDTO> doInBackground() throws Exception
        {
            startProgress();
            
            return DelegateFactory.getTeacherDelegate().getStudentsWithGradesFromActivity(currentActivity.getId());
        }
        
        @Override
        public void done()
        {
            try
            {
                gradesDialog.setModel(get());
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // gradesDialog.setActivity(currentActivity.getId());
            gradesDialog.setVisible(true);
            stopProgress();
        }
        
    }
    
    @Override
    public void delete()
    {
        // TODO Auto-generated method stub
        
    }
    
    private class AttendancesProvider extends Worker<List<AttendanceDTO>>
    {
        
        public AttendancesProvider(Integer projectId)
        {
            super();
        }
        
        @Override
        protected List<AttendanceDTO> doInBackground() throws Exception
        {
            startProgress();
            return DelegateFactory.getCommonDelegate().getProjectActivityAttendances(currentActivity.getId());
        }
        
        @Override
        public void done()
        {
            try
            {
                attendanceModel.setModel(get());
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            attendancesDialog.setVisible(true);
            stopProgress();
            
        }
        
    }
    
    private class AddGradesSheetWorker extends Worker<Void>
    {
        
        public AddGradesSheetWorker()
        {
            super("save");
        }
        
        @Override
        protected Void doInBackground() throws Exception
        {
            startProgress();
            DelegateFactory.getTeacherDelegate().addRegularGrades(gradesDialog.getMap(), currentActivity.getId(),
                    gradesDialog.getTypeData(), gradesDialog.getDescription());
            
            return null;
        }
        
        @Override
        public void done()
        {
            gradesDialog.clearSelection();
            stopProgress();
            
        }
    }
    
    private class GradeTypesProvider extends Worker<List<GradeTypeDTO>>
    {
        
        @Override
        protected List<GradeTypeDTO> doInBackground() throws Exception
        {
            startProgress();
            return DelegateFactory.getCommonDelegate().getGradeTypes();
        }
        
        @Override
        public void done()
        {
            try
            {
                gradesDialog.setComboModel(get());
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            stopProgress();
            
        }
        
    }
}
