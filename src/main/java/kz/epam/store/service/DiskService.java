package kz.epam.store.service;

import kz.epam.store.entity.Disk;
import kz.epam.store.exception.ServiceException;

import java.util.List;

public interface DiskService {
    Disk getDiskById(int id) throws ServiceException;

    List<Disk> getAll() throws ServiceException;

    int getDisksCount() throws ServiceException;

    void addDisk(Disk disk) throws ServiceException;

    List<Disk> getByAuthorId(int authorId) throws ServiceException;

    void deleteById(int diskId) throws ServiceException;

    void updateDisk(Disk disk) throws ServiceException;
}
