package com.codepath.simpletodo.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.codepath.simpletodo.Helper.TaskDBHelper;
import com.codepath.simpletodo.Model.Task;
import com.codepath.simpletodo.R;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditItemFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TASK_NAME = "name";
    private static final String TASK_ID = "id";
    private static final String POSITION = "position";
    private static final String TASK_DEADLINE = "deadline";
    private static final String TASK_DESCRIPTION = "description";
    private static final String TASK_PRIORITY = "priority";

    // TODO: Rename and change types of parameters
    private String mTaskName;
    private int mTaskId;
    private String mTaskDeadline;
    private String mTaskPriority;
    private String mTaskDescription;
    private int mPosition;
    EditText etItem;
    private EditText etDeadline;
    private EditText etDescription;
    private BetterSpinner snPriority;
    private ImageButton imDeadline;
    Button btnSave;
    private String[] PRIORITYLIST = {"Low", "Normal", "High"};
    private OnFragmentInteractionListener mListener;

    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private static final String TIME_PATTERN = "HH:mm";
    public EditItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditItemFragment newInstance(Task param1, int param2) {
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        args.putString(TASK_NAME, param1.name);
        args.putInt(TASK_ID, param1.id);
        args.putString(TASK_DEADLINE, param1.deadline.toString());
        args.putString(TASK_DESCRIPTION, param1.description);
        args.putString(TASK_PRIORITY, param1.priority);
        args.putInt(POSITION, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTaskName = getArguments().getString(TASK_NAME);
            mTaskId = getArguments().getInt(TASK_ID);
            mTaskDeadline = getArguments().getString(TASK_DEADLINE);
            mTaskDescription = getArguments().getString(TASK_DESCRIPTION);
            mTaskPriority = getArguments().getString(TASK_PRIORITY);
            mPosition = getArguments().getInt(POSITION);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_item, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getActivity().getFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        etDeadline.setText(calendar.getTime().toString());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Task item, int pos);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        etItem = (EditText) view.findViewById(R.id.etName);
        etDeadline = (EditText) view.findViewById(R.id.etDeadline);
        etDescription = (EditText) view.findViewById(R.id.etDescription);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, PRIORITYLIST);
        snPriority = (BetterSpinner)
                view.findViewById(R.id.snPriority);
        snPriority.setAdapter(adapter);
        snPriority.setText(mTaskPriority);
        imDeadline = (ImageButton) view.findViewById(R.id.imDeadline);
        imDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateTimePicker();
            }
        });
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });

        calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,1);
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        // Fetch arguments from bundle and set title
        String name = getArguments().getString(TASK_NAME, "Enter Name");
        etItem.setText(name);
        etDeadline.setText(mTaskDeadline);
        etDescription.setText(mTaskDescription);
        // Show soft keyboard automatically and request focus to field
        etItem.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void onSave(){
        if (mListener != null) {
            Task mTask = new Task(etItem.getText().toString(),new Date(etDeadline.getText().toString()),etDescription.getText().toString(),snPriority.getText().toString(),0);
            mTask.setId(mTaskId);
            TaskDBHelper.updateEntityToDB(mTask,getContext());
            mListener.onFragmentInteraction(mTask,mPosition);
            dismiss();
        }
    }
    public void openDateTimePicker() {
        DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getActivity().getFragmentManager(), "datePicker");

    }
}
