package recibos;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import recibos.Tareas;

import recibos.TareasDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig tareasDaoConfig;

    private final TareasDao tareasDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        tareasDaoConfig = daoConfigMap.get(TareasDao.class).clone();
        tareasDaoConfig.initIdentityScope(type);

        tareasDao = new TareasDao(tareasDaoConfig, this);

        registerDao(Tareas.class, tareasDao);
    }
    
    public void clear() {
        tareasDaoConfig.getIdentityScope().clear();
    }

    public TareasDao getTareasDao() {
        return tareasDao;
    }

}
