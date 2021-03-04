package ru.mechtatell;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.mechtatell.dao.mappers.LineMapperFirst;
import ru.mechtatell.dao.mappers.LineMapperSecond;
import ru.mechtatell.dao.mappers.LineMapperThird;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@ComponentScan("ru.mechtatell")
public class SpringConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://192.168.1.69:5432/db_lab");
        dataSource.setUsername("mechtatell");
        dataSource.setPassword("123qwe");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate =  new JdbcTemplate(dataSource());
        List<String> strings = jdbcTemplate.query("SELECT pr.name, pr.start_date, pr.end_date, pl.construction_type, pl.floors_count, SUM(m.price * mp.count) AS price FROM project pr JOIN plan pl ON pr.plan_id = pl.id LEFT JOIN material_plan mp on pl.id = mp.plan_id LEFT JOIN material m on mp.material_id = m.id GROUP BY pr.name, pr.start_date, pr.end_date, pl.construction_type, pl.floors_count", new LineMapperFirst());
        for (String string : strings) {
            System.out.println(string);
        }
        System.out.println();
        strings = jdbcTemplate.query("SELECT e.first_name, e.last_name, p.name, p.payment, COUNT(*) as team_count FROM employee e JOIN position p on e.position_id = p.id JOIN employee_team et on e.id = et.employee_id JOIN team t on et.team_id = t.id GROUP BY e.first_name, e.last_name, p.name, p.payment;", new LineMapperSecond());
        for (String string : strings) {
            System.out.println(string);
        }
        System.out.println();
        strings = jdbcTemplate.query("SELECT t.name, COUNT(et.employee_id) AS employee_count, SUM(p.payment) AS payment_sum FROM team t JOIN employee_team et on t.id = et.team_id JOIN employee e on et.employee_id = e.id JOIN position p on e.position_id = p.id GROUP BY t.name", new LineMapperThird());
        for (String string : strings) {
            System.out.println(string);
        }
        return jdbcTemplate;
    }
}
