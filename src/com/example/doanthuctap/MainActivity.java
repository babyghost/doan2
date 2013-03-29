package com.example.doanthuctap;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity  {
	private static final String TAG = "com.npl.simplemusicplayer_MainPlayer";

	private static final String PREF_NAME = "MAINPLAYER";
	private static final String SETTING_SHUFFLE = "SHUFFLE";
	private static final String SETTING_REPEAT = "REPEAT";

	private TextView playing_Title;
	private TextView playing_singer;
	private TextView currentPlayingPosition;
	private TextView currentDuration;

	private PlaylistManager mPlaylistMgr;
	private MusicCatalogLoader mMusicLoader;

	private ImageButton bt_Play;
	private ImageButton bt_Next;
	private ImageButton bt_Previous;
	private ImageButton bt_ShuffleToggle;
	private ImageButton bt_RepeatToggle;
	private ImageButton playlistView;

	private SeekBar songProgressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Textview playing display
				playing_Title = (TextView) findViewById(R.id.Songsname);
				playing_singer = (TextView) findViewById(R.id.Singer);
				currentPlayingPosition = (TextView) findViewById(R.id.currentDuration);
				currentDuration = (TextView) findViewById(R.id.songtotalDuration);
				// Song progress bar
				songProgressBar = (SeekBar) findViewById(R.id.seekbar);
				songProgressBar.setMax(100);
				songProgressBarIsBeingTouch = false;
				songProgressBar.setOnSeekBarChangeListener(songProgressBar_changeListener);

				playlistView = (ImageButton) findViewById(R.id.playlist);
				playlistView.setOnClickListener(bt_Playlist);

				// assign listener for button
				bt_Previous = (ImageButton) findViewById(R.id.btnPrevious);
				bt_Previous.setOnClickListener(bt_Previous_click);

				bt_Play = (ImageButton) findViewById(R.id.btnPlay);
				bt_Play.setOnClickListener(bt_Play_click);

				bt_Next = (ImageButton) findViewById(R.id.btnNext);
				bt_Next.setOnClickListener(bt_Next_click);

				bt_ShuffleToggle = (ImageButton) findViewById(R.id.shuffle);
				bt_ShuffleToggle.setOnClickListener(bt_ShuffleToggle_Click);

				bt_RepeatToggle = (ImageButton) findViewById(R.id.repeat);
				bt_RepeatToggle.setOnClickListener(bt_RepeatToggle_Click);

				// restore preferences
				restorePreferences();
	}

}
