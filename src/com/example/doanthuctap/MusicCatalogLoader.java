package com.example.doanthuctap;
import java.util.ArrayList;


import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class MusicCatalogLoader {
	private String TAG = "MusicCatalog";
	private Activity activity;
	private ContentResolver mCR;
	private ArrayList<SongItem> list_songs = null;
	private ArrayList<PlaylistItem> list_playlists = null;
	private boolean bAlwaysGetNew = false;
	public MusicCatalogLoader(Activity a) {
		activity = a;
		mCR = activity.getContentResolver();
		
	}

	public void setAlwaysGetNew(boolean b) {
		bAlwaysGetNew = b;
	}
	public void loadMusicCatalog() {
		list_songs = getSongsList();
	}
	public ArrayList<PlaylistItem> ListPlaylists() {
	return list_playlists;
}
	public class PlaylistItem {
		public long id;
		public String dataStream;
		public String name;

		public PlaylistItem(long _id, String d, String n) {
			id = _id;
			dataStream = d;
			name = n;
		}
	}
	public ArrayList<PlaylistItem> getPlaylistsList() {
		if (list_playlists != null && !bAlwaysGetNew)
			return list_playlists;
		Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

		Cursor cur = mCR.query(uri, null, null, null, null);

		if (cur == null) {
			return null;
		}

		if (!cur.moveToFirst()) {
			return null;
		}

		ArrayList<PlaylistItem> list = new ArrayList<PlaylistItem>();
		int idColumn = cur.getColumnIndex(MediaStore.Audio.Playlists._ID);
		int dataColumn = cur.getColumnIndex(MediaStore.Audio.Playlists.DATA);
		int nameColumn = cur.getColumnIndex(MediaStore.Audio.Playlists.NAME);

		do {
			list.add(new PlaylistItem(cur.getLong(idColumn), cur.getString(dataColumn), cur.getString(nameColumn)));
		} while (cur.moveToNext());

		list_playlists = list;
		return list;
	}
	public static class SongItem {
		public long id = 0;
		public String title = "";
		public String artist = "";
		public long artistID = 0;
		public String album = "";
		public long albumID = 0;
		public long duration = 0;
		public String dataStream = "";

		public SongItem() {

		}

		public SongItem(long _id, String t, String a, String p) {
			id = _id;
			title = t;
			artist = a;
			dataStream = p;
		}
	}
	private ArrayList<SongItem> queryForSongs(Uri uri, String[] projection, String selection, String[] args,
			String sortOrder) {
		Cursor cur = mCR.query(uri, projection, selection, args, sortOrder);

		if (cur == null) {
			return null;
		}

		if (!cur.moveToFirst()) {
			return null;
		}

		ArrayList<SongItem> list = new ArrayList<SongItem>();

		int idColumn = cur.getColumnIndex(MediaStore.Audio.Media._ID);
		int titleColumn = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);
		int artistColumn = cur.getColumnIndex(MediaStore.Audio.Media.ARTIST);
		int artistIDColumn = cur.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
		int durationColumn = cur.getColumnIndex(MediaStore.Audio.Media.DURATION);
		int dataColumn = cur.getColumnIndex(MediaStore.Audio.Media.DATA);
		do {
			SongItem si = new SongItem();
			si.id = cur.getLong(idColumn);
			si.title = cur.getString(titleColumn);
			si.artist = cur.getString(artistColumn);
			si.artistID = cur.getLong(artistIDColumn);
			si.duration = cur.getLong(durationColumn);
			si.dataStream = cur.getString(dataColumn);
			list.add(si);
		} while (cur.moveToNext());
		return list;
	}
	// Playlist member retriever
	public ArrayList<SongItem> getSongsInPlaylist(PlaylistItem pl) {
		Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", pl.id);

		return queryForSongs(uri, null, MediaStore.Audio.Media.IS_MUSIC + "=1", null, null);
	}
	// Get all the songs
		public ArrayList<SongItem> getSongsList() {
			if (list_songs != null && !bAlwaysGetNew)
				return list_songs;

			Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

			// WHERE statement
			String selection = MediaStore.Audio.Media.IS_MUSIC + "=1";

			list_songs = queryForSongs(uri, null, selection, null, null);
			return list_songs;
		}
}
