package com.polsl.edziennik.desktopclient.view.admin.panels.preview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.polsl.edziennik.delegates.DelegateFactory;
import com.polsl.edziennik.delegates.TeacherDelegate;
import com.polsl.edziennik.delegates.exceptions.DelegateException;
import com.polsl.edziennik.desktopclient.controller.utils.workers.Worker;
import com.polsl.edziennik.desktopclient.model.tables.AttendanceTableModel;
import com.polsl.edziennik.desktopclient.model.tables.LabActivityTableModel;
import com.polsl.edziennik.desktopclient.model.tables.RegularGradeTableModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentGradesTableModel;
import com.polsl.edziennik.desktopclient.model.tables.StudentTableModel;
import com.polsl.edziennik.desktopclient.view.common.dialogs.AttendancesDialog;
import com.polsl.edziennik.desktopclient.view.common.dialogs.GradesDialog;
import com.polsl.edziennik.desktopclient.view.common.dialogs.GradesSheetDialog;
import com.polsl.edziennik.desktopclient.view.common.dialogs.StudentDualListDialog;
import com.polsl.edziennik.desktopclient.view.common.panels.button.ActivityPreviewButtonPanel;
import com.polsl.edziennik.desktopclient.view.common.panels.preview.LabActivityPreviewPanel;
import com.polsl.edziennik.modelDTO.activity.LabActivityDTO;
import com.polsl.edziennik.modelDTO.attendance.AttendanceDTO;
import com.polsl.edziennik.modelDTO.exercise.ExerciseTopicDTO;
import com.polsl.edziennik.modelDTO.grade.GradeTypeDTO;
import com.polsl.edziennik.modelDTO.group.GroupDTO;
import com.polsl.edziennik.modelDTO.person.StudentDTO;
import com.polsl.edziennik.modelDTO.person.TeacherDTO;

public class AdminLabActivityPreviewPanel extends LabActivityPreviewPanel
{
    
    private AttendancesDialog attendancesDialog;
    private GradesSheetDialog gradesSheetDialog;
    private StudentDualListDialog studentsDialog;
    private GradesDialog gradesDialog;
    private ActivityPreviewButtonPanel buttonPanel;
    private RegularGradeTableModel regularGradeTableModel;
    private StudentTableModel studentTableModel;
    protected LabActivityTableModel model;
    private TeacherDelegate delegate;
    private AttendanceTableModel attendanceModel;
    private int counter;
    private StudentGradesTableModel gradesTableModel;
    
    public AdminLabActivityPreviewPanel(String title, LabActivityTableModel model)
    {
        super(title);
        this.model = model;
        
        setEditable(true);
    }
    
