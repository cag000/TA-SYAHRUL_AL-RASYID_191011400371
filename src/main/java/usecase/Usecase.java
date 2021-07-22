package usecase;

import repository.Repository;

import java.sql.SQLException;
import java.util.List;

public class Usecase {
    private Repository repository;

    public Usecase(Repository repository) {
        this.repository = repository;
    }

    public List<models.Item> listDataItem() throws SQLException {
        return repository.getListItem();
    }

    public models.Item getDataItem(long id) {
        return repository.getItemById(id);
    }

    public boolean addDataItem(models.Item form) {
        return repository.addItem(form);
    }

    public models.Item updateDataItem(models.Item form) {
        return repository.updateItem(form);
    }

    public boolean deleteDataItem(long id) {
        return repository.deleteItemById(id);
    }

    public List<models.Rental> listDataRental() throws SQLException {
        return repository.getListRental();
    }

    public models.Rental getDataRental(long id) {
        return repository.getRentalById(id);
    }

    public boolean addDataRental(models.Rental form) {
        return repository.addRental(form);
    }

    public models.Rental updateDataRental(models.Rental form) {
        return repository.updateRental(form);
    }

    public boolean deleteDataRental(long id) {
        return repository.deleteRental(id);
    }

}
