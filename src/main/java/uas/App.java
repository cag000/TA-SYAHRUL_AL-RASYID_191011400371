package uas;

import config.Config;
import config.Mysql;
import repository.Repository;
import usecase.Usecase;
import view.View;

public class App {
    public static void main(String[] args) {
        Config cfg = new Config();
        Mysql mysql = new Mysql(cfg.getConfig());

        // Repository
        Repository repository = new Repository(mysql);

        // Usecase
        Usecase usecase = new Usecase(repository);

        // View
        View view = new View(usecase);
        view.setVisible(true);
    }
}
