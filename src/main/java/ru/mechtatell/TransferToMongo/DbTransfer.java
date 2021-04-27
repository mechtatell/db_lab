package ru.mechtatell.TransferToMongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mechtatell.DAO.Repos.*;
import ru.mechtatell.Models.*;
import ru.mechtatell.TransferToMongo.Models.*;
import ru.mechtatell.TransferToMongo.Repos.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DbTransfer {
    private final PositionMongoRepos positionMongoRepos;
    private final EmployeeMongoRepos employeeMongoRepos;
    private final TeamMongoRepos teamMongoRepos;
    private final ProjectMongoRepos projectMongoRepos;
    private final PlanMongoRepos planMongoRepos;
    private final MaterialMongoRepos materialMongoRepos;

    private final PositionRepos positionPostgresRepos;
    private final EmployeeRepos employeePostgresRepos;
    private final TeamRepos teamPostgresRepos;
    private final ProjectRepos projectPostgresRepos;
    private final PlanRepos planPostgresRepos;
    private final MaterialRepos materialPostgresRepos;

    @Autowired
    public DbTransfer(PositionMongoRepos positionMongoRepos, EmployeeMongoRepos employeeMongoRepos,
                      TeamMongoRepos teamMongoRepos, ProjectMongoRepos projectMongoRepos,
                      PlanMongoRepos planMongoRepos, MaterialMongoRepos materialMongoRepos,
                      PositionRepos positionPostgresRepos, EmployeeRepos employeePostgresRepos,
                      TeamRepos teamPostgresRepos, ProjectRepos projectPostgresRepos,
                      PlanRepos planPostgresRepos, MaterialRepos materialPostgresRepos) {
        this.positionMongoRepos = positionMongoRepos;
        this.employeeMongoRepos = employeeMongoRepos;
        this.teamMongoRepos = teamMongoRepos;
        this.projectMongoRepos = projectMongoRepos;
        this.planMongoRepos = planMongoRepos;
        this.materialMongoRepos = materialMongoRepos;
        this.positionPostgresRepos = positionPostgresRepos;
        this.employeePostgresRepos = employeePostgresRepos;
        this.teamPostgresRepos = teamPostgresRepos;
        this.projectPostgresRepos = projectPostgresRepos;
        this.planPostgresRepos = planPostgresRepos;
        this.materialPostgresRepos = materialPostgresRepos;
    }

    public void transfer() {
        positionMongoRepos.deleteAll();
        employeeMongoRepos.deleteAll();
        teamMongoRepos.deleteAll();
        projectMongoRepos.deleteAll();
        planMongoRepos.deleteAll();
        materialMongoRepos.deleteAll();

        List<Position> positions = (List<Position>) positionPostgresRepos.findAll();
        positions.stream()
                .map(this::toDocument)
                .forEach(positionMongoRepos::save);

        List<Employee> employees = (List<Employee>) employeePostgresRepos.findAll();
        employees.stream()
                .map(this::toDocument)
                .forEach(employeeMongoRepos::save);

        List<Team> teams = (List<Team>) teamPostgresRepos.findAll();
        teams.stream()
                .map(this::toDocument)
                .forEach(teamMongoRepos::save);

        List<Material> materials = (List<Material>) materialPostgresRepos.findAll();
        materials.stream()
                .map(this::toDocument)
                .forEach(materialMongoRepos::save);

        List<Plan> plans = (List<Plan>) planPostgresRepos.findAll();
        plans.stream()
                .map(this::toDocument)
                .forEach(planMongoRepos::save);

        List<Project> projects = (List<Project>) projectPostgresRepos.findAll();
        projects.stream()
                .map(this::toDocument)
                .forEach(projectMongoRepos::save);
    }

    private PositionDocument toDocument(Position position) {
        return new PositionDocument(position.getId(), position.getName(), position.getPayment());
    }

    private EmployeeDocument toDocument(Employee employee) {
        return new EmployeeDocument(employee.getId(), employee.getFirstName(), employee.getLastName(),
                employee.getPosition() != null ? toDocument(employee.getPosition()) : null);
    }

    private TeamDocument toDocument(Team team) {
        return new TeamDocument(team.getId(), team.getName(),
                team.getEmployeeList().stream().map(this::toDocument).collect(Collectors.toList()));
    }

    private MaterialDocument toDocument(Material material) {
        return new MaterialDocument(material.getId(), material.getName(), material.getPrice());
    }

    private PlanDocument toDocument(Plan plan) {
        return new PlanDocument(plan.getId(), plan.getConstructionType(), plan.getFloorsCount(),
                plan.getMaterialPlanList().stream().collect(Collectors.toMap(
                        e -> e.getMaterial().getId(), MaterialPlan::getCount)));
    }

    private ProjectDocument toDocument(Project project) {
        return new ProjectDocument(project.getId(), project.getName(), project.getStartDate(), project.getEndDate(),
                project.getTeamList().stream().map(this::toDocument).collect(Collectors.toList()), toDocument(project.getPlan()));
    }
}
