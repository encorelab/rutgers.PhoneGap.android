package org.encorelab.rutgers;

import org.encorelab.rutgers.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;


public class RutgersSettings extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences_screen);
		setResult(RESULT_OK);
	}

}