    @Override
    public void create()
    {
        super.create();
        buttonPanel = new ActivityPreviewButtonPanel();
        
        buttonPanel.getStudentsButton().addActionListener(new StudentsDualListListener());
        buttonPanel.getAttendancesButton().addActionListener(new AttendancesListener());
        buttonPanel.getAddGradesSheetButton().addActionListener(new AddGradesSheetListener());
        buttonPanel.getGradesButton().addActionListener(new GradesListener());
        
        regularGradeTableModel = new RegularGradeTableModel();
        studentTableModel = new StudentTableModel();
        gradesTableModel = new StudentGradesTableModel();
        attendanceModel = new AttendanceTableModel();
        
        gradesSheetDialog = new GradesSheetDialog(regularGradeTableModel, "GradesSheet");
        gradesSheetDialog.getExitButton().addActionListener(new CancelListener());
        gradesSheetDialog.getSaveButton().addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                AddGradesSheetWorker w = new AddGradesSheetWorker();
                w.execute();
                w.startProgress();
            }
        });
        
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
        
        gradesDialog = new GradesDialog(gradesTableModel);
        gradesDialog.getExButton().addActionListener(new CancelListener());
        
        studentsDialog = new StudentDualListDialog(studentTableModel);
        studentsDialog.getCancelButton().addActionListener(new CancelListener());
        studentsDialog.getSaveButton().addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                UpdateStudentsWorker uw = new UpdateStudentsWorker(studentsDialog.getSelectedIds());
                uw.execute();
                uw.startProgress();
                
            }
        });
    }
    
    @Override
    public void setComponents()
    {
        super.setComponents();
        add(buttonPanel, cc.xyw(1, 23, 10));
    }
    
    @Override
    public void setEnabled(boolean b)
    {
        super.setEnabled(b);
        buttonPanel.activate(b);
    }
    
    private class AttendancesListener implements ActionListener
    {
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (currentLabActivity != null && currentLabActivity.getId() != null)
            {
                AttendancesProvider ap = new AttendancesProvider();
                ap.execute();
                ap.startProgress();
            }
            else
                attendancesDialog.setVisible(true);
            
        }
    }
    
    private class AddGradesSheetListener implements ActionListener
    {
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            
            if (currentLabActivity != null && currentLabActivity.getId() != null)
            {
                
                setCounter(0);
                
                GradeTypesProvider gtp = new GradeTypesProvider();
                gtp.execute();
                gtp.startProgress();
                
                SelectedStudentsProvider ssp = new SelectedStudentsProvider(currentLabActivity.getId(), false);
                ssp.execute();
                ssp.startProgress();
                
            }
            else
                gradesSheetDialog.setVisible(true);
            
        }
        
    }
    
    private class StudentsDualListListener implements ActionListener
    {
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            
            if (currentLabActivity != null && currentLabActivity.getId() != null)
            {
                setCounter(0);
                SelectedStudentsProvider ssp = new SelectedStudentsProvider(currentLabActivity.getId());
                ssp.execute();
                ssp.startProgress();
                
                NotSelectedStudentsProvider nssp = new NotSelectedStudentsProvider(currentLabActivity.getId());
                nssp.execute();
                nssp.startProgress();
            }
            else
                studentsDialog.setVisible(true);
            
        }
        
    }
    
    private class GradesListener implements ActionListener
    {
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            
            if (currentLabActivity != null && currentLabActivity.getId() != null)
            {
                GradesProvider gp = new GradesProvider();
                gp.execute();
                gp.startProgress();
                
            }
            else
                gradesDialog.setVisible(true);
        }
        
    }
    
    @Override
    public void save()
    {
        try
        {
            delegate = DelegateFactory.getTeacherDelegate();
            
            // zapis nowego LabActivitya
            if (currentLabActivity == null)
            {
                currentLabActivity = new LabActivityDTO();
                setLabActivityData();
                currentLabActivity = delegate.addActivity(currentLabActivity);
                model.add(currentLabActivity);
                
            }
            else
            {
                setLabActivityData();
                delegate.updateLabActivity(currentLabActivity);
                model.update(currentLabActivity, selected);
                
            }
        }
        catch (DelegateException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public void delete()
    {
        if (model.get(selected) != null)
            try
            {
                delegate = DelegateFactory.getTeacherDelegate();
                if (delegate != null)
                {
                    delegate.deleteLabActivity((model.get(selected)).getId());
                }
            }
            catch (DelegateException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        model.remove(selected);
    }
    
    public synchronized void setCounter(int val)
    {
        counter = val;
    }
    
    public synchronized int getCounter()
    {
        return counter;
    }
    
    public synchronized void incCounter()
    {
        counter++;
    }
    
    public void setGroupComboModel(List<GroupDTO> groups)
    {
        studentsDialog.setComboModel(groups);
    }
    
    public void setTeacherModel(List<TeacherDTO> teachers)
    {
        teacherComboModel.setModel(teachers);
        teacherComboBox.setSelectedItem(null);
        teacherComboBox.updateUI();
    }
    
    public void setGroupModel(List<GroupDTO> groups)
    {
        groupComboModel.setModel(groups);
        groupComboBox.setSelectedItem(null);
        groupComboBox.updateUI();
    }
    
    public void setExerciseTopicModel(List<ExerciseTopicDTO> exerciseTopics)
    {
        exerciseTopicComboModel.setModel(exerciseTopics);
        exerciseTopicComboBox.setSelectedItem(null);
        exerciseTopicComboBox.updateUI();
    }
    
    private class AttendancesProvider extends Worker<List<AttendanceDTO>>
    {
        
        public AttendancesProvider()
        {
            super("get");
        }
        
        @Override
        protected List<AttendanceDTO> doInBackground() throws Exception
        {
            delegate = DelegateFactory.getTeacherDelegate();
            return delegate.getAttendancesByActivity(currentLabActivity.getId());
        }
        
        @Override
        public void done()
        {
            stopProgress();
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
        }
        
    }
    
    private class SelectedStudentsProvider extends Worker<List<StudentDTO>>
    {
        
        private Integer id;
        private boolean b = true;
        
        public SelectedStudentsProvider(Integer id)
        {
            super("get");
            this.id = id;
        }
        
        public SelectedStudentsProvider(Integer id, boolean b)
        {
            super("get");
            this.id = id;
            this.b = b;
        }
        
        @Override
        protected List<StudentDTO> doInBackground() throws Exception
        {
            delegate = DelegateFactory.getTeacherDelegate();
            return delegate.getStudentsByActivity(id);
        }
        
        @Override
        public void done()
        {
            stopProgress();
            
            if (b)
            {
                
                try
                {
                    studentsDialog.setSelectedModel(get());
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
                
                incCounter();
                if (getCounter() >= 2)
                    studentsDialog.setVisible(true);
            }
            else
            {
                try
                {
                    gradesSheetDialog.setModel(get());
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
                
                incCounter();
                if (getCounter() >= 2)
                    gradesSheetDialog.setVisible(true);
            }
        }
    }
    
    private class NotSelectedStudentsProvider extends Worker<List<StudentDTO>>
    {
        
        private Integer id;
        
        public NotSelectedStudentsProvider(Integer id)
        {
            super("get");
            this.id = id;
        }
        
        @Override
        protected List<StudentDTO> doInBackground() throws Exception
        {
            delegate = DelegateFactory.getTeacherDelegate();
            return delegate.getStudentsNotFromActivity(id);
        }
        
        @Override
        public void done()
        {
            stopProgress();
            try
            {
                studentsDialog.setChoosableModel(get());
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
            
            incCounter();
            if (getCounter() == 2)
                studentsDialog.setVisible(true);
            
        }
        
    }
    
    private class UpdateStudentsWorker extends Worker<Void>
    {
        
        private List<Integer> lsit;
        
        public UpdateStudentsWorker(List<Integer> list)
        {
            super("save");
            this.lsit = list;
        }
        
        @Override
        protected Void doInBackground() throws Exception
        {
            
            DelegateFactory.getTeacherDelegate().addAttendances(lsit, currentLabActivity.getId(), 0, 0);
            return null;
        }
        
        @Override
        public void done()
        {
            stopProgress();
            studentsDialog.clearSelection();
            
        }
        
    }
    
    private class CancelListener implements ActionListener
    {
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (studentsDialog != null)
                studentsDialog.dispose();
            if (attendancesDialog != null)
                attendancesDialog.dispose();
            if (gradesDialog != null)
                gradesDialog.dispose();
            if (gradesSheetDialog != null)
                gradesSheetDialog.dispose();
            
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
            DelegateFactory.getTeacherDelegate().updateAttendances(attendanceModel.getData());
            return null;
        }
        
        @Override
        public void done()
        {
            stopProgress();
        }
        
    }
    
    private class GradeTypesProvider extends Worker<List<GradeTypeDTO>>
    {
        
        public GradeTypesProvider()
        {
            super("get");
        }
        
        @Override
        protected List<GradeTypeDTO> doInBackground() throws Exception
        {
            return DelegateFactory.getCommonDelegate().getGradeTypes();
        }
        
        @Override
        public void done()
        {
            stopProgress();
            try
            {
                gradesSheetDialog.setComboModel(get());
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
            incCounter();
            if (getCounter() >= 2)
                gradesSheetDialog.setVisible(true);
            
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
            
            DelegateFactory.getTeacherDelegate().addRegularGrades(gradesSheetDialog.getMap(),
                    currentLabActivity.getId(), gradesSheetDialog.getTypeData(), gradesSheetDialog.getDescription());
            
            return null;
        }
        
        @Override
        public void done()
        {
            stopProgress();
            gradesSheetDialog.clearSelection();
            
        }
    }
    
    private class GradesProvider extends Worker<List<StudentDTO>>
    {
        
        @Override
        protected List<StudentDTO> doInBackground() throws Exception
        {
            return DelegateFactory.getTeacherDelegate().getStudentsWithGradesFromActivity(currentLabActivity.getId());
        }
        
        @Override
        public void done()
        {
            stopProgress();
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
            gradesDialog.setActivity(currentLabActivity.getId());
            gradesDialog.setVisible(true);
        }
        
    }
    
}
