package repository;

import config.Mysql;
import models.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private Connection connection;

    public Repository() {

    }

    public Repository(Mysql connection) {
        try {
            this.connection = connection.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Item
    public List<models.Item> getListItem() throws SQLException {
        List<models.Item> items = new ArrayList<>();
        String query = "select * from items where deleted_at is null order by id desc";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            models.Item item = new models.Item();
            item.setId(rs.getLong("id"));
            item.setName(rs.getString("name"));
            item.setCategory(rs.getString("category"));
            item.setStatus(rs.getString("status"));
            item.setCreatedAt(rs.getTimestamp("created_at"));
            item.setUpdatedAt(rs.getTimestamp("updated_at"));
            item.setDeletedAt(rs.getTimestamp("deleted_at"));

            items.add(item);
        }

        if (stmt != null) {
            stmt.close();
        }

        return items;
    }

    public models.Item getItemById(long id) {
        models.Item item = new models.Item();
        String query = "select * from items where id = ? and deleted_at is null";
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                item.setId(rs.getLong("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setStatus(rs.getString("status"));
                item.setCreatedAt(rs.getTimestamp("created_at"));
                item.setUpdatedAt(rs.getTimestamp("updated_at"));
                item.setDeletedAt(rs.getTimestamp("deleted_at"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new models.Item();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return item;
    }

    public boolean addItem(models.Item item) {
        String query = "insert into items(name, category, status)" +
                " " +
                "value(?,?,?)";
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCategory());
            stmt.setString(3, item.getStatus());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return true;
    }

    public boolean deleteItemById(long id) {
        String query = "update items set deleted_at = now() where id = ?";
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return true;
    }

    public models.Item updateItem(models.Item item) {
        String query = "update items" +
                " " +
                "set" +
                " " +
                "name = ?," +
                "category = ?," +
                "status = ?, " +
                "updated_at = now()" +
                " " +
                "where id = ? and deleted_at is null";
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCategory());
            stmt.setString(3, item.getStatus());
            stmt.setLong(4, item.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new models.Item();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return item;
    }

    // Rental
    public boolean addRental(models.Rental rental) {
        String query = "insert into rental(item_id, tenant, start_time, end_time)" +
                " " +
                "value(?,?,?,?)";
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, rental.getItem().getId());
            stmt.setString(2, rental.getTenant());
            stmt.setTimestamp(3, rental.getStartTime());
            stmt.setTimestamp(4, rental.getEndTime());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return true;
    }

    public boolean deleteRental(long id) {
        String query = "update rental set deleted_at = now() where id = ?";
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return true;
    }

    public models.Rental updateRental(models.Rental rental) {
        String query = "update rental" +
                " " +
                "set" +
                " " +
                "item_id = ?," +
                "tenant = ?," +
                "start_time = ?, " +
                "end_time = ?, " +
                "updated_at = now()" +
                " " +
                "where id = ? and deleted_at is null";
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, rental.getItem().getId());
            stmt.setString(2, rental.getTenant());
            stmt.setTimestamp(3, rental.getStartTime());
            stmt.setTimestamp(4, rental.getEndTime());
            stmt.setLong(5, rental.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new models.Rental();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return rental;
    }

    public List<models.Rental> getListRental() throws SQLException {
        List<models.Rental> rentals = new ArrayList<>();
        String query = "select" +
                " " +
                "rl.id as rental_id," +
                "rl.tenant as tenant," +
                "rl.start_time as start_time," +
                "rl.end_time as end_time," +
                "rl.created_at as rental_created_at," +
                "rl.updated_at as rental_updated_at," +
                "rl.deleted_at as rental_deleted_at," +
                "ims.id as item_id," +
                "ims.name as item_name," +
                "ims.category as item_category," +
                "ims.status as item_status," +
                "ims.created_at item_created_at," +
                "ims.updated_at as item_updated_at," +
                "ims.deleted_at as item_deleted_at" +
                " " +
                "from rental rl" +
                " " +
                "left join items ims on ims.id = rl.item_id" +
                " " +
                "where rl.deleted_at is null" +
                " " +
                "order by rl.id desc";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            models.Item item = new Item();
            item.setId(rs.getLong("item_id"));
            item.setName(rs.getString("item_name"));
            item.setCategory(rs.getString("item_category"));
            item.setStatus(rs.getString("item_status"));
            item.setCreatedAt(rs.getTimestamp("rental_created_at"));
            item.setUpdatedAt(rs.getTimestamp("rental_updated_at"));
            item.setDeletedAt(rs.getTimestamp("rental_deleted_at"));

            models.Rental rental = new models.Rental();
            rental.setId(rs.getLong("rental_id"));
            rental.setItem(item);
            rental.setTenant(rs.getString("tenant"));
            rental.setStartTime(rs.getTimestamp("start_time"));
            rental.setEndTime(rs.getTimestamp("end_time"));
            rental.setCreatedAt(rs.getTimestamp("rental_created_at"));
            rental.setUpdatedAt(rs.getTimestamp("rental_updated_at"));
            rental.setDeletedAt(rs.getTimestamp("rental_deleted_at"));

            rentals.add(rental);

        }

        if (stmt != null) {
            stmt.close();
        }

        return rentals;
    }

    public models.Rental getRentalById(long id) {
        models.Rental rental = new models.Rental();
        String query = "select" +
                " " +
                "rl.id as rental_id," +
                "rl.tenant as tenant," +
                "rl.start_time as start_time," +
                "rl.end_time as end_time," +
                "rl.created_at as rental_created_at," +
                "rl.updated_at as rental_updated_at," +
                "rl.deleted_at as rental_deleted_at," +
                "ims.id as item_id," +
                "ims.name as item_name," +
                "ims.category as item_category," +
                "ims.status as item_status," +
                "ims.created_at item_created_at," +
                "ims.updated_at as item_updated_at," +
                "ims.deleted_at as item_deleted_at" +
                " " +
                "from rental rl" +
                " " +
                "left join items ims on ims.id = rl.item_id " +
                " " +
                "where rl.id = ? and rl.deleted_at is null";
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                models.Item item = new Item();
                item.setId(rs.getLong("item_id"));
                item.setName(rs.getString("item_name"));
                item.setCategory(rs.getString("item_category"));
                item.setStatus(rs.getString("item_status"));
                item.setCreatedAt(rs.getTimestamp("rental_created_at"));
                item.setUpdatedAt(rs.getTimestamp("rental_updated_at"));
                item.setDeletedAt(rs.getTimestamp("rental_deleted_at"));

                rental = new models.Rental();
                rental.setId(rs.getLong("rental_id"));
                rental.setItem(item);
                rental.setTenant(rs.getString("tenant"));
                rental.setStartTime(rs.getTimestamp("start_time"));
                rental.setEndTime(rs.getTimestamp("end_time"));
                rental.setCreatedAt(rs.getTimestamp("rental_created_at"));
                rental.setUpdatedAt(rs.getTimestamp("rental_updated_at"));
                rental.setDeletedAt(rs.getTimestamp("rental_deleted_at"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new models.Rental();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return rental;
    }

}
