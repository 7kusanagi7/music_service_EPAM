package kz.epam.store.service.impl;

import kz.epam.store.dao.DiskDao;
import kz.epam.store.dao.impl.DiskDaoImpl;
import kz.epam.store.entity.Disk;
import kz.epam.store.exception.DaoException;
import kz.epam.store.exception.ServiceException;
import kz.epam.store.service.DiskService;

import java.util.ArrayList;
import java.util.List;

public class DiskServiceImpl implements DiskService {

    private final DiskDao dao;

    public DiskServiceImpl(){
        dao = new DiskDaoImpl();
    }

    @Override
    public Disk getDiskById(int id) throws ServiceException {
        try {
            return dao.get(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Disk> getAll() throws ServiceException {
        try {
            return dao.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getDisksCount() throws ServiceException {
        try {
            return dao.getAll().size();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addDisk(Disk disk) throws ServiceException {
        try {
            dao.save(disk);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Disk> getByAuthorId(int authorId) throws ServiceException {
        List<Disk> disks = new ArrayList<>();
        for(Disk disk: getAll()){
            if(disk.getAuthorId() == authorId)
                disks.add(disk);
        }
        return disks;
    }

    @Override
    public void deleteById(int diskId) throws ServiceException {
        try {
            dao.delete(getDiskById(diskId));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateDisk(Disk disk) throws ServiceException {
        try {
            dao.update(disk);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
