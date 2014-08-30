package kankan.wheel.demo.extended;


import kankan.wheel.demo.extended.R;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeActivity extends Activity {
	// Time changed flag
	private boolean timeChanged = false;

	// Time scrolled flag
	private boolean timeScrolled = false;
	private TextView temperature;
	private int T_START = 35;
	private int T_END = 44;
	private Button cancel_button;
	private Button submit_button;
	private  WheelView hours;
	private  WheelView mins;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.time_layout);

		hours = (WheelView) findViewById(R.id.hour);
		hours.setViewAdapter(new NumericWheelAdapter(this, T_START, T_END));
		
		mins = (WheelView) findViewById(R.id.mins);
		mins.setViewAdapter(new NumericWheelAdapter(this, 0, 9, "%01d"));
		mins.setCyclic(true);

//		final TimePicker picker = (TimePicker) findViewById(R.id.time);
//		picker.setIs24HourView(true);
		temperature = (TextView) findViewById(R.id.temperature_show_number);
		cancel_button = (Button) findViewById(R.id.t_cancel_button);
		submit_button = (Button) findViewById(R.id.t_submit_button);
		init_button();
		// set current time
//		Calendar c = Calendar.getInstance();
//		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curHours = 2;
//		int curMinutes = c.get(Calendar.MINUTE);
		int curMinutes = 0;
		hours.setCurrentItem(curHours);
		mins.setCurrentItem(curMinutes);

//		picker.setCurrentHour(curHours);
//		picker.setCurrentMinute(curMinutes);

		// add listeners
		addChangingListener(mins, "min");
		addChangingListener(hours, "hour");

		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
//					picker.setCurrentHour(hours.getCurrentItem());
//					picker.setCurrentMinute(mins.getCurrentItem());
					updateTextView(hours.getCurrentItem(),mins.getCurrentItem());
					timeChanged = false;
				}
			}
		};
		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);

		OnWheelClickedListener click = new OnWheelClickedListener() {
			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {
				wheel.setCurrentItem(itemIndex, true);
			}
		};
		hours.addClickingListener(click);
		mins.addClickingListener(click);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			@Override
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				updateTextView(hours.getCurrentItem(),mins.getCurrentItem());
//				picker.setCurrentHour(hours.getCurrentItem());
//				picker.setCurrentMinute(mins.getCurrentItem());
				timeChanged = false;
			}
		};

		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);

//		picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//			@Override
//			public void onTimeChanged(TimePicker  view, int hourOfDay, int minute) {
//				if (!timeChanged) {
//					hours.setCurrentItem(hourOfDay, true);
//					mins.setCurrentItem(minute, true);
//				}
//			}
//		});
		
	}

	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * @param wheel the wheel
	 * @param label the wheel label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				//wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}
	private void updateTextView(int hour,int min){
//		Log.d("ddd",hour+min+"");
		hour =hour+T_START;
		temperature.setText(String.valueOf(hour)+"."+String.valueOf(min));
	}
	private void init_button(){
		cancel_button.setOnClickListener(new View.OnClickListener(){
			@Override
		    public void onClick(View v){
				hours.setCurrentItem(2);
				mins.setCurrentItem(0);
			}
		});
		
		submit_button.setOnClickListener(new View.OnClickListener() {  
		      
		    @Override  
		    public void onClick(View v) {  
		        // TODO Auto-generated method stub  
		        Log.i("TEST", "Should post to server");  
		        int integer_part=hours.getCurrentItem()+T_START;
		        int float_part=mins.getCurrentItem();
		        String t = integer_part+"."+float_part;
		        Log.e("temperature",t);
		    }  
		});  
	}
}
