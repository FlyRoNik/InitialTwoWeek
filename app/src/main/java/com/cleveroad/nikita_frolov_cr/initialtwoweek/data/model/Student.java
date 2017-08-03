package com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true, nameInDb = "students", createInDb = false)
public class Student {
    @Id
    @Property(nameInDb = "_id")
    private Long id;

    @NotNull
    @Property(nameInDb = "name_student")
    private String name;

    @NotNull
    @Property(nameInDb = "id_group")
    private Long idGroup;

    @NotNull
    @Property(nameInDb = "id_photo")
    private Long idPhoto;


    @ToOne(joinProperty = "idGroup")
    private Group group;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1943931642)
    private transient StudentDao myDao;


    @Generated(hash = 1084796087)
    public Student(Long id, @NotNull String name, @NotNull Long idGroup,
            @NotNull Long idPhoto) {
        this.id = id;
        this.name = name;
        this.idGroup = idGroup;
        this.idPhoto = idPhoto;
    }


    @Generated(hash = 1556870573)
    public Student() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Long getIdGroup() {
        return this.idGroup;
    }


    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }


    public Long getIdPhoto() {
        return this.idPhoto;
    }


    public void setIdPhoto(Long idPhoto) {
        this.idPhoto = idPhoto;
    }


    @Generated(hash = 201187923)
    private transient Long group__resolvedKey;


    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1966785124)
    public Group getGroup() {
        Long __key = this.idGroup;
        if (group__resolvedKey == null || !group__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GroupDao targetDao = daoSession.getGroupDao();
            Group groupNew = targetDao.load(__key);
            synchronized (this) {
                group = groupNew;
                group__resolvedKey = __key;
            }
        }
        return group;
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 232339194)
    public void setGroup(@NotNull Group group) {
        if (group == null) {
            throw new DaoException(
                    "To-one property 'idGroup' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.group = group;
            idGroup = group.getId();
            group__resolvedKey = idGroup;
        }
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1701634981)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStudentDao() : null;
    }

 
}
