package com.tebyl.aviariolocal.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BirdDao_Impl implements BirdDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Bird> __insertionAdapterOfBird;

  private final StringListConverter __stringListConverter = new StringListConverter();

  private final EntityDeletionOrUpdateAdapter<Bird> __deletionAdapterOfBird;

  private final SharedSQLiteStatement __preparedStmtOfSetFavorite;

  public BirdDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBird = new EntityInsertionAdapter<Bird>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `birds` (`id`,`species`,`sci`,`photoUri`,`dateMillis`,`dateStr`,`location`,`locShort`,`behavior`,`notes`,`tags`,`isFavorite`,`lat`,`lng`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Bird entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getSpecies() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getSpecies());
        }
        if (entity.getSci() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSci());
        }
        statement.bindString(4, entity.getPhotoUri());
        statement.bindLong(5, entity.getDateMillis());
        statement.bindString(6, entity.getDateStr());
        statement.bindString(7, entity.getLocation());
        statement.bindString(8, entity.getLocShort());
        statement.bindString(9, entity.getBehavior());
        statement.bindString(10, entity.getNotes());
        final String _tmp = __stringListConverter.fromList(entity.getTags());
        statement.bindString(11, _tmp);
        final int _tmp_1 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        if (entity.getLat() == null) {
          statement.bindNull(13);
        } else {
          statement.bindDouble(13, entity.getLat());
        }
        if (entity.getLng() == null) {
          statement.bindNull(14);
        } else {
          statement.bindDouble(14, entity.getLng());
        }
      }
    };
    this.__deletionAdapterOfBird = new EntityDeletionOrUpdateAdapter<Bird>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `birds` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Bird entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfSetFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE birds SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final Bird bird, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBird.insertAndReturnId(bird);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Bird bird, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBird.handle(bird);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setFavorite(final long id, final boolean fav,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetFavorite.acquire();
        int _argIndex = 1;
        final int _tmp = fav ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetFavorite.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Bird>> observeAll() {
    final String _sql = "SELECT * FROM birds ORDER BY dateMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"birds"}, new Callable<List<Bird>>() {
      @Override
      @NonNull
      public List<Bird> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSpecies = CursorUtil.getColumnIndexOrThrow(_cursor, "species");
          final int _cursorIndexOfSci = CursorUtil.getColumnIndexOrThrow(_cursor, "sci");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
          final int _cursorIndexOfDateStr = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStr");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLocShort = CursorUtil.getColumnIndexOrThrow(_cursor, "locShort");
          final int _cursorIndexOfBehavior = CursorUtil.getColumnIndexOrThrow(_cursor, "behavior");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLng = CursorUtil.getColumnIndexOrThrow(_cursor, "lng");
          final List<Bird> _result = new ArrayList<Bird>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Bird _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSpecies;
            if (_cursor.isNull(_cursorIndexOfSpecies)) {
              _tmpSpecies = null;
            } else {
              _tmpSpecies = _cursor.getString(_cursorIndexOfSpecies);
            }
            final String _tmpSci;
            if (_cursor.isNull(_cursorIndexOfSci)) {
              _tmpSci = null;
            } else {
              _tmpSci = _cursor.getString(_cursorIndexOfSci);
            }
            final String _tmpPhotoUri;
            _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            final long _tmpDateMillis;
            _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
            final String _tmpDateStr;
            _tmpDateStr = _cursor.getString(_cursorIndexOfDateStr);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpLocShort;
            _tmpLocShort = _cursor.getString(_cursorIndexOfLocShort);
            final String _tmpBehavior;
            _tmpBehavior = _cursor.getString(_cursorIndexOfBehavior);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final List<String> _tmpTags;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.fromString(_tmp);
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            final Double _tmpLat;
            if (_cursor.isNull(_cursorIndexOfLat)) {
              _tmpLat = null;
            } else {
              _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            }
            final Double _tmpLng;
            if (_cursor.isNull(_cursorIndexOfLng)) {
              _tmpLng = null;
            } else {
              _tmpLng = _cursor.getDouble(_cursorIndexOfLng);
            }
            _item = new Bird(_tmpId,_tmpSpecies,_tmpSci,_tmpPhotoUri,_tmpDateMillis,_tmpDateStr,_tmpLocation,_tmpLocShort,_tmpBehavior,_tmpNotes,_tmpTags,_tmpIsFavorite,_tmpLat,_tmpLng);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object get(final long id, final Continuation<? super Bird> $completion) {
    final String _sql = "SELECT * FROM birds WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Bird>() {
      @Override
      @Nullable
      public Bird call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSpecies = CursorUtil.getColumnIndexOrThrow(_cursor, "species");
          final int _cursorIndexOfSci = CursorUtil.getColumnIndexOrThrow(_cursor, "sci");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
          final int _cursorIndexOfDateStr = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStr");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfLocShort = CursorUtil.getColumnIndexOrThrow(_cursor, "locShort");
          final int _cursorIndexOfBehavior = CursorUtil.getColumnIndexOrThrow(_cursor, "behavior");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLng = CursorUtil.getColumnIndexOrThrow(_cursor, "lng");
          final Bird _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSpecies;
            if (_cursor.isNull(_cursorIndexOfSpecies)) {
              _tmpSpecies = null;
            } else {
              _tmpSpecies = _cursor.getString(_cursorIndexOfSpecies);
            }
            final String _tmpSci;
            if (_cursor.isNull(_cursorIndexOfSci)) {
              _tmpSci = null;
            } else {
              _tmpSci = _cursor.getString(_cursorIndexOfSci);
            }
            final String _tmpPhotoUri;
            _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            final long _tmpDateMillis;
            _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
            final String _tmpDateStr;
            _tmpDateStr = _cursor.getString(_cursorIndexOfDateStr);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            final String _tmpLocShort;
            _tmpLocShort = _cursor.getString(_cursorIndexOfLocShort);
            final String _tmpBehavior;
            _tmpBehavior = _cursor.getString(_cursorIndexOfBehavior);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final List<String> _tmpTags;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfTags);
            _tmpTags = __stringListConverter.fromString(_tmp);
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            final Double _tmpLat;
            if (_cursor.isNull(_cursorIndexOfLat)) {
              _tmpLat = null;
            } else {
              _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            }
            final Double _tmpLng;
            if (_cursor.isNull(_cursorIndexOfLng)) {
              _tmpLng = null;
            } else {
              _tmpLng = _cursor.getDouble(_cursorIndexOfLng);
            }
            _result = new Bird(_tmpId,_tmpSpecies,_tmpSci,_tmpPhotoUri,_tmpDateMillis,_tmpDateStr,_tmpLocation,_tmpLocShort,_tmpBehavior,_tmpNotes,_tmpTags,_tmpIsFavorite,_tmpLat,_tmpLng);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM birds";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
