package ru.mechtatell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.mechtatell.Views.MainFrame;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private final MainFrame mainFrame;

    @Autowired
    public Main(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) {
        mainFrame.init();
    }
}
